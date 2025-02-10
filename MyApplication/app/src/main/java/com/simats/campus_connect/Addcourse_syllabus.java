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

public class Addcourse_syllabus extends AppCompatActivity {

    private EditText[] editTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addcourse_syllabus);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize EditText fields
        editTexts = new EditText[]{
                findViewById(R.id.textView88),
                findViewById(R.id.textView89),
                findViewById(R.id.textView90),
                findViewById(R.id.textView91),
                findViewById(R.id.textView92),
                findViewById(R.id.textView93),
                findViewById(R.id.textView94),
                findViewById(R.id.textView95),
                findViewById(R.id.textView96),
                findViewById(R.id.textView97),
                findViewById(R.id.textView98),
                findViewById(R.id.textView99)
        };
    }

    public void moredetails(View v) {
        Intent i = new Intent(this, Moredetails.class);
        startActivity(i);
    }

    public void completed(View v) {
        if (areAllFieldsFilled()) {
            Toast.makeText(this, "Changes made successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Completed.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean areAllFieldsFilled() {
        for (EditText editText : editTexts) {
            if (editText.getText().toString().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
