const { check, validationResult } = require('express-validator');
const db = require('../../database');

// Function to register or update student details
const registerStudentDetails = (req, res) => {
  // Validate request
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({ message: errors.array()[0].msg });
  }

  const { id, name, age, group_12th, marks_12th, course_required, mobile_no } = req.body;

  // Check if the id already exists in the users table
      const updateUserQuery = `
      UPDATE users
      SET name = ?, age = ?, group_12th = ?, marks_12th = ?, course_required = ?, mobile_no = ?
      WHERE id = ?
    `;
    db.query(updateUserQuery, [name, age, group_12th, marks_12th, course_required, mobile_no, id], (err, result) => {
      if (err) {
        console.error('Database update error:', err);
        return res.status(500).json({ message: 'Internal server error' });
      }

      if(result.length == 0) return res.status(404).json({Message:"User not found"});

      res.status(200).json({ message: 'Student details updated successfully' });
    });
};

module.exports = { registerStudentDetails };
