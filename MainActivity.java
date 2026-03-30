package com.vighnesh.smarteligibilitychecker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etAge, etIncome;
    Spinner spCategory, spEducation, spOccupation, spGender;
    Button btnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI
        etAge = findViewById(R.id.etAge);
        etIncome = findViewById(R.id.etIncome);
        spCategory = findViewById(R.id.spCategory);
        spEducation = findViewById(R.id.spEducation);
        spOccupation = findViewById(R.id.spOccupation);
        spGender = findViewById(R.id.spGender);
        btnCheck = findViewById(R.id.btnCheck);

        // Dropdown Data
        String[] categories = {"Select Category","General", "OBC", "SC", "ST", "EWS"};
        String[] education = {"Select Education","Below 10th", "10th", "12th", "Undergraduate", "Graduate"};
        String[] occupation = {"Select Occupation","Student", "Undergraduate", "Farmer", "Business", "Entrepreneur"};
        String[] gender = {"Select Gender","Any", "Male", "Female"};

        // Set Adapters
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, gender);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(genderAdapter);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(categoryAdapter);

        ArrayAdapter<String> educationAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, education);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEducation.setAdapter(educationAdapter);

        ArrayAdapter<String> occupationAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, occupation);
        occupationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOccupation.setAdapter(occupationAdapter);

        // Button Click → Go to Result Screen
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ageStr = etAge.getText().toString();
                String incomeStr = etIncome.getText().toString();

                if(ageStr.isEmpty() || incomeStr.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (spGender.getSelectedItemPosition() == 0 ||
                        spCategory.getSelectedItemPosition() == 0 ||
                        spEducation.getSelectedItemPosition() == 0 ||
                        spOccupation.getSelectedItemPosition() == 0) {

                    Toast.makeText(MainActivity.this, "Please select all fields properly", Toast.LENGTH_SHORT).show();
                    return;
                }
                int age = Integer.parseInt(ageStr);
                int income = Integer.parseInt(incomeStr);

                String category = spCategory.getSelectedItem().toString();
                String occupation = spOccupation.getSelectedItem().toString();
                String gender = spGender.getSelectedItem().toString();
                String education = spEducation.getSelectedItem().toString();

                // Send data to ResultActivity
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("age", age);
                intent.putExtra("income", income);
                intent.putExtra("category", category);
                intent.putExtra("occupation", occupation);
                intent.putExtra("gender", gender);
                intent.putExtra("education", education);

                startActivity(intent);
            }
        });
    }
}