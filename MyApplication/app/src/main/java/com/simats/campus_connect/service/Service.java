package com.simats.campus_connect.service;

import com.simats.campus_connect.service.response.MyRequest;
import com.simats.campus_connect.service.response.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Service {

    // Signup endpoint
    @POST("api/signup")
    Call<Response.SignUpResponse> signup(@Body MyRequest.SignupRequest request);

    // Signin endpoint
    @POST("api/signin")
    Call<Response.SigninResponse> signin(@Body MyRequest.SigninRequest request);

    // Reset password endpoint
    @POST("api/reset")
    Call<Response.ResetResponse> reset(@Body MyRequest.ResetRequest request);

    @POST("api/register")
    Call<Response.RegisterResponse> register(@Body MyRequest.RegisterRequest request);
    // Add this method



    @POST("api/register_college")
    Call<Response.Register_CollegeResponse> register_college(@Body MyRequest.Register_CollegeRequest request);

    @POST("api/update_college")
    Call<Response.Update_CollegeResponse> update_college(@Body MyRequest.Update_CollegeRequest request);

    @POST("api/change_collegeDetails")
    Call<Response.Change_CollegeDetailsResponse> change_collegeDetails(@Body MyRequest.Change_CollegeDetailsRequest request);

    @POST("api/GetAllColleges")
    Call<List<String>> GetAllColleges();





}
