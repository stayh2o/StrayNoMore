package com.example.androidproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentDonation extends Fragment {
    private String url = "http://192.168.43.77:8081/ngolist";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Donationadapter donationadapter;
    ArrayList<NGO> list;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //linearlist();
        getdata();
        View rootView =  inflater.inflate(R.layout.fragment_donation,container,false);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        recyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        donationadapter = new Donationadapter(getActivity(),this.list);
        recyclerView.setAdapter(donationadapter);
        ipaddress ip = new ipaddress();
        url = ip.getIp();
        url = url+"ngolist";
        Log.d("url",url);
        return rootView;
    }

    public void getdata(){
        list = new ArrayList<NGO>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject ngodata = response.getJSONObject(i);
                        String ngoname = ngodata.getString("ngo_name");
                        String ngo_location = ngodata.getString("address");
                        String contact = ngodata.getString("contact");
                        list.add(new NGO(ngoname,ngo_location, contact));
                        donationadapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(list.size()-1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Data not loaded",Toast.LENGTH_LONG).show();
            }
        }){

        };
        requestQueue.add(jsonArrayRequest);
    }
    private void linearlist(){
        list = new ArrayList<NGO>();
        list.add(new NGO("PETA India","Dadar", "9969199986"));
        list.add(new NGO("Wild World","JBnagar","9757328434"));
    }
}
