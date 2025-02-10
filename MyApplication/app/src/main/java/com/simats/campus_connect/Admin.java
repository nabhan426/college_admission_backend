package com.simats.campus_connect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.simats.campus_connect.service.RestClient;
import com.simats.campus_connect.service.response.MyRequest;
import com.simats.campus_connect.service.response.Response;
import com.simats.campus_connect.utils.sf;
import org.json.JSONObject;

public class Admin extends AppCompatActivity {

    private EditText college_nameField, change_collegeField;
    private Button addButton, changeButton;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);


        // Initialize UI elements
        college_nameField = findViewById(R.id.college_name);
        change_collegeField = findViewById(R.id.change_college);
        addButton = findViewById(R.id.add);
        changeButton = findViewById(R.id.change);

        addButton.setOnClickListener(v -> {
            String college_name = college_nameField.getText().toString().trim();

            // Validate input fields
            if (college_name.isEmpty()) {
                Toast.makeText(Admin.this, college_name, Toast.LENGTH_SHORT).show();
                return;
            }

            // Proceed with college register
            apiCall(college_name);
        });
        changeButton.setOnClickListener(v -> {
            String college_name = change_collegeField.getText().toString();

            // Validate input fields
            if (college_name.isEmpty()) {
                Toast.makeText(Admin.this, "college name is empty "+college_name, Toast.LENGTH_SHORT).show();
                return;
            }

            // Proceed with college register
            apiCall2(college_name);
        });
    }


    private void apiCall(String college_name) {
        MyRequest.Register_CollegeRequest request = new MyRequest.Register_CollegeRequest(college_name);
        retrofit2.Call<Response.Register_CollegeResponse> apiCall = RestClient.makeAPI().register_college(request);
        apiCall.enqueue(new retrofit2.Callback<Response.Register_CollegeResponse>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<Response.Register_CollegeResponse> call, retrofit2.Response<Response.Register_CollegeResponse> response) {
                if (response.isSuccessful()) {
                    String CollegeId = response.body().getCollegeId();
                    String college_name = response.body().getCollege_name();
                    sf.setLoginSFValue(Admin.this, sf.college_id, CollegeId);
                    sf.setLoginSFValue(Admin.this, sf.college_name, college_name);
                    Intent intent = new Intent(Admin.this, Collegedetails.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Admin.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Admin.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Admin.this, "Jason Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Response.Register_CollegeResponse> call, Throwable t) {
                Toast.makeText(Admin.this,"onFailure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Signup", t.getMessage());
            }

        });
    }
    private void apiCall2(String change_college) {
        MyRequest.Change_CollegeDetailsRequest request = new MyRequest.Change_CollegeDetailsRequest(change_college);
        retrofit2.Call<Response.Change_CollegeDetailsResponse> apiCall = RestClient.makeAPI().change_collegeDetails(request);
        apiCall.enqueue(new retrofit2.Callback<Response.Change_CollegeDetailsResponse>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<Response.Change_CollegeDetailsResponse> call, retrofit2.Response<Response.Change_CollegeDetailsResponse> response) {
                if (response.isSuccessful()) {
                    String CollegeId = response.body().getCollegeId();
                    String change_college = response.body().getCollege_name();
                    sf.setLoginSFValue(Admin.this, sf.college_id, CollegeId);
                    sf.setLoginSFValue(Admin.this, sf.college_name, change_college);
                    Intent intent = new Intent(Admin.this, Collegedetails.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Admin.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Admin.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Admin.this, "Jason Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Response.Change_CollegeDetailsResponse> call, Throwable t) {
                Toast.makeText(Admin.this, "onFailure " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Signup", t.getMessage());
            }
        });
    }


}