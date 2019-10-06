package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adoptadpter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Adopt> mDataset;
    Context context;

    public Adoptadpter(Context context, ArrayList<Adopt>Inputdata){
        this.context = context;
        this.mDataset = Inputdata;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        RecyclerView.ViewHolder vh;
        ConstraintLayout constraintLayout;
        constraintLayout = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.adpot_list,parent,false);
        vh=new Adoptadpter.MessageViewHolder(constraintLayout);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i){
                final int f = i;
                ((Adoptadpter.MessageViewHolder) holder).bind(mDataset.get(i),context);
                ((MessageViewHolder) holder).constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"You clicked "+mDataset.get(f).getAnimalname(),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context,adopt_dialog.class);
                        intent.putExtra("animalname",mDataset.get(f).getAnimalname());
                        intent.putExtra("ngoname", mDataset.get(f).getNgoname());
                        intent.putExtra("img_url",mDataset.get(f).getImg_url());
                        context.startActivity(intent);
                    }
                });
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView mNgoname, mAnimalname;
        public ImageView mAnimal;
        ConstraintLayout constraintLayout;
        public MessageViewHolder(ConstraintLayout c) {
            super(c);
            mNgoname = (TextView) c.findViewById(R.id.ngoName);
            mAnimalname = (TextView) c.findViewById(R.id.animalname);
            mAnimal = (ImageView) c.findViewById(R.id.animalpic);
            constraintLayout = c.findViewById(R.id.adoptLayout);
        }

        void bind(Adopt message, Context context){
            mNgoname.setText(message.getNgoname());
            mAnimalname.setText(message.getAnimalname());

            Picasso.with(context)
                    .load(message.getImg_url())
                    .into(mAnimal);
        }
    }


}
