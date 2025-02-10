const { check, validationResult } = require('express-validator');
const db = require('../../database');

// Function to retrieve all registered colleges
const GetAllColleges = (req, res) => {
    const getCollegesQuery = `SELECT college_name FROM colleges`;
  
    db.query(getCollegesQuery, (err, results) => {
      if (err) {
        console.error('Database query error:', err);
        return res.status(500).json({ message: 'Internal server error' });
      }
  
      // Map results to an array of college names
      const collegeNames = results.map((row) => row.college_name);
  
      res.status(200).json(collegeNames);
    });
  };
  

module.exports = { GetAllColleges };