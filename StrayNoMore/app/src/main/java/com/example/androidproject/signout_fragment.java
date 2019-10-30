package com.example.androidproject;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class signout_fragment extends Fragment {
    public static final int PICK_IMAGE = 1;
    private String url;
    private TextView alreadyaccount;
    private FrameLayout parentframeLayout;
    private Button uploadimg;
    private Button button;
    private EditText email;
    private EditText name;
    private EditText phone;
    private EditText password;
    private EditText cpassword;
    CircleImageView circleImageView;
    private String imgbase64="p";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signout_fragment, container, false);
        alreadyaccount = view.findViewById(R.id.signin_in_signup);
        parentframeLayout = getActivity().findViewById(R.id.register_framelayout);

        button = view.findViewById(R.id.btn_signout);
        uploadimg = view.findViewById(R.id.uploadimg);
        email = view.findViewById(R.id.signout_email);
        name = view.findViewById(R.id.signout_name);
        phone = view.findViewById(R.id.phone_number);
        password = view.findViewById(R.id.signout_password);
        cpassword = view.findViewById(R.id.signout_confirmpassword);
        circleImageView = view.findViewById(R.id.logosignup);
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

        uploadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String signup_email = email.getText().toString();
                final String signup_password = password.getText().toString();
                final String signup_phone = phone.getText().toString();
                final String signup_name = name.getText().toString();
                final String signup_cpassword = cpassword.getText().toString();

                String status = check(signup_email,signup_password,signup_phone,signup_name,signup_cpassword, imgbase64);
                if(status == "Pass"){
                    HashMap data = new HashMap();
                    data.put("email_id",signup_email);
                    data.put("password",signup_password);
                    data.put("name",signup_name);
                    data.put("phone",signup_phone);
                    data.put("image",imgbase64);
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
                else {
                    Toast.makeText(getActivity(),status,Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.silde_from_left,R.anim.slide_out_from_right);
        fragmentTransaction.replace(parentframeLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE  && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                imgbase64 = Base64.encodeToString(b, Base64.DEFAULT);
                circleImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public String check(String signup_email,String signup_password,String signup_phone,String signup_name,String signup_cpassword, String imgbase64){
        if(signup_cpassword.isEmpty() || signup_email.isEmpty() || signup_phone.isEmpty() || signup_password.isEmpty() || imgbase64.isEmpty()){
            return "Fields are empty";
        }
        else if(!(signup_password.equals(signup_cpassword))) {
            return "Password don't match";
        }
        else if(! (signup_phone.length()==10 || signup_phone.length()==11 || signup_phone.length()==8)){
            return "Contact number size is wrong";
        }
        else if(! isValid(signup_email)){
            return "Email Id format is worng";
        }
        else{
            return "Pass";
        }
    }
}
