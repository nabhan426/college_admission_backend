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
import com.simats.campus_connect.utils.sf;
import org.json.JSONObject;

public class Signin extends AppCompatActivity {

    private EditText emailField, passwordField;
    private Button signinButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        // Initialize UI elements
        emailField = findViewById(R.id.mail);
        passwordField = findViewById(R.id.pass);
        signinButton = findViewById(R.id.signin);

        // Set click listener for signin button
        signinButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            // Validate input fields
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Signin.this, "Email and password are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(Signin.this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Proceed with signin
            apiCall(email, password);
        });
    }

    private void apiCall(String email, String password) {
        MyRequest.SigninRequest request = new MyRequest.SigninRequest(email, password);
        retrofit2.Call<Response.SigninResponse> apiCall = RestClient.makeAPI().signin(request);
        apiCall.enqueue(new retrofit2.Callback<Response.SigninResponse>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<Response.SigninResponse> call, retrofit2.Response<Response.SigninResponse> response) {
                if (response.isSuccessful()) {
                    // Extract userId and store it in SharedPreferences
                    String userId = response.body().getUserId();
                    String name = response.body().getName();
                    String mark = response.body().getMarks_12th();
                    sf.setLoginSFValue(Signin.this, sf.marks_12th,mark);
                    sf.setLoginSFValue(Signin.this, sf.userId,userId);
                    sf.setLoginSFValue(Signin.this, sf.name,name);

                    // Check user type from response
                    String userType = response.body().getUserType();

                    if ("admin".equalsIgnoreCase(userType)) {
                        // If user is admin, navigate to Admin page
                        Intent intent = new Intent(Signin.this, Admin.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(Signin.this, "Welcome Admin!", Toast.LENGTH_SHORT).show();
                    } else {
                        // If user is not admin, navigate to Homepage
                        Intent intent = new Intent(Signin.this, Homepage.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(Signin.this, "Signin successful!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getString("message");

                        if ("Incorrect password".equalsIgnoreCase(errorMessage)) {
                            Toast.makeText(Signin.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                        } else if ("Email does not exist".equalsIgnoreCase(errorMessage)) {
                            Toast.makeText(Signin.this, "Email ID does not exist!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Signin.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(Signin.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Response.SigninResponse> call, Throwable t) {
                Toast.makeText(Signin.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Signin", t.getMessage());
            }
        });
    }




    // Navigate to Reset Password Screen
    public void reset(View v) {
        Intent i = new Intent(this, Reset.class);
        startActivity(i);
    }

    // Navigate to Homepage
    public void homepage(View v) {
        Intent i = new Intent(this, Homepage.class);
        startActivity(i);
    }

    // Navigate to Signup Screen
    public void signup(View v) {
        Intent i = new Intent(this, Signup.class);
        startActivity(i);
    }
}
