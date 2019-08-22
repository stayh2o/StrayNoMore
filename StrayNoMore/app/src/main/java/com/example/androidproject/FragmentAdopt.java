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

public class FragmentAdopt extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Adoptadpter adoptadpter;
    ArrayList<Adopt> list = new ArrayList<>();;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView =  inflater.inflate(R.layout.fragmet_adopt,container,false);
        linearlist();
        recyclerView = rootView.findViewById(R.id.recyclerview_adopt);
        recyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adoptadpter = new Adoptadpter(getActivity(),this.list);
        recyclerView.setAdapter(adoptadpter);
        recyclerView.smoothScrollToPosition(adoptadpter.getItemCount());

        return rootView;
    }

    private void linearlist(){
        list = new ArrayList<Adopt>();
        list.add(new Adopt("Fuffy","PETA India"));
        list.add(new Adopt("Sonwy","Wild World"));
    }
}
