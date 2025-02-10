package com.simats.campus_connect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.simats.campus_connect.service.RestClient;
import com.simats.campus_connect.service.response.MyRequest;
import com.simats.campus_connect.service.response.Response;
import com.simats.campus_connect.utils.sf;


import org.json.JSONObject;

public class Studentdetails extends AppCompatActivity {

    private EditText nameField, marks12thField, mobileNoField;
    private Spinner ageSpinner, group12thSpinner, courseRequiredSpinner;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentdetails);

        String userId = sf.getLoginSF(Studentdetails.this).getString(sf.userId, null);

        // Initialize UI elements
        nameField = findViewById(R.id.name);
        ageSpinner =findViewById(R.id.age);
        group12thSpinner = findViewById(R.id.group);
        marks12thField = findViewById(R.id.mark);
        courseRequiredSpinner = findViewById(R.id.course);
        mobileNoField = findViewById(R.id.number);
        updateButton = findViewById(R.id.submit);

        // Set up auto-complete for age
        String[] ages = {"17", "18", "19", "20", "21", "22"};
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, ages);
        ageSpinner.setAdapter(ageAdapter);

        // Set up auto-complete for 12th group
        String[] groups = {"PCMB", "Commerce", "Arts", "PCMC", "PCMI"};
        ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, groups);
        group12thSpinner.setAdapter(groupAdapter);

        // Set up auto-complete for course required
        String[] courses = {"AIML", "AIDS", "CSE", "Mechanical", "ECE", "EEE", "BT", "BM"};
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, courses);
        courseRequiredSpinner.setAdapter(courseAdapter);

        // Set padding for system bars (if using Edge-to-Edge mode)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set click listener for the update button
        updateButton.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String age = ageSpinner.getSelectedItem().toString().trim();
            String group12th = group12thSpinner.getSelectedItem().toString().trim();
            String marks12th = marks12thField.getText().toString().trim();
            String courseRequired = courseRequiredSpinner.getSelectedItem().toString().trim();
            String mobileNo = mobileNoField.getText().toString().trim();

            // Validate input fields
            if (name.isEmpty() || age.isEmpty() || group12th.isEmpty() || marks12th.isEmpty() || courseRequired.isEmpty() || mobileNo.isEmpty()) {
                Toast.makeText(Studentdetails.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Proceed with updating student details
            apiCall(name, age, group12th, marks12th, courseRequired, mobileNo, userId);
        });
    }

    private void apiCall(String name, String age, String group12th, String marks12th, String courseRequired, String mobileNo, String userId) {
        MyRequest.RegisterRequest request = new MyRequest.RegisterRequest(name, age, group12th, marks12th, courseRequired, mobileNo, userId);
        retrofit2.Call<Response.RegisterResponse> apiCall = RestClient.makeAPI().register(request);
        apiCall.enqueue(new retrofit2.Callback<Response.RegisterResponse>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<Response.RegisterResponse> call, @NonNull retrofit2.Response<Response.RegisterResponse> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(Studentdetails.this, Homepage.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Studentdetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Studentdetails.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Studentdetails.this, "JSON Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<Response.RegisterResponse> call, @NonNull Throwable t) {
                Toast.makeText(Studentdetails.this, "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Register", t.getMessage());
            }
        });
    }

    // Navigation methods
    public void signup(View v) {
        Intent i = new Intent(this, Signup.class);
        startActivity(i);
    }

    public void homepage(View v) {
        Intent i = new Intent(this, Homepage.class);
        startActivity(i);
    }



    public void personal(View v) {
        Intent i = new Intent(this, Personal.class);
        startActivity(i);
    }
}
