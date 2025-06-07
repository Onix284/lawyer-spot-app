const db = require('../config/db')

function signUpUser(name, email, password, profileImageURL, role, callback){

    const sql = 'INSERT INTO users (name, email, password, profileImageURL, role) VALUES (?, ?, ?, ?, ?)';

    const values = [name, email, password, profileImageURL, role];

  db.query(sql, values, (err, result) => {
    if (err) {
      console.log('Error inserting user:', err);
      return callback(err, null);
    }
    callback(null, result);
  });
}

module.exports = { signUpUser };