package com.example.androidproject;

public class Adopt {
    private String Animalname;
    private String Ngoname;

    public Adopt(String Animalname, String Ngoname){
        this.Animalname = Animalname;
        this.Ngoname = Ngoname;
    }
    public String getAnimalname() {
        return Animalname;
    }

    public String getNgoname() {
        return Ngoname;
    }
}
