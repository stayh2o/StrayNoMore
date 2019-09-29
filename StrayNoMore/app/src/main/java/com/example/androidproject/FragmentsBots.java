package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentsBots extends Fragment {
    private String url = "http://192.168.43.77:5000/get";
    private final String filename = "encrypted_messages.bcm";
    public static final int message_query = 1;
    public static final int message_send = 2;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    BotAdapter botAdapter;
    ArrayList<Message> list = new ArrayList<>();;
    ImageButton sendButton;
    EditText editText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //linearlist();
        loadMessages();
        View rootView = inflater.inflate(R.layout.fragment_bots,container,false);
        //RecyclerView Shit
        recyclerView = rootView.findViewById(R.id.messagerecyclerview);
        recyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        botAdapter = new BotAdapter(getActivity(),this.list);
        recyclerView.setAdapter(botAdapter);
        recyclerView.smoothScrollToPosition(botAdapter.getItemCount());
        refresh();
        //Other shit
        editText = (EditText) rootView.findViewById(R.id.editQuery);
        sendButton = (ImageButton) rootView.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getMessage().length()>0){
                    sendMessage();
                }
            }
        });

        return rootView;
    }
    private String getMessage(){
        return editText.getText().toString();
    }

    private void sendMessage() {
        final String message = getMessage();
        editText.getText().clear();
        list.add(new Message(message, message_send));
        refresh();
        HashMap data = new HashMap();
        data.put("msg",message);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("botmsg",response);
                            list.add(new Message(response, message_query));
                            }
                        finally {
                            refresh();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("msg",message);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
       // Log.i("Messages", list.get(list.size()-1).getText());
    }
    private void refresh() {
        saveMessages();
        botAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(list.size()-1);
    }

    private void saveMessages() {
        try {
            FileOutputStream fileOutputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
            objectOutputStream.close();
            //Log.i("Messages", list.get(list.size()-1).getText());
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMessages() {
        try {
            FileInputStream fileInputStream = getActivity().openFileInput(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            list.clear();
            list = (ArrayList<Message>) objectInputStream.readObject();
            //Log.i("Messages", list.get(list.size()-1).getText());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void linearlist(){
        list = new ArrayList<Message>();
        list.add(new Message("Hey",2));
        list.add(new Message("Hello, Welcome",1));
    }
}


