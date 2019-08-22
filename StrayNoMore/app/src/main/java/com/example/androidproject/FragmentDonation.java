package com.example.androidproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentDonation extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Donationadapter donationadapter;
    ArrayList<NGO> list;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        linearlist();
        View rootView =  inflater.inflate(R.layout.fragment_donation,container,false);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        recyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        donationadapter = new Donationadapter(getActivity(),this.list);
        recyclerView.setAdapter(donationadapter);
        return rootView;
    }

    private void linearlist(){
        list = new ArrayList<NGO>();
        list.add(new NGO("PETA India","Dadar"));
        list.add(new NGO("Wild World","JBnagar"));
    }
}
