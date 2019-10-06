package com.example.androidproject;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Donationadapter extends RecyclerView.Adapter<Donationadapter.MyHolder> {
    Context context;
    ArrayList<NGO> ngos;
    private FrameLayout parentframeLayout;

    public Donationadapter(Context context, ArrayList<NGO> ngos) {
        this.context = context;
        this.ngos = ngos;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.donation_list, viewGroup, false);
        parentframeLayout = view.findViewById(R.id.view_pager);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myholder, int i) {
        final int f = i;
        myholder.ngoname.setText(ngos.get(i).getngoname());
        myholder.ngolocation.setText(ngos.get(i).getngolocation());
        myholder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"You clicked "+ngos.get(f).getngoname(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context,donation_dialog.class);
                intent.putExtra("ngoname",ngos.get(f).getngoname());
                intent.putExtra("ngolocation", ngos.get(f).getngolocation());
                intent.putExtra("ngocontact", ngos.get(f).getContact());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return ngos.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView ngoname, ngolocation;
        RelativeLayout relativeLayout;
        private MyHolder(@NonNull View itemView) {
            super(itemView);
            ngoname = itemView.findViewById(R.id.ngoname);
            ngolocation = itemView.findViewById(R.id.ngolocation);
            relativeLayout = itemView.findViewById(R.id.donationlayout);
        }
    }
}
