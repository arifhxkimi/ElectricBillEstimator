package com.example.individualassignment_arif;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView textViewDetail;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Bill Detail");
        setContentView(R.layout.activity_detail);

        textViewDetail = findViewById(R.id.textViewDetail);
        db = new DatabaseHelper(this);

        int id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            Cursor cursor = db.getAllData();
            while (cursor.moveToNext()) {
                if (cursor.getInt(0) == id) {
                    String detail = "Month: " + cursor.getString(1) + "\n" +
                            "Unit Used: " + cursor.getInt(2) + " kWh\n" +
                            "Total Charges: RM " + cursor.getDouble(3) + "\n" +
                            "Rebate: " + cursor.getDouble(4) + " %\n" +
                            "Final Cost: RM " + cursor.getDouble(5);
                    textViewDetail.setText(detail);
                    break;
                }
            }
        }
    }
}