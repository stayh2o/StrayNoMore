package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class donation_dialog extends AppCompatActivity {
    TextView ngoname;
    TextView ngolocation;
    TextView contact;
    String ngoemail;
    String useremail;
    EditText editText;
    EditText editText1;
    Context context;
    Button button;
    private String url = "http://192.168.43.77:8081/donate";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_dialog);
        ngoname = findViewById(R.id.ngoname_dialog_donation);
        ngolocation = findViewById(R.id.ngolocation_dialog_donation);
        contact = findViewById(R.id.ngo_contact);
        editText = findViewById(R.id.donation_amount);
        editText1 =findViewById(R.id.donation_paisa);
        button = findViewById(R.id.donate);

        ngoname.setText(getIntent().getStringExtra("ngoname"));
        ngolocation.setText(getIntent().getStringExtra("ngolocation"));
        contact.setText(getIntent().getStringExtra("ngocontact"));

        ngoemail = getIntent().getStringExtra("ngoemail");
        useremail = MainActivity.getEmail();

        context = this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyupload();
                finish();
            }
        });

        Toast.makeText(this,ngoemail+useremail,Toast.LENGTH_LONG).show();
    }

    public void volleyupload(){
        HashMap data = new HashMap();
        data.put("sender_email",useremail);
        data.put("txId",editText.getText().toString());
        data.put("amount",editText1.getText().toString());
        data.put("ngo_email",ngoemail);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(data),
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        editText.setText("");
                        editText1.setText("");
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Image not sent",Toast.LENGTH_LONG).show();
            }
        }){

        };
        requestQueue.add(jsonObjectRequest);
    }
}
