package com.simats.campus_connect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.simats.campus_connect.databinding.ActivityHomepageBinding;
import com.simats.campus_connect.service.RestClient;
import com.simats.campus_connect.utils.sf;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Homepage extends AppCompatActivity {

    private TextView userIdTextView;
    ActivityHomepageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize UI elements
        userIdTextView = findViewById(R.id.userIdTextView);
        AutoCompleteTextView autoCompleteColleges = findViewById(R.id.autoCompleteColleges);
        Spinner dropdown = findViewById(R.id.dropdown_menu);
        LinearLayout buttonContainer = findViewById(R.id.button_container);
        Button sendButton = findViewById(R.id.Send);

        // Retrieve user data from SharedPreferences
        String userId = sf.getLoginSF(Homepage.this).getString(sf.userId, null);
        String name = sf.getLoginSF(Homepage.this).getString(sf.name, null);
        String marks_12th = sf.getLoginSF(Homepage.this).getString(sf.marks_12th, null);


        if (userId != null) {
            userIdTextView.setText("Welcome " + name);
            Log.d("Homepage", "User ID retrieved: " + name);
        } else {
            Toast.makeText(this, "User ID not found! Please sign in again.", Toast.LENGTH_SHORT).show();
            Log.e("Homepage", "User ID is null");
        }

        // Adjust for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        apicall();

        // Define dropdown menu items
        List<String> menuItems = new ArrayList<>();
        menuItems.add("Sort By : Eligibility");
        menuItems.add("Sort By : Fees");
        menuItems.add("Sort By : NIRF Ranking");
        menuItems.add("Sort By : Placement Percentage");
        menuItems.add("Sort By : Highest Package");

        // Set up adapter for Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, menuItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        // Dropdown selection listener
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                buttonContainer.removeAllViews(); // Clear previous buttons

                // Handle button creation based on the selected menu item
                List<String> colleges = new ArrayList<>();
                switch (position) {
                    case 0: // Sort By : Eligibility
                        colleges.add("Saveetha School of Engineering");
                        colleges.add("Vellore Institute of Technology");
                        colleges.add("Rajalakshmi Institute of Technology");
                        colleges.add("Chennai Institute of Technology");
                        break;
                    case 1: // Sort By : Fees
                        colleges.add("Vellore Institute of Technology");
                        colleges.add("Saveetha School of Engineering");
                        colleges.add("Chennai Institute of Technology");
                        colleges.add("Rajalakshmi Institute of Technology");
                        break;
                    case 2: // Sort By : NIRF Ranking
                        colleges.add("Chennai Institute of Technology");
                        colleges.add("Vellore Institute of Technology");
                        colleges.add("Saveetha School of Engineering");
                        colleges.add("Rajalakshmi Institute of Technology");
                        break;
                    case 3: // Sort By : Placement Percentage
                        colleges.add("Rajalakshmi Institute of Technology");
                        colleges.add("Chennai Institute of Technology");
                        colleges.add("Saveetha School of Engineering");
                        colleges.add("Vellore Institute of Technology");
                        break;
                    case 4: // Sort By : Highest Package
                        colleges.add("Vellore Institute of Technology");
                        colleges.add("Rajalakshmi Institute of Technology");
                        colleges.add("Chennai Institute of Technology");
                        colleges.add("Saveetha School of Engineering");
                        break;
                }

                // Create buttons for the selected colleges
                for (String college : colleges) {
                    createButton(college, buttonContainer, Saveetha.class);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                buttonContainer.removeAllViews();
            }
        });

        // Send button click listener
        sendButton.setOnClickListener(v -> {
            String selectedCollege = autoCompleteColleges.getText().toString();
            if (!selectedCollege.isEmpty()) {
                Intent intent = new Intent(Homepage.this, Saveetha.class);
                intent.putExtra("COLLEGE_NAME", selectedCollege);
                startActivity(intent);
            } else {
                Toast.makeText(Homepage.this, "Enter or select a college name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void apicall() {
        RestClient.makeAPI().GetAllColleges().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Homepage.this, android.R.layout.simple_dropdown_item_1line, response.body());
                    binding.autoCompleteColleges.setAdapter(adapter);
                } else {
                    Toast.makeText(Homepage.this, "No College Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                Toast.makeText(Homepage.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createButton(String text, LinearLayout container, Class<?> targetActivity) {
        Button button = new Button(this);
        button.setText(text);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 16, 0, 16);
        button.setLayoutParams(layoutParams);
        button.setPadding(16, 16, 16, 16);
        container.addView(button);

        // Set click listener to pass data to Saveetha.class
        button.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, targetActivity);
            intent.putExtra("COLLEGE_NAME", text);
            startActivity(intent);
        });
    }
    public void signin(View v){
        Intent i = new Intent(this, Signin.class);
        startActivity(i);
    }
}
