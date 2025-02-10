package com.simats.campus_connect.service.response;

//this is for giving to that page

public class MyRequest {

    // Signup Request
    public static class SignupRequest {
        private String name;
        private String email;
        private String password;

        public SignupRequest(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }
    }

    // Signin Request
    public static class SigninRequest {
        private String email;
        private String password;

        public SigninRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    // Reset Request
    public static class ResetRequest {
        private String email;
        private String newPassword;

        public ResetRequest(String email, String newPassword) {
            this.email = email;
            this.newPassword = newPassword;
        }

    }

    // student details register
    public static class RegisterRequest {
        private String name;
        private String age;
        private String group_12th;
        private String marks_12th;
        private String course_required;
        private String mobile_no;
        private String id; // Added id field

        public RegisterRequest(String name, String age, String group_12th, String marks_12th, String course_required, String mobile_no, String id) {
            this.name = name;
            this.age = age;
            this.group_12th = group_12th;
            this.marks_12th = marks_12th;
            this.course_required = course_required;
            this.mobile_no = mobile_no;
            this.id = id;
        }
    }

    // new college register
    public static class Register_CollegeRequest {
        private String college_name;

        public Register_CollegeRequest(String college_name) {
            this.college_name = college_name;
        }

    }

    // change college name

    public static class Change_CollegeDetailsRequest{
        private String college_name;


        public Change_CollegeDetailsRequest(String college_name) {
            this.college_name = college_name;

        }


    }

    // update the college details

    public static class Update_CollegeRequest {
        private String college_name;
        private Integer mark;

        private String batch;

        private String courses;
        private String degree;

        private String college_id; // Added id field





        public Update_CollegeRequest(String new_college_name, Integer mark,String batch,String courses, String degree,String college_id) {
            this.college_name = new_college_name;
            this.mark = mark;
            this.batch= batch;
            this.courses = courses;
            this.degree = degree;
            this.college_id = college_id;
        }
    }

    // retrieving college names
    public static class GetAllCollegesRequest {

        public GetAllCollegesRequest (){}// No fields needed as there are no user inputs for this request
    }

}
