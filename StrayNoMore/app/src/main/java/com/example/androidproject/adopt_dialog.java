package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;

public class adopt_dialog extends AppCompatActivity {
    TextView animalname;
    TextView ngoname;
    ImageView imageView;
    Button adopt;
    Context context;
    private String email;
    private String url ;
    String animal_name;
    String ngo_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_dialog);
        animalname = findViewById(R.id.adpot_name);
        ngoname = findViewById(R.id.adopt_ngoname);
        imageView = findViewById(R.id.adpot_image);
        adopt = findViewById(R.id.btn_adopt);
        email = MainActivity.getEmail();
        context = this;
        ipaddress ip = new ipaddress();
        url = ip.getIp();
        url = url+"meadopt";

        adopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyupload();
                //Toast.makeText(context,email,Toast.LENGTH_LONG).show();
                finish();
            }
        });
        animal_name = getIntent().getStringExtra("animalname");
        ngo_name = getIntent().getStringExtra("ngoname");
        ngoname.setText(getIntent().getStringExtra("ngoname"));
        animalname.setText(getIntent().getStringExtra("animalname"));
        String img_url = getIntent().getStringExtra("img_url");
        Picasso.with(this)
                .load(img_url)
                .into(imageView);

    }

    public void volleyupload(){
        HashMap data = new HashMap();
        data.put("owner",email);
        data.put("animal_name",animal_name);
        data.put("found_by_ngo",ngo_name);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(data),
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(),"Image not sent",Toast.LENGTH_LONG).show();
            }
        }){

        };
        requestQueue.add(jsonObjectRequest);
    }
}
