package com.example.androidproject;

public class ipaddress {
    private  String ip = "http://192.168.43.77:8081/";
    private  String chatip = "htpp://192.168.43.77:5000/";

    public String getIp() {
        return ip;
    }

    public  String getChatip(){
        return chatip;
    }

    public void setIp(String ip){
        this.ip = ip + "/8081";
        this.chatip = ip + "/5000";
    }
}
