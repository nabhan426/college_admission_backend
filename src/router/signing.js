const bcrypt = require('bcryptjs');
const { check, validationResult } = require('express-validator');
const db = require('../../database');

// Signup Function
const Signup = (req, res) => {
  console.log('Request body:', req.body);

  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    console.error('Validation error:', errors.array()[0].msg);
    return res.status(400).json({ message: errors.array()[0].msg });
  }

  const { name, email, password } = req.body;

  if (!name || !email || !password) {
    return res.status(400).json({ message: 'All fields are required' });
  }

  const checkEmailQuery = 'SELECT * FROM users WHERE email = ?';
  db.query(checkEmailQuery, [email], (err, results) => {
    if (err) {
      console.error('Database error during email check:', err);
      return res.status(500).json({ message: 'Internal server error' });
    }

    if (results.length > 0) {
      console.log('Email already registered:', email);
      return res.status(409).json({ message: 'Email already registered' });
    }

    bcrypt.hash(password, 10, (err, hashedPassword) => {
      if (err) {
        console.error('Hashing error:', err);
        return res.status(500).json({ message: 'Password hashing failed' });
      }

      const insertUserQuery = 'INSERT INTO users (name, email, password) VALUES (?, ?, ?)';
      db.query(insertUserQuery, [name, email, hashedPassword], (err, result) => {
        if (err) {
          console.error('Database error during user insert:', err);
          return res.status(500).json({ message: 'Internal server error' });
        }
        console.log('User created successfully:', result.insertId);
        res.status(201).json({ 
          message: 'User created successfully',
          userId: result.insertId, // Returning userId for reference
          name:name
        });
      });
    });
  });
};

// Signin Function
const Signin = (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({ message: errors.array()[0].msg });
  }

  const { email, password } = req.body;

  if (!email || !password) {
    return res.status(400).json({ message: 'Email and password are required' });
  }

  const checkEmailQuery = 'SELECT * FROM users WHERE email = ?';
  db.query(checkEmailQuery, [email], (err, results) => {
    if (err) {
      console.error('Database error during email check:', err);
      return res.status(500).json({ message: 'Internal server error' });
    }

    if (results.length === 0) {      return res.status(404).json({ message: 'Email does not exist' });
    }

    const user = results[0];
    bcrypt.compare(password, user.password, (err, isMatch) => {
      if (err) {
        console.error('Password comparison error:', err);
        return res.status(500).json({ message: 'Internal server error' });
      }

      if (!isMatch) {
        return res.status(401).json({ message: 'Incorrect password' });
      }

      // Returning userId along with user type
      res.status(200).json({
        message: 'Signin successful',
        userId: user.id, // Assuming the database has an `id` field
        name:user.name,
        marks_12th: user.marks_12th,
        userType: user.user_type === 'admin' ? 'admin' : 'user'
      });
    });
  });
};

// Reset Password Function
const Reset = (req, res) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({ message: errors.array()[0].msg });
  }

  const { email, newPassword } = req.body;

  if (!email || !newPassword) {
    return res.status(400).json({ message: 'Email and new password are required' });
  }

  const checkEmailQuery = 'SELECT * FROM users WHERE email = ?';
  db.query(checkEmailQuery, [email], (err, results) => {
    if (err) {
      console.error('Database error during email check:', err);
      return res.status(500).json({ message: 'Internal server error' });
    }

    if (results.length === 0) {
      return res.status(404).json({ message: 'Email does not exist' });
    }

    bcrypt.hash(newPassword, 10, (err, hashedPassword) => {
      if (err) {
        console.error('Password hashing error:', err);
        return res.status(500).json({ message: 'Password hashing failed' });
      }

      const updatePasswordQuery = 'UPDATE users SET password = ? WHERE email = ?';
      db.query(updatePasswordQuery, [hashedPassword, email], (err, result) => {
        if (err) {
          console.error('Database error during password update:', err);
          return res.status(500).json({ message: 'Internal server error' });
        }

        res.status(200).json({ message: 'Password reset successfully' });
      });
    });
  });
};

module.exports = { Signup, Signin, Reset };
