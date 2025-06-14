package com.example.individualassignment_arif;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerMonth, spinnerRebate;
    EditText editTextUnit;
    TextView textViewResult;
    DatabaseHelper db;

    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String[] rebates = {"0", "1", "2", "3", "4", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Electric Bill Estimator");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_main);

        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerRebate = findViewById(R.id.spinnerRebate);
        editTextUnit = findViewById(R.id.editTextUnit);
        textViewResult = findViewById(R.id.textViewResult);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        spinnerMonth.setAdapter(monthAdapter);

        ArrayAdapter<String> rebateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, rebates);
        spinnerRebate.setAdapter(rebateAdapter);

        db = new DatabaseHelper(this);

        findViewById(R.id.btnCalculate).setOnClickListener(v -> calculateAndSave());
        findViewById(R.id.btnViewHistory).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, BillListActivity.class)));
        findViewById(R.id.btnAbout).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AboutActivity.class)));
    }

    void calculateAndSave() {
        String month = spinnerMonth.getSelectedItem().toString();
        String unitStr = editTextUnit.getText().toString();
        String rebateStr = spinnerRebate.getSelectedItem().toString();

        if (unitStr.isEmpty()) {
            editTextUnit.setError("Enter unit used");
            return;
        }

        int unit = Integer.parseInt(unitStr);
        double rebate = Double.parseDouble(rebateStr);

        double total = calculateTotal(unit);
        double finalCost = total - (total * (rebate / 100.0));

        textViewResult.setText("Total: RM" + total + "\nFinal Cost: RM" + finalCost);

        db.insertData(month, unit, total, rebate, finalCost);
        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
    }

    double calculateTotal(int unit) {
        double total = 0;
        if (unit <= 200)
            total = unit * 0.218;
        else if (unit <= 300)
            total = 200 * 0.218 + (unit - 200) * 0.334;
        else if (unit <= 600)
            total = 200 * 0.218 + 100 * 0.334 + (unit - 300) * 0.516;
        else
            total = 200 * 0.218 + 100 * 0.334 + 300 * 0.516 + (unit - 600) * 0.546;

        return Math.round(total * 100.0) / 100.0;
    }
}