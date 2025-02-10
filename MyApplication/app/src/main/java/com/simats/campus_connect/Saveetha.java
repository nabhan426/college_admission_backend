package com.simats.campus_connect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Spinner;

public class Saveetha extends AppCompatActivity {

    private Spinner domainSpinner;
    private Spinner courseSpinner;
    private Spinner degreeSpinner;
    private Spinner batchSpinner;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_saveetha);
        textView = findViewById(R.id.textView3);


        Intent intent = getIntent();
        String clgName = intent.getStringExtra("COLLEGE_NAME");
        if (clgName != null) {
            // Use the received college name
        }




        textView .setText(clgName);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize AutoCompleteTextViews
        domainSpinner = findViewById(R.id.domain);
        courseSpinner = findViewById(R.id.course);
        degreeSpinner = findViewById(R.id.degree);
        batchSpinner = findViewById(R.id.batch);

        // Options for the dropdown menus
        String[] domains = {"CSE", "ECE", "EEE", "BT", "Mechanical"};
        String[] courses = {"AIML", "AIDS", "CSE", "IT", "ECE", "Robotics", "Civil", "Mechanical", "BT"};
        String[] degrees = {"B.Tech", "B.E"};
        String[] batches = {"2025-2029", "2026-2030", "2027-2031", "2028-2032"};

        // Set adapters for AutoCompleteTextViews
        domainSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, domains));
        courseSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, courses));
        degreeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, degrees));
        batchSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, batches));
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

    public void eligible(View view) {
        // Get the selected values from each spinner
        String selectedDomain = domainSpinner.getSelectedItem().toString();
        String selectedCourse = courseSpinner.getSelectedItem().toString();
        String selectedDegree = degreeSpinner.getSelectedItem().toString();
        String selectedBatch = batchSpinner.getSelectedItem().toString();

        // Create an intent to start the Eligible activity
        Intent intent = new Intent(this, Eligible.class);

        // Pass the selected values using intent extras
        intent.putExtra("domain", selectedDomain);
        intent.putExtra("course", selectedCourse);
        intent.putExtra("degree", selectedDegree);
        intent.putExtra("batch", selectedBatch);

        // Start the Eligible activity
        startActivity(intent);
    }

}
