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

app.get("/delete_user", (req, res) => {
    res.send(`<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Account</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f4f4f9;
        }
        .container {
            background-color: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            width: 400px;
            text-align: center;
        }
        input[type="text"], input[type="email"], input[type="password"] {
            width: 90%;
            padding: 15px;
            margin: 15px 0;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 16px;
        }
        button {
            background-color: #ff4d4d;
            color: white;
            border: none;
            padding: 12px 25px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #e43f3f;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Delete Account</h2>
        <form id="deleteForm" onsubmit="return handleDelete(event)">
            <input type="text" id="name" placeholder="Enter your name" required>
            <input type="email" id="email" placeholder="Enter your email" required>
            <input type="password" id="password" placeholder="Enter your password" required>
            <button type="submit">Delete Account</button>
        </form>
    </div>

    <script>
        function handleDelete(event) {
            event.preventDefault(); // Prevent default form submission
            document.body.innerHTML = "<h1 style='text-align: center; margin-top: 20%; font-family: Arial, sans-serif;'>Your account will be deleted in a few days.</h1>";
        }
    </script>
</body>
</html>
`);
  });


app.get("/privacy_policy", (req, res) => {
  res.send(`<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Privacy Policy - Campus Connect</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 20px;
            color: #333;
            background-color: #f9f9f9;
        }

        h1, h2, h3, h4 {
            color: #0047ab;
        }

        h1 {
            text-align: center;
            text-transform: uppercase;
            border-bottom: 4px solid #0047ab;
            padding-bottom: 10px;
        }

        h2 {
            margin-top: 30px;
            border-bottom: 2px solid #0047ab;
            padding-bottom: 5px;
        }

        h3 {
            color: #0056b3;
        }

        h4 {
            color: #006bb6;
        }

        p, ul {
            background: #ffffff;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 15px;
            margin-bottom: 20px;
        }

        ul {
            list-style-type: square;
            margin-left: 20px;
        }

        ul li {
            margin-bottom: 10px;
        }

        a {
            color: #0047ab;
            text-decoration: none;
            font-weight: bold;
        }

        a:hover {
            text-decoration: underline;
        }

        .contact {
            background-color: #f2f2f2;
            border: 1px solid #ccc;
            padding: 15px;
            border-radius: 5px;
        }

        .contact ul {
            list-style-type: none;
            padding: 0;
        }

        .contact ul li {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

    <h1>Privacy Policy</h1>
    <p>Last updated: January 23, 2025</p>
    <p>
        Welcome to **Campus Connect**, your trusted platform to check your eligibility for college applications 
        and explore courses and colleges. We value your privacy and are committed to safeguarding your personal 
        information.
    </p>

    <p>This Privacy Policy outlines:</p>
    <ul>
        <li>What data we collect and how we use it.</li>
        <li>How we store and secure your data.</li>
        <li>Your rights and choices regarding your data.</li>
        <li>How you can contact us for questions or concerns.</li>
    </ul>

    <h2>Data We Collect</h2>
    <h3>1. Personal Information</h3>
    <p>When you create an account or use Campus Connect, we may collect the following information:</p>
    <ul>
        <li>Your full name</li>
        <li>Email address</li>
        <li>Phone number</li>
        <li>12th grade marks and percentage</li>
        <li>Preferred colleges and courses</li>
    </ul>

    <h3>2. Usage Data</h3>
    <p>
        Usage Data is automatically collected when you interact with our app. This data includes:
    </p>
    <ul>
        <li>Your device's IP address</li>
        <li>Browser type and version</li>
        <li>Time and date of app access</li>
        <li>Pages visited within the app</li>
    </ul>

    <h3>3. Eligibility Check Information</h3>
    <p>
        When you check your eligibility, we temporarily process your academic details and the requirements of 
        the colleges you are interested in. This data is used solely to determine your eligibility and is not 
        stored permanently.
    </p>

    <h2>How We Use Your Data</h2>
    <p>We use the data we collect for the following purposes:</p>
    <ul>
        <li>To allow you to search for colleges and courses based on your eligibility.</li>
        <li>To provide personalized recommendations for colleges and programs.</li>
        <li>To improve the app's features and user experience.</li>
        <li>To communicate with you about updates, changes, or support inquiries.</li>
    </ul>

    <h2>Data Security</h2>
    <p>
        We prioritize the security of your personal information. All data is encrypted during transmission, 
        and sensitive information such as your grades and contact details are stored securely. We regularly 
        review our security practices to ensure your data is protected.
    </p>

    <h2>Your Rights</h2>
    <p>As a user, you have the following rights:</p>
    <ul>
        <li>Access: You can request a copy of the personal data we hold about you.</li>
        <li>Correction: You can request corrections to inaccurate or incomplete data.</li>
        <li>Deletion: You can request the deletion of your personal data.</li>
        <li>Withdrawal of Consent: You can withdraw your consent for data processing at any time.</li>
    </ul>

    <h2>Third-Party Services</h2>
    <p>
        We may use third-party services to enhance your experience, such as hosting providers or analytics tools. 
        These services may have access to limited data solely for the purpose of performing their tasks. They are 
        contractually obligated to protect your data.
    </p>

    <h2>Children's Privacy</h2>
    <p>
        Our app is not intended for children under the age of 13. We do not knowingly collect personal data from 
        children. If we become aware that a child under 13 has provided us with personal data, we will delete it 
        promptly.
    </p>

    <h2>Changes to This Privacy Policy</h2>
    <p>
        We may update our Privacy Policy from time to time. Any changes will be posted on this page, and we encourage 
        you to review this policy periodically for updates.
    </p>

    <h2>Contact Us</h2>
    <p>If you have any questions or concerns about this Privacy Policy or the way we handle your data, please contact us:</p>
    <div class="contact">
        <ul>
            <li>By email: <a href="mailto:mohamednabhan5009.sse@saveetha.com">mohamednabhan5009.sse@saveetha.com</a></li>
            <li>Through the app: Use the "Contact Us" section in the settings menu.</li>
        </ul>
    </div>

</body>
</html>
`);
});


// Routers
const userRoutes = require('./src/router/user_router'); // Ensure the path points to user_router.js
app.use("/api", userRoutes);

// Start the server
app.listen(port, '0.0.0.0', () => {
  console.log(`Server running on http://localhost:${port}`);
});
