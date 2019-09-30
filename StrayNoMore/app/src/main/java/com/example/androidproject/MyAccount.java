package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MyAccount extends AppCompatActivity {
    private TextView acc_name;
    private TextView acc_email;
    private TextView acc_phone;
    Toolbar toolbar;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Intent data = getIntent();
        acc_email = (TextView) findViewById(R.id.account_email);
        acc_name = (TextView) findViewById(R.id.account_name);
        acc_phone = (TextView) findViewById(R.id.account_number);
        context = this;
        toolbar = findViewById(R.id.toolbar_account);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, MainActivity.class);
//                startActivity(intent);
//            }
//        });

        acc_email.setText(data.getStringExtra("email_id"));
        acc_name.setText(data.getStringExtra("name"));
        acc_phone.setText(data.getStringExtra("phone"));
    }
}
