package com.simats.campus_connect;

import android.annotation.SuppressLint;
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

import org.json.JSONObject;

public class Reset extends AppCompatActivity {

    private EditText emailField, newPasswordField, confirmPasswordField;
    private Button resetButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        // Initialize UI elements
        emailField = findViewById(R.id.mail);
        newPasswordField = findViewById(R.id.new_pass);
        confirmPasswordField = findViewById(R.id.confirm_pass);
        resetButton = findViewById(R.id.reset);

        // Set click listener for reset button
        resetButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String newPassword = newPasswordField.getText().toString().trim();
            String confirmPassword = confirmPasswordField.getText().toString().trim();

            // Validate input fields
            if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(Reset.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(Reset.this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(Reset.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newPassword.length() < 6) {
                Toast.makeText(Reset.this, "Password must be at least 6 characters long!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Proceed with password reset
            apiCall(email, newPassword);
        });
    }

    private void apiCall(String email, String newPassword) {
        MyRequest.ResetRequest request = new MyRequest.ResetRequest(email, newPassword);
        retrofit2.Call<Response.ResetResponse> apiCall = RestClient.makeAPI().reset(request);
        apiCall.enqueue(new retrofit2.Callback<Response.ResetResponse>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<Response.ResetResponse> call, retrofit2.Response<Response.ResetResponse> response) {
                if (response.isSuccessful()) {
                    // Notify success and navigate to Signin screen
                    Intent intent = new Intent(Reset.this, Signin.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Reset.this, "Password reset successful!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getString("message");

                        if ("Email does not exist".equalsIgnoreCase(errorMessage)) {
                            Toast.makeText(Reset.this, "Email ID does not exist!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Reset.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(Reset.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Response.ResetResponse> call, Throwable t) {
                Toast.makeText(Reset.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Reset", t.getMessage());
            }
        });
    }

    // Navigate back to Signin screen
    public void signin(View v) {
        Intent i = new Intent(this, Signin.class);
        startActivity(i);
    }

    public void changed(View v) {
        Intent i = new Intent(this, Homepage.class);
        startActivity(i);
    }


}
