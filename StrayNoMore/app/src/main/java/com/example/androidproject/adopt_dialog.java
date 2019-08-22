package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class adopt_dialog extends AppCompatActivity {
    TextView animalname;
    TextView ngoname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_dialog);
        animalname = findViewById(R.id.adpot_name);
        ngoname = findViewById(R.id.adopt_ngoname);

        ngoname.setText(getIntent().getStringExtra("ngoname"));
        animalname.setText(getIntent().getStringExtra("animalname"));
    }
}
