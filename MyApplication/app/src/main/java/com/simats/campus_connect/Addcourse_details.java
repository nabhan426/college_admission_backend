package com.simats.campus_connect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Addcourse_details extends AppCompatActivity {

    private EditText courseEditText, degreeEditText, batchEditText, coreSubjectsEditText, electivesEditText, professionalCertificatesEditText, conferencesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addcourse_details);

        // Initialize EditText fields
        courseEditText = findViewById(R.id.textView70);
        degreeEditText = findViewById(R.id.textView69);
        batchEditText = findViewById(R.id.textView71);
        coreSubjectsEditText = findViewById(R.id.textView74);
        electivesEditText = findViewById(R.id.textView73);
        professionalCertificatesEditText = findViewById(R.id.textView75);
        conferencesEditText = findViewById(R.id.textView72);

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void moredetails(View v) {
        Intent i = new Intent(this, Moredetails.class);
        startActivity(i);
    }

    public void completed(View v) {
        // Get text from EditText fields
        String course = courseEditText.getText().toString().trim();
        String degree = degreeEditText.getText().toString().trim();
        String batch = batchEditText.getText().toString().trim();
        String coreSubjects = coreSubjectsEditText.getText().toString().trim();
        String electives = electivesEditText.getText().toString().trim();
        String professionalCertificates = professionalCertificatesEditText.getText().toString().trim();
        String conferences = conferencesEditText.getText().toString().trim();

        // Check if any field is empty
        if (course.isEmpty() || degree.isEmpty() || batch.isEmpty() || coreSubjects.isEmpty()
                || electives.isEmpty() || professionalCertificates.isEmpty() || conferences.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show a confirmation message
        String confirmationMessage = "Details submitted successfully";


        Toast.makeText(this, confirmationMessage, Toast.LENGTH_LONG).show();

        // Optionally navigate to another activity
        Intent i = new Intent(this, Completed.class);
        startActivity(i);
    }
}
