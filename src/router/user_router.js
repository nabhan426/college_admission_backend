const express = require('express');
const { check } = require('express-validator');
const router = express.Router();
const { Signup, Signin, Reset } = require('../router/signing');
const { registerStudentDetails } = require('../router/student_details');
const {  registerNewCollege,registerCollegeDetails,ChangeCollegeDetails } = require('../router/college');
const { GetAllColleges } = require('../router/homepage');



// Signup Route
router.post('/signup', [
  check('name').notEmpty().withMessage('Name is required'),
  check('email').isEmail().withMessage('Valid email is required'),
  check('password').isLength({ min: 6 }).withMessage('Password must be at least 6 characters long')
], Signup);

// Signin Route
router.post('/signin', [
  check('email').isEmail().withMessage('Valid email is required'),
  check('password').notEmpty().withMessage('Password is required')
], Signin);

// Reset Password Route
router.post('/reset', [
  check('email').isEmail().withMessage('Valid email is required'),
  check('newPassword').isLength({ min: 6 }).withMessage('Password must be at least 6 characters long')
], Reset);

// Student Details Registration Route
router.post('/register', [
  check('id').notEmpty().withMessage('Valid id is required'),
  check('name').notEmpty().withMessage('Name is required'),
  check('age').isInt({ min: 10 }).withMessage('Age must be a valid number and at least 10'),
  check('group_12th').notEmpty().withMessage('Group 12th is required'),
  check('marks_12th').isNumeric().withMessage('Marks 12th must be a valid number'),
  check('course_required').notEmpty().withMessage('Course required is mandatory'),
  check('mobile_no').isMobilePhone().withMessage('Valid mobile number is required')
], registerStudentDetails);

// new college
router.post('/register_college', [
  check('college_name')
    .notEmpty()
    .withMessage('College name is required')
], registerNewCollege);



// changing details for colleges

router.post('/change_collegeDetails', [
  check('college_name')
    .notEmpty()
    .withMessage('College name is required')
], ChangeCollegeDetails);


// updating college details

router.post('/update_college', [
  check('college_id')
    .isInt()
    .withMessage('Valid college ID is required'),
  check('college_name')
    .notEmpty()
    .withMessage('College name is required'),
  check('mark')
    .isNumeric()
    .optional()
    .withMessage('Mark must be a valid number'),
  check('batch')
    .notEmpty()
    .optional()
    .withMessage('Batch is required'),
  check('course')
    .notEmpty()
    .optional()
    .withMessage('course is required'),
  check('degree')
    .notEmpty()
    .optional()
    .withMessage('Degree is required')
], registerCollegeDetails);


router.post('/GetAllColleges', GetAllColleges);





module.exports = router;


