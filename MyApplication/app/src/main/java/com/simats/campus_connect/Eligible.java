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

import com.simats.campus_connect.utils.sf;

public class Eligible extends AppCompatActivity {

    TextView viewCourseDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eligible);

        String userId = sf.getLoginSF(Eligible.this).getString(sf.userId, null);
        String name = sf.getLoginSF(Eligible.this).getString(sf.name, null);

        // Get the marks_12th from SharedPreferences
        String marks_12th = sf.getLoginSF(Eligible.this).getString(sf.marks_12th, null);

// Convert the marks_12th string to a number (assuming it is a string representation of an integer)
        int marks = 0;
        if (marks_12th != null) {
            marks = Integer.parseInt(marks_12th);
        }

// Find the TextView
        TextView eligibilityTextView = findViewById(R.id.textView3);

// Check if marks are less than 250
        if (marks < 250) {
            eligibilityTextView.setText("You Are Not Eligible");
        } else {
            eligibilityTextView.setText("You Are Eligible 🎉🎉🎉");
        }


        viewCourseDetail  = findViewById(R.id.textView61);
        Intent course_details = getIntent();
        String domain = course_details.getStringExtra("domain");
        String course = course_details.getStringExtra("course");
        String degree = course_details.getStringExtra("degree");
        String batch = course_details.getStringExtra("batch");

        viewCourseDetail.setOnClickListener(v -> {
            Intent intent = new Intent(this, Course_details.class);
            intent.putExtra("domain", domain);
            intent.putExtra("course", course);
            intent.putExtra("degree",degree);
            intent.putExtra("batch", batch);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }




    public void homepage(View v) {
        Intent i = new Intent(this, Homepage.class);
        startActivity(i);
    }


    public void personal(View v){
        Intent i = new Intent(this, Personal.class);
        startActivity(i);
    }
    public void course_details(View v) {
        Intent i = new Intent(this, Course_details.class);
        startActivity(i);
    }

    public void fee_structure(View v){
        Intent i = new Intent(this, Fee_structure.class);
        startActivity(i);
    }
    public void faculty_ratio(View v){
        Intent i = new Intent(this, Faculty_ratio.class);
        startActivity(i);
    }
    public void course_syllabus(View v){
        Intent i = new Intent(this, Course_syllabus.class);
        startActivity(i);
    }

    public void saveetha(View v){
        Intent i = new Intent(this, Saveetha.class);
        startActivity(i);
    }
 }



