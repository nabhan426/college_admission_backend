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

public class Addfee_structure extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addfee_structure);
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
        // Retrieve all EditText fields
        EditText editText1 = findViewById(R.id.textView76);
        EditText editText2 = findViewById(R.id.textView77);
        EditText editText3 = findViewById(R.id.textView78);
        EditText editText4 = findViewById(R.id.textView79);
        EditText editText5 = findViewById(R.id.textView80);
        EditText editText6 = findViewById(R.id.textView81);
        EditText editText7 = findViewById(R.id.textView82);

        // Check if all EditText fields are filled
        if (isFieldEmpty(editText1) || isFieldEmpty(editText2) || isFieldEmpty(editText3) ||
                isFieldEmpty(editText4) || isFieldEmpty(editText5) || isFieldEmpty(editText6) || isFieldEmpty(editText7)) {
            Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Changes made successfully.", Toast.LENGTH_SHORT).show();

            // Optionally, navigate to the next activity if required
            Intent i = new Intent(this, Completed.class);
            startActivity(i);
        }
    }

    // Helper method to check if an EditText is empty
    private boolean isFieldEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }
}
