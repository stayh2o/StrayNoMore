package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RescuedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Rescue> mDataset;
    Context context;

    public RescuedAdapter(Context context, ArrayList<Rescue>Inputdata){
        this.context = context;
        this.mDataset = Inputdata;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        RecyclerView.ViewHolder vh;
        ConstraintLayout constraintLayout;
        constraintLayout = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.adpot_list,parent,false);
        vh=new RescuedAdapter.MessageViewHolder(constraintLayout);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i){
        ((RescuedAdapter.MessageViewHolder) holder).bind(mDataset.get(i),context);
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView mNgoname, mStatus;
        public ImageView mAnimal;
        //ConstraintLayout constraintLayout;
        public MessageViewHolder(ConstraintLayout c) {
            super(c);
            mNgoname = (TextView) c.findViewById(R.id.animalname);
            mStatus = (TextView) c.findViewById(R.id.ngoName);
            mAnimal = (ImageView) c.findViewById(R.id.animalpic);
            //constraintLayout = c.findViewById(R.id.adoptLayout);
        }

        void bind(Rescue message, Context context){
            mNgoname.setText(message.getNgoname());
            mStatus.setText(message.getStatus());

            Picasso.with(context)
                    .load(message.getImg_url())
                    .into(mAnimal);
        }
    }

}
