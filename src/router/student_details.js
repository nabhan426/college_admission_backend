const { check, validationResult } = require('express-validator');
const db = require('../../database');

// Function to register or update student details
const registerStudentDetails = (req, res) => {
  // Validate request
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({ message: errors.array()[0].msg });
  }

  const { email, name, age, group_12th, marks_12th, course_required, mobile_no } = req.body;

  // Check if the email already exists in the users table
  const checkUserQuery = 'SELECT * FROM users WHERE email = ?';
  db.query(checkUserQuery, [email], (err, results) => {
    if (err) {
      console.error('Database query error:', err);
      return res.status(500).json({ message: 'Internal server error' });
    }

    if (results.length === 0) {
      // Email does not exist, create a new user with student details
      const insertUserQuery = `
        INSERT INTO users (email, name, age, group_12th, marks_12th, course_required, mobile_no)
        VALUES (?, ?, ?, ?, ?, ?, ?)
      `;
      db.query(insertUserQuery, [email, name, age, group_12th, marks_12th, course_required, mobile_no], (err, result) => {
        if (err) {
          console.error('Database insertion error:', err);
          return res.status(500).json({ message: 'Internal server error' });
        }

        res.status(201).json({ message: 'User and student details registered successfully' });
      });
    } else {
      // Email exists, update the existing user's student details
      const updateUserQuery = `
        UPDATE users
        SET name = ?, age = ?, group_12th = ?, marks_12th = ?, course_required = ?, mobile_no = ?
        WHERE email = ?
      `;
      db.query(updateUserQuery, [name, age, group_12th, marks_12th, course_required, mobile_no, email], (err, result) => {
        if (err) {
          console.error('Database update error:', err);
          return res.status(500).json({ message: 'Internal server error' });
        }

        res.status(200).json({ message: 'Student details updated successfully' });
      });
    }
  });
};

module.exports = { registerStudentDetails };
