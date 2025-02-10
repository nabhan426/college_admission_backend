package com.simats.campus_connect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Course_details extends AppCompatActivity {

    private TextView domainTextView, courseTextView, degreeTextView, batchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize TextViews

        courseTextView = findViewById(R.id.courseTextView);
        degreeTextView = findViewById(R.id.degreeTextView);
        batchTextView = findViewById(R.id.batchTextView);

        // Retrieve data from Intent

        Intent course_details = getIntent();
        String domain = course_details.getStringExtra("domain");
        String course = course_details.getStringExtra("course");
        String degree = course_details.getStringExtra("degree");
        String batch = course_details.getStringExtra("batch");

        // Display retrieved data in TextViews

        courseTextView.setText(courseTextView.getText() + " " + course);
        degreeTextView.setText(degreeTextView.getText() + " " + degree);
        batchTextView.setText(batchTextView.getText() + " " + batch);
    }

    // Navigation methods
    public void homepage(View v) {
        Intent i = new Intent(this, Homepage.class);
        startActivity(i);
    }



    public void personal(View v) {
        Intent i = new Intent(this, Personal.class);
        startActivity(i);
    }

    public void eligible(View v) {
        Intent i = new Intent(this, Eligible.class);
        startActivity(i);
    }
}
