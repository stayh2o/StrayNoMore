package com.example.androidproject;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class signin_fragment extends Fragment {


    public signin_fragment() {
        // Required empty public constructor
    }
    private String url ;
    private TextView donthaveaccount;
    private FrameLayout parentframeLayout;
    private EditText email;
    private EditText password;
    private Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin_fragment, container, false);
        donthaveaccount = view.findViewById(R.id.signup_in_signin);
        email = view.findViewById(R.id.signin_email);
        password = view.findViewById(R.id.signin_password);
        btn = view.findViewById(R.id.btn_signin);
        parentframeLayout = getActivity().findViewById(R.id.register_framelayout);
        ipaddress ip = new ipaddress();
        url = ip.getIp();
        url = url+"login";
        Log.d("url",url);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        donthaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new signout_fragment());
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sigin_email = email.getText().toString();
                final String signin_password = password.getText().toString();
                HashMap data = new HashMap();
                data.put("email_id",sigin_email);
                data.put("password",signin_password);
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(data),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Intent intent = new Intent(getActivity(),MainActivity.class);
                                try {
                                    intent.putExtra("email_id",response.getString("email_id"));
                                    intent.putExtra("name",response.getString("name"));
                                    intent.putExtra("phone",response.getString("phone"));
                                    startActivity(intent);
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Wrong Username or Password",Toast.LENGTH_LONG).show();
                    }
                }){

                };
                requestQueue.add(jsonObjectRequest);
            }
        });

    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_out_from_left);
        fragmentTransaction.replace(parentframeLayout.getId(),fragment);
        fragmentTransaction.commit();

    }
}
