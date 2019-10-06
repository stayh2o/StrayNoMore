package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class donation_dialog extends AppCompatActivity {
    TextView ngoname;
    TextView ngolocation;
    TextView contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_dialog);
        ngoname = findViewById(R.id.ngoname_dialog_donation);
        ngolocation = findViewById(R.id.ngolocation_dialog_donation);
        contact = findViewById(R.id.ngo_contact);

        ngoname.setText(getIntent().getStringExtra("ngoname"));
        ngolocation.setText(getIntent().getStringExtra("ngolocation"));
        contact.setText(getIntent().getStringExtra("ngocontact"));
    }
}
