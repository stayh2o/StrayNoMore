package com.example.androidproject;

public class NGO {
    private String ngoname;
    private String ngolocation;
    private String contact;
    private String email;

    public NGO(String ngoname, String ngolocation, String contact, String email){
        this.ngoname = ngoname;
        this.ngolocation = ngolocation;
        this.contact = contact;
        this.email = email;
    }

    public String getngoname(){
        return ngoname;
    }

    public String getngolocation(){
        return ngolocation;
    }

    public String getContact() { return contact; }

    public String getEmail() { return email; }
}
