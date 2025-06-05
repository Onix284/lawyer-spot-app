const express = require('express');
const app = express();
const db = require('./config/db')
const PORT = process.env.PORT || 3000;

app.use(express.json());

app.get('/', (req, res) => {
    res.send('Welcome to the Lawyer Spot API');
});

//Get all users
app.get('/users', (req, res) => {
    db.query('SELECT * FROM users', (err, results) => {
        if (err) {
            return res.status(500).json({ error: 'Database query failed' });
        }
        res.json(results);
    });
});



//Register a new user
app.post('/register', (req, res) => {
    const { name, email, password, profileImageURL, role} = req.body;

    if(!name || !email || !password || !profileImageURL || !role) {
        return res.status(400).json({ error: 'All fields are required' });
    }

    const sql = 'INSERT INTO users (name, email, password, profileImageURL, role) VALUES (?, ?, ?, ?, ?)';  
    
    const values = [name, email, password, profileImageURL, role];

    db.query(sql, values, (err, result) => {
        if(err){
            console.log('Error inserting user:', err);
            if (err.code === 'ER_DUP_ENTRY') {
                return res.status(409).json({ message: 'Email already exists' });
            }
            return res.status(500).json({ message: 'Server error' });
        }

        res.status(201).json({ message: 'User registered successfully', userId: result.insertId });
    });
});

//Lawyer Extra information 
app.post('/lawyer-profile', (req, res) => {

    const { user_id, location, experience, casesFought, wins, specialization, certificateOfPracticeURL } = req.body;

    if (!user_id || !location || !experience || !specialization || !certificateOfPracticeURL) {
        return res.status(400).json({ message: 'Please provide all required fields' });
    }

    // Step 1: Check if user exists and is a lawyer
    const checkUserSql = 'SELECT * FROM users WHERE id = ?';

    db.query(checkUserSql, [user_id], (err, result) => {

        if(err){
             console.error(err);
            return res.status(500).json({ message: 'Server error' });
        }

        if(result.length === 0){
            return res.status(404).json({ message: 'User not found' });
        }

        if(result[0].role !== 'lawyer') {
            return res.status(403).json({ message: 'User is not a lawyer' });
        }
        

        // Step 2: Insert lawyer profile
        const insertSql = `INSERT INTO lawyer_profile 
        (user_id, location, experience, casesFought, wins, specialization, certificateOfPracticeURL)
        VALUES (?, ?, ?, ?, ?, ?, ?)`;

        const values = [
            user_id,
            location,
            experience,
            casesFought || 0,
            wins || 0,
            specialization,
            certificateOfPracticeURL || ''
        ];

        db.query(insertSql, values, (err, result) => {
            if(err){
                console.error('Error inserting lawyer profile:', err);
                return res.status(500).json({ message: 'Server error' });
            }

            res.status(201).json({ message: 'Lawyer profile created successfully', profileId: result.insertId });
        });
    });
});


//Login a user
app.post('/login', (req, res) => {

    const { email, password } = req.body;

    if(!email || !password){
        return res.status(400).json({error: 'Email and password are required'});
    }

    const sql = 'SELECT * FROM users WHERE email = ? AND password = ?';

    const values = [email, password];

    db.query(sql, values, (err, results) => {
        
        if(err){
            console.error('Error during login:', err);
            return res.status(500).json({ error1: 'Server error' });
        }

        if(results.length === 0){
            return res.status(401).json({ error2: 'Invalid email or password' });
        }

        const user = results[0];
        res.status(200).json({ message: 'Login successful', user });
    });
});



//Get user profile by ID
app.get('/users/:id', (req, res) => {
    const { id } = req.params;
    const sql = 'SELECT * FROM users WHERE id = ?';
    db.query(sql, [id], (err, results) => {
        if(err) {
            console.error('Error fetching user profile:', err);
            return res.status(500).json({ error: 'Server error' });
        }

        if(results.length === 0) {
            return res.status(404).json({ error: 'User not found' });
        }

        res.status(200).json(results[0]);
    })
})


app.listen(PORT, () => {
    console.log(`🚀 Server running at http://localhost:${PORT}`);
});