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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentAdopt extends Fragment {
    private String url = "http://192.168.43.77:8081/";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Adoptadpter adoptadpter;
    ArrayList<Adopt> list = new ArrayList<>();;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView =  inflater.inflate(R.layout.fragmet_adopt,container,false);

        recyclerView = rootView.findViewById(R.id.recyclerview_adopt);
        recyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        getdata();
        adoptadpter = new Adoptadpter(getActivity(),this.list);
        recyclerView.setAdapter(adoptadpter);
        recyclerView.smoothScrollToPosition(adoptadpter.getItemCount());
//        ipaddress ip = new ipaddress();
//        url = ip.getIp();
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
       // Toast.makeText(getActivity(),"Pausey",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getActivity(),"Resumey",Toast.LENGTH_LONG).show();
        adoptadpter.notifyDataSetChanged();


    }

    public void getdata(){
        list = new ArrayList<Adopt>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String dataurl = url+"adoptlist";
        Log.d("url",dataurl);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, dataurl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject adoptdata = response.getJSONObject(i);
                        String ngo_name = adoptdata.getString("found_by_ngo");
                        String animal_name = adoptdata.getString("animal_name");
                        String img_url = url+ adoptdata.getString("img_addr");
                        //Toast.makeText(getActivity(),animal_name,Toast.LENGTH_LONG).show();
                        list.add(new Adopt(animal_name,ngo_name, img_url));
                        adoptadpter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(list.size()-1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Data not loaded adopt",Toast.LENGTH_LONG).show();
            }
        }){

        };
        requestQueue.add(jsonArrayRequest);
    }

//    private void linearlist(){
//        list = new ArrayList<Adopt>();
//        list.add(new Adopt("Fuffy","PETA India"));
//        list.add(new Adopt("Sonwy","Wild World"));
//    }
}
