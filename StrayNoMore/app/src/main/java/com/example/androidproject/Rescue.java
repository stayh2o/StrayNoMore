package com.example.androidproject;

public class Rescue {
    private String status;
    private String Ngoname;
    private String img_url;

    public Rescue(String status, String Ngoname, String img_url){
        this.status = status;
        this.Ngoname = Ngoname;
        this.img_url = img_url;
    }
    public String getStatus() {
        return status;
    }

    public String getNgoname() {
        return Ngoname;
    }

    public String getImg_url(){return img_url;}
}
