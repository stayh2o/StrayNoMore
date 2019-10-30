package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAccount extends AppCompatActivity {
    private TextView acc_name;
    private TextView acc_email;
    private TextView acc_phone;
    Toolbar toolbar;
    Context context;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RescuedAdapter rescuedAdapter;
    private String email;
    private String url = "http://192.168.43.77:8081/";
    private String imgurl;
    //private String email;
    private CircleImageView circleImageView;
    private TextView textView;
    ArrayList<Rescue> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent data = getIntent();
        email = data.getStringExtra("email_id");
        getdata();
        setContentView(R.layout.activity_my_account);

        acc_email = (TextView) findViewById(R.id.account_email);
        acc_name = (TextView) findViewById(R.id.account_name);
        acc_phone = (TextView) findViewById(R.id.account_number);
        circleImageView = (CircleImageView) findViewById(R.id.profileImg);
        textView = (TextView) findViewById(R.id.paisa_contri);
        toolbar = findViewById(R.id.toolbar_help);
        setSupportActionBar(toolbar);
        context = this;


        acc_email.setText(data.getStringExtra("email_id"));
        acc_name.setText(data.getStringExtra("name"));
        acc_phone.setText(data.getStringExtra("phone"));
        //email = MainActivity.getEmail();

        imgurl = url+data.getStringExtra("email_id")+"_profilepic.jpg";

        Picasso.with(context)
                .load(imgurl)
                .into(circleImageView);

        ipaddress ip = new ipaddress();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d("test",list.toString());
        getamount();
        recyclerView = findViewById(R.id.rescued);
        recyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        rescuedAdapter = new RescuedAdapter(this,this.list);
        recyclerView.setAdapter(rescuedAdapter);
        recyclerView.smoothScrollToPosition(rescuedAdapter.getItemCount());
    }

    public void getdata(){
        list = new ArrayList<Rescue>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String dataurl = url+"mehelp/"+email;
        Log.d("url",dataurl);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, dataurl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //acc_name.setText("YOLO");
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject adoptdata = response.getJSONObject(i);
                        String ngo_name = adoptdata.getString("found_by_ngo");
                        String status = adoptdata.getString("status");
                        String img_url = url+ adoptdata.getString("img_addr");
                        list.add(new Rescue(status,ngo_name, img_url));
                        Log.d("test",list.get(i).getStatus()+list.get(i).getNgoname()+list.get(i).getImg_url());
                        rescuedAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(list.size()-1);
                    }
                    //Log.d("test",list.get(0).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),"Data not loaded myaccount",Toast.LENGTH_LONG).show();
            }
        }){

        };
        requestQueue.add(jsonArrayRequest);
    }

    public void getamount(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String dataurl = url+"user/"+email;
        Log.d("url",dataurl);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, dataurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //acc_name.setText("YOLO");
                try {
                    Integer i = response.getInt("amount_don");
                    String am = String.valueOf(i);
                    textView.setText(am);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),"Data not loaded myaccount",Toast.LENGTH_LONG).show();
            }
        }){

        };
        requestQueue.add(jsonObjectRequest);
    }
    private void linearlist(){
        list = new ArrayList<Rescue>();
        list.add(new Rescue("Waiting for help","None","http://192.168.43.77:8081/chaitanyadukkipaty@gmail.com1572342228024.jpg"));
    }
}
