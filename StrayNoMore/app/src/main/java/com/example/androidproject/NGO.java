package com.example.androidproject;

public class NGO {
    private String ngoname;
    private String ngolocation;

    public NGO(String ngoname, String ngolocation){
        this.ngoname = ngoname;
        this.ngolocation = ngolocation;
    }

    public String getngoname(){
        return ngoname;
    }

    public String getngolocation(){
        return ngolocation;
    }
}
