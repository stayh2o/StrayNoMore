package com.example.androidproject;

public class NGO {
    private String ngoname;
    private String ngolocation;
    private String contact;

    public NGO(String ngoname, String ngolocation, String contact){
        this.ngoname = ngoname;
        this.ngolocation = ngolocation;
        this.contact = contact;
    }

    public String getngoname(){
        return ngoname;
    }

    public String getngolocation(){
        return ngolocation;
    }

    public String getContact() { return contact; }
}
