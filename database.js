const mysql = require('mysql2');

// Configuration for the database connection pool
const pool = mysql.createPool({
    host: process.env.DB_HOST || 'localhost',
    user: process.env.DB_USER || 'root',
    password: process.env.DB_PASSWORD || 'nabhan2004',
    database: process.env.DB_NAME || 'appdb',
    connectionLimit: 10, // Maximum number of connections in the pool
});

// Test the connection
pool.getConnection((err, connection) => {
    if (err) {
        console.error('MySQL Connection Error ->', err.message);
    } else {
        console.log('Connected to the MySQL database successfully');
        connection.release(); // Release the connection back to the pool
    }
});

// Export the pool for use in other parts of the application
module.exports = pool;
