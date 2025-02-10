package com.simats.campus_connect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Moredetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_moredetails);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void collegedetails(View v) {
        Intent i = new Intent(this, Collegedetails.class);
        startActivity(i);
    }

    public void course_details(View v) {
        Intent i = new Intent(this, Addcourse_details.class);
        startActivity(i);
    }


    public void fee_structure(View v){
        Intent i = new Intent(this, Addfee_structure.class);
        startActivity(i);
    }
    public void faculty_ratio(View v){
        Intent i = new Intent(this, Addfaculty_ratio.class);
        startActivity(i);
    }
    public void course_syllabus(View v){
        Intent i = new Intent(this, Addcourse_syllabus.class);
        startActivity(i);
    }
}