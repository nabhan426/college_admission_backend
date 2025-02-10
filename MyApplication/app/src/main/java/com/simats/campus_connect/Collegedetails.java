package com.simats.campus_connect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

public class Collegedetails extends AppCompatActivity {

    private TextView userIdTextView;
    private EditText college_nameField, batchField, coursesField, degreeField, markField;
    private Button addmoredetailsButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_collegedetails);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        userIdTextView = findViewById(R.id.userIdTextView);
        college_nameField = findViewById(R.id.college_name);
        batchField = findViewById(R.id.batch);
        coursesField = findViewById(R.id.courses);
        degreeField = findViewById(R.id.degree);
        markField = findViewById(R.id.mark);
        addmoredetailsButton = findViewById(R.id.addmoredetails);

        // Retrieve data from SharedPreferences
        String college_id = sf.getLoginSF(Collegedetails.this).getString(sf.college_id, null);
        String old_college_name = sf.getLoginSF(Collegedetails.this).getString(sf.college_name, null);

        if (college_id != null && !college_id.isEmpty()) {
            userIdTextView.setText(old_college_name);
            Log.d("Collegedetails", "College ID retrieved: " + old_college_name);
        } else {
            Toast.makeText(this, "College ID not found! Please type it again.", Toast.LENGTH_SHORT).show();
            Log.e("Collegedetails", "College ID is null or empty");
        }

        // Set click listener for the update button
        addmoredetailsButton.setOnClickListener(v -> {
            String college_name = college_nameField.getText().toString().trim();
            String batch = batchField.getText().toString().trim();
            String courses = coursesField.getText().toString().trim();
            String degree = degreeField.getText().toString().trim();

            // Validate input fields
            if (college_name.isEmpty()) {
                Toast.makeText(this, "College name is required!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (batch.isEmpty()) {
                Toast.makeText(this, "Batch is required!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (courses.isEmpty()) {
                Toast.makeText(this, "Courses are required!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (degree.isEmpty()) {
                Toast.makeText(this, "Degree is required!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Parse and validate mark
            Integer mark;
            try {
                mark = Integer.parseInt(markField.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Mark must be a valid number!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Proceed with your logic here



        // Make API call
            apiCall(college_name,mark, batch, courses, degree, college_id);
        });
    }

    private void apiCall(String college_name,Integer mark, String batch, String courses, String degree, String college_id) {
        MyRequest.Update_CollegeRequest request = new MyRequest.Update_CollegeRequest(college_name, mark , batch , courses, degree, college_id);
        retrofit2.Call<Response.Update_CollegeResponse> apiCall = RestClient.makeAPI().update_college(request);

        apiCall.enqueue(new retrofit2.Callback<Response.Update_CollegeResponse>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<Response.Update_CollegeResponse> call, @NonNull retrofit2.Response<Response.Update_CollegeResponse> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(Collegedetails.this, Completed.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Collegedetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Collegedetails.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Collegedetails.this, "JSON Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<Response.Update_CollegeResponse> call, @NonNull Throwable t) {
                Toast.makeText(Collegedetails.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("UpdateCollege", "API call failed: ", t);
            }

        });
    }
    public void moredetails(View v){
        Intent i = new Intent(this, Moredetails.class);
        startActivity(i);

    }
}
