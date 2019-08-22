package com.example.androidproject;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class signin_fragment extends Fragment {


    public signin_fragment() {
        // Required empty public constructor
    }

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
                String sigin_email = email.getText().toString();
                String signin_password = password.getText().toString();
                if(sigin_email.equals("test")  && signin_password.equals("test")){
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(),"Wrong Username or Password",Toast.LENGTH_LONG).show();
                }
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
