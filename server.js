const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const morgan = require('morgan');
require('dotenv').config();

const app = express();
const port = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(bodyParser.json());
app.use(morgan('dev'));

// MySQL connection setup
require('./database');

// API route for the home page
app.get("/", (req, res) => {
  res.send("Welcome to Campus Connect");
});

// Routers
const userRoutes = require('./src/router/user_router'); // Ensure the path points to user_router.js
app.use("/api", userRoutes);

// Start the server
app.listen(port, '0.0.0.0', () => {
  console.log(`Server running on http://localhost:${port}`);
});
