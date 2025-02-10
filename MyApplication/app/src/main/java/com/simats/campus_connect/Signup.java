package com.simats.campus_connect;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.simats.campus_connect.service.RestClient;
import com.simats.campus_connect.service.response.MyRequest;
import com.simats.campus_connect.service.response.Response;
import com.simats.campus_connect.utils.sf;

import org.json.JSONObject;

public class Signup extends AppCompatActivity {

    private EditText nameField, emailField, passwordField, confirmPasswordField;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI elements
        nameField = findViewById(R.id.name);
        emailField = findViewById(R.id.mail);
        passwordField = findViewById(R.id.pass);
        confirmPasswordField = findViewById(R.id.confirm);
        signupButton = findViewById(R.id.signup);

        // Set click listener for signup button
        signupButton.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            String confirmPassword = confirmPasswordField.getText().toString().trim();

            // Validate input fields
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(Signup.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(Signup.this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(Signup.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Proceed with signup
            apiCall(name, email, password);
        });
    }

    private void apiCall(String name, String email, String password) {
        MyRequest.SignupRequest request = new MyRequest.SignupRequest(name, email, password);
        retrofit2.Call<Response.SignUpResponse> apiCall = RestClient.makeAPI().signup(request);
        apiCall.enqueue(new retrofit2.Callback<Response.SignUpResponse>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<Response.SignUpResponse> call, retrofit2.Response<Response.SignUpResponse> response) {
                if(response.isSuccessful()) {
                    String userId = response.body().getUserId();
                    String name = response.body().getName();

                    sf.setLoginSFValue(Signup.this, sf.userId,userId);
                    sf.setLoginSFValue(Signup.this, sf.name,name);

                    Intent intent = new Intent(Signup.this, Studentdetails.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Signup.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Signup.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Signup.this, "Jason Exception "+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Response.SignUpResponse> call, Throwable t) {
                Toast.makeText(Signup.this,"onFailure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Signup", t.getMessage());
            }
        });
    }

    // Signin button click
    public void signin(View v) {
        Intent i = new Intent(this, Signin.class);
        startActivity(i);
    }

    // Move to Studentdetails page
    public void studentdetails(View v) {
        Intent i = new Intent(this, Studentdetails.class);
        startActivity(i);
    }

    // Go back to the MainActivity
    public void mainactivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
