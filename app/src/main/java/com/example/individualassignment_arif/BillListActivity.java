package com.example.individualassignment_arif;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BillListActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> bills;
    ArrayList<Integer> ids;
    DatabaseHelper db;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Saved Bills");
        setContentView(R.layout.activity_bill_list);

        listView = findViewById(R.id.listView);
        bills = new ArrayList<>();
        ids = new ArrayList<>();
        db = new DatabaseHelper(this);

        loadBills();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(BillListActivity.this, DetailActivity.class);
            intent.putExtra("id", ids.get(position));
            startActivity(intent);
        });
    }

    void loadBills() {
        Cursor cursor = db.getAllData();
        if (cursor.getCount() == 0) {
            bills.add("No data found");
            return;
        }

        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(0));
            String month = cursor.getString(1);
            double finalCost = cursor.getDouble(5);
            bills.add(month + " - RM " + finalCost);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bills);
        listView.setAdapter(adapter);
    }
}