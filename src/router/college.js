const { check, validationResult } = require('express-validator');
const db = require('../../database');

// Function to register a new college
const registerNewCollege = (req, res) => {
  // Validate request
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({ message: errors.array()[0].msg });
  }

  const { college_name } = req.body;

  // Check if the college is already registered
  const checkCollegeQuery = `SELECT * FROM colleges WHERE college_name = ?`;
  db.query(checkCollegeQuery, [college_name], (err, result) => {
    if (err) {
      console.error('Database query error:', err);
      return res.status(500).json({ message: 'Internal server error' });
    }

    if (result.length > 0) {
      return res.status(400).json({ message: 'College is already registered' });
    }

    // Insert the new college into the colleges table
    const insertCollegeQuery = `INSERT INTO colleges (college_name) VALUES (?)`;
    db.query(insertCollegeQuery, [college_name], (err, result) => {
      if (err) {
        console.error('Database insert error:', err);
        return res.status(500).json({ message: 'Internal server error' });
      }

      res.status(201).json({ message: 'College registered successfully', college_id: result.insertId ,college_name: college_name });
    });
  });
};



// Function to check if a college is registered
const ChangeCollegeDetails = (req, res) => {
  // Validate request
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
      return res.status(400).json({ message: errors.array()[0].msg });
  }

  const { college_name } = req.body;

  // Query to check if the college is registered
  const checkCollegeQuery = `SELECT college_id, college_name FROM colleges WHERE college_name = ?`;
  db.query(checkCollegeQuery, [college_name], (err, result) => {
      if (err) {
          console.error('Database query error:', err);
          return res.status(500).json({ message: 'Internal server error' });
      }

      if (result.length > 0) {
          // College is registered; return college_id and college_name
          const { college_id, college_name } = result[0];
          return res.status(200).json({ 
              message: 'Change Now', 
              college_id, 
              college_name 
          });
      } else {
          // College is not registered
          return res.status(404).json({ message: 'College is not registered' });
      }
  });
};







// Function to register or update college details
const registerCollegeDetails = (req, res) => {
  // Validate request
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({ message: errors.array()[0].msg });
  }

  const { college_id, college_name, mark, batch, courses, degree } = req.body;
  const course = courses;
  

  // Check if the college_id already exists in the colleges table
  const updateCollegeQuery = `
    UPDATE colleges
    SET college_name = ?, mark = ?, batch = ?, course = ?, degree = ?
    WHERE college_id = ?
  `;
  db.query(updateCollegeQuery, [college_name, mark, batch, course, degree, college_id], (err, result) => {
    if (err) {
      console.error('Database update error:', err);
      return res.status(500).json({ message: 'Internal server error' });
    }

    if (result.affectedRows === 0) {
      return res.status(404).json({ message: 'College not found' });
    }

    res.status(200).json({ message: 'College details updated successfully' });
  });
};

module.exports = { registerNewCollege,ChangeCollegeDetails,registerCollegeDetails };