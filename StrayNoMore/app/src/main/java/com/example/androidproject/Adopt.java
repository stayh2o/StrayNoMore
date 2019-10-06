package com.example.androidproject;

public class Adopt {
    private String Animalname;
    private String Ngoname;
    private String img_url;

    public Adopt(String Animalname, String Ngoname, String img_url){
        this.Animalname = Animalname;
        this.Ngoname = Ngoname;
        this.img_url = img_url;
    }
    public String getAnimalname() {
        return Animalname;
    }

    public String getNgoname() {
        return Ngoname;
    }

    public String getImg_url(){return img_url;}
}
