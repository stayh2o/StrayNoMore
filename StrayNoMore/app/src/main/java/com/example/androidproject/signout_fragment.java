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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class signout_fragment extends Fragment {
    private String url;
    private TextView alreadyaccount;
    private FrameLayout parentframeLayout;
    private Button button;
    private EditText email;
    private EditText name;
    private EditText phone;
    private EditText password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signout_fragment, container, false);
        alreadyaccount = view.findViewById(R.id.signin_in_signup);
        parentframeLayout = getActivity().findViewById(R.id.register_framelayout);

        button = view.findViewById(R.id.btn_signout);
        email = view.findViewById(R.id.signout_email);
        name = view.findViewById(R.id.signout_name);
        phone = view.findViewById(R.id.phone_number);
        password = view.findViewById(R.id.signout_password);
        ipaddress ip = new ipaddress();
        url = ip.getIp();
        url = url+"register";
        Log.d("url",url);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alreadyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new signin_fragment());
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String signup_email = email.getText().toString();
                final String signup_password = password.getText().toString();
                final String signup_phone = phone.getText().toString();
                final String signup_name = name.getText().toString();
                HashMap data = new HashMap();
                data.put("email_id",signup_email);
                data.put("password",signup_password);
                data.put("name",signup_name);
                data.put("phone",signup_phone);
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
                        Toast.makeText(getActivity(),"Wrong la Username or Password",Toast.LENGTH_LONG).show();
                    }
                }){

                };
                requestQueue.add(jsonObjectRequest);
            }
        });
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.silde_from_left,R.anim.slide_out_from_right);
        fragmentTransaction.replace(parentframeLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
