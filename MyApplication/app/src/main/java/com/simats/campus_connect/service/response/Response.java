package com.simats.campus_connect.service.response;

//this is for receiving from that page
public interface Response {

    // Response class for signup
    class SignUpResponse {
        private String message;

        private String userId;

        private String name;



        public String getMessage() {
            return message;
        }

        public String getUserId() { return userId; }

        public String getName() { return name; }


    }

    // Response class for signin
    class SigninResponse {
        private String message;
        private String userType;

        private String userId;

        private String name;

        private String marks_12th;

        public String getUserType() {
            return userType;
        }

        public String getMessage() {
            return message;
        }

        public String getMarks_12th() {
            return marks_12th;
        }



        public String getUserId() { return userId; }

        public String getName() { return name; }



    }

    // Response class for reset
    class ResetResponse {
        private String message;

        private String userId;

        public String getMessage() {
            return message;
        }

        public String getUserId() { return userId; }

    }

    // response class for student details register
    class RegisterResponse {
        private String message;

        private String userId;

        public String getMessage() {
            return message;
        }

        public String getUserId() { return userId; }
    }

    // response for new college register
    class Register_CollegeResponse {
        private String message;

        private String college_id;

        private String college_name;

        public String getMessage() { return message; }

        public String getCollege_name() { return college_name;}

        public String getCollegeId() { return college_id; }
    }

    // response for changing existing college details

    class Change_CollegeDetailsResponse {
        private String message;

        private String college_id;

        private String college_name;

        public String getMessage() { return message; }

        public String getCollegeId() { return college_id; }

        public String getCollege_name() { return college_name;}
    }

    // response for updating college details

    class Update_CollegeResponse {
        private String message;

        private String CollegeId;

        public String getMessage() {
            return message;
        }

        public String getCollegeId() { return CollegeId; }
    }


    // retrieving college names









}
