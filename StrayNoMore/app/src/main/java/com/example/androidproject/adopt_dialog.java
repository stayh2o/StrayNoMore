package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class adopt_dialog extends AppCompatActivity {
    TextView animalname;
    TextView ngoname;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_dialog);
        animalname = findViewById(R.id.adpot_name);
        ngoname = findViewById(R.id.adopt_ngoname);
        imageView = findViewById(R.id.adpot_image);

        ngoname.setText(getIntent().getStringExtra("ngoname"));
        animalname.setText(getIntent().getStringExtra("animalname"));
        String img_url = getIntent().getStringExtra("img_url");
        Picasso.with(this)
                .load(img_url)
                .into(imageView);

    }
}
