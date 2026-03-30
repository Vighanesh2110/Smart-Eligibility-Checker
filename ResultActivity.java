package com.vighnesh.smarteligibilitychecker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TableLayout tableLayout;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tableLayout = findViewById(R.id.tableLayout);
        dbHelper = new DBHelper(this);

        int age = getIntent().getIntExtra("age", 0);
        int income = getIntent().getIntExtra("income", 0);
        String category = getIntent().getStringExtra("category");
        String occupation = getIntent().getStringExtra("occupation");
        String gender = getIntent().getStringExtra("gender");
        String education = getIntent().getStringExtra("education");

        showResults(age, income, category, occupation, gender, education);
    }

    private void showResults(int age, int income, String category,
                             String occupation, String gender, String education) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM schemes WHERE minAge <= ? AND maxAge >= ? AND maxIncome >= ?",
                new String[]{
                        String.valueOf(age),
                        String.valueOf(age),
                        String.valueOf(income)
                });

        boolean found = false;

        while (cursor.moveToNext()) {

            String name = cursor.getString(1);
            String dbCategory = cursor.getString(5);
            String dbOccupation = cursor.getString(6);
            String dbEducation = cursor.getString(7);

            boolean isEligible = true;

            // ✅ CATEGORY (General applies to all)
            if (!(dbCategory.equalsIgnoreCase(category) || dbCategory.equalsIgnoreCase("General"))) {
                isEligible = false;
            }

            // ✅ OCCUPATION (flexible)
            if (!dbOccupation.equalsIgnoreCase("Any")) {
                if (!(dbOccupation.equalsIgnoreCase(occupation) ||
                        (occupation.equalsIgnoreCase("Undergraduate") && dbOccupation.equalsIgnoreCase("Student")))) {
                    isEligible = false;
                }
            }

            // ✅ EDUCATION (flexible)
            if (!dbEducation.equalsIgnoreCase("Any")) {
                if (!(dbEducation.equalsIgnoreCase(education) ||
                        (education.equalsIgnoreCase("Undergraduate") && dbEducation.equalsIgnoreCase("12th")))) {
                    isEligible = false;
                }
            }

            // ✅ GENDER
            if (gender.equalsIgnoreCase("Male") && name.toLowerCase().contains("girl")) {
                isEligible = false;
            }

            if (!isEligible) continue;

            // 🔥 CREATE ROW
            TableRow row = new TableRow(this);
            row.setPadding(0, 10, 0, 10);

            row.addView(createCell(name));
            row.addView(createCell(cursor.getInt(2) + "-" + cursor.getInt(3)));
            row.addView(createCell(String.valueOf(cursor.getInt(4))));
            row.addView(createCell(dbCategory));

            tableLayout.addView(row);

            found = true;
        }

        cursor.close();

        if (!found) {
            TableRow row = new TableRow(this);
            TextView tv = new TextView(this);
            tv.setText("No Eligible Schemes Found");
            tv.setTextColor(Color.RED);
            tv.setTextSize(16);
            tv.setPadding(20, 40, 20, 20);
            row.addView(tv);
            tableLayout.addView(row);
        }
    }

    // 🔥 Clean reusable cell
    private TextView createCell(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setPadding(16, 12, 16, 12);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(14);
        return tv;
    }
}