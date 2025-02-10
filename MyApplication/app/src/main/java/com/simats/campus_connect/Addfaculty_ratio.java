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

public class Addfaculty_ratio extends AppCompatActivity {

    private EditText editText1, editText2, editText3, editText4, editText5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addfaculty_ratio);

        // Initialize EditText fields
        editText1 = findViewById(R.id.textView83);
        editText2 = findViewById(R.id.textView84);
        editText3 = findViewById(R.id.textView85);
        editText4 = findViewById(R.id.textView86);
        editText5 = findViewById(R.id.textView87);

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
        // Check if all EditText fields are filled
        if (isAllFieldsFilled()) {
            Toast.makeText(this, "Changes made successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Completed.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isAllFieldsFilled() {
        return !editText1.getText().toString().trim().isEmpty() &&
                !editText2.getText().toString().trim().isEmpty() &&
                !editText3.getText().toString().trim().isEmpty() &&
                !editText4.getText().toString().trim().isEmpty() &&
                !editText5.getText().toString().trim().isEmpty();
    }
}
