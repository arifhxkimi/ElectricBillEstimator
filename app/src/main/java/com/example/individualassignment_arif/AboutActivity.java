package com.example.individualassignment_arif;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("About");
        setContentView(R.layout.activity_about);

        TextView about = findViewById(R.id.textAbout);
        Button btnGit = findViewById(R.id.btnOpenGit);

        about.setText("Name: Nur Arif Hakimi Bin Noor Hazri\n" +
                "Student ID: 2024542311\n" +
                "Course: ICT602 - Mobile Technology and Development\n" +
                "© 2025 Nur Arif Hakimi. All rights reserved.");

        btnGit.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/arifhxkimi/ElectricBillEstimator"));
            startActivity(browserIntent);
        });
    }
}
