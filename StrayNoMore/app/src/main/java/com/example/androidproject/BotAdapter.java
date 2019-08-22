package com.example.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Message> mDataset;
    Context context;
    public BotAdapter(Context context,ArrayList<Message> Inputdata) {
        this.mDataset = Inputdata;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        RecyclerView.ViewHolder vh;
        ConstraintLayout constraintLayout;

        switch (viewType){
            case FragmentsBots.message_query:
                constraintLayout = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.msgrecieve,parent,false);
                vh=new MessageViewHolder(constraintLayout);
                break;
            case FragmentsBots.message_send:
            default:
                constraintLayout = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.msgsend,parent,false);
                vh=new MessageViewHolder(constraintLayout);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i){
        switch (getItemViewType(i)){
            case FragmentsBots.message_query:
                ((MessageViewHolder) holder).bind(mDataset.get(i));
                break;
            case FragmentsBots.message_send:
            default:
                ((MessageViewHolder) holder).bind(mDataset.get(i));
                break;
        }
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int i){
        return mDataset.get(i).getType();
    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView mText, mTime;

        public MessageViewHolder(ConstraintLayout c) {
            super(c);
            mText = (TextView) c.findViewById(R.id.textMessage);
            mTime = (TextView) c.findViewById(R.id.textTime);
        }

        void bind(Message message){
            mText.setText(message.getText());
            mTime.setText(message.getTime());
        }
    }
}
