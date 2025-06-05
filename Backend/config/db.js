const mysql = require('mysql2');

const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'Onix@8210',
    database: 'lawyerspotdb'
});


connection.connect((err) => {
    if(err){
        console.error('Error connecting to the database:', err);
    }
    else{
        console.log('Connected to the database successfully!');
    }
});

module.exports = connection;