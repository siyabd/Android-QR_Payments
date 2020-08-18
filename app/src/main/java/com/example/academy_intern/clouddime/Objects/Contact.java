package com.example.academy_intern.clouddime.Objects;



public class Contact {

    private String Name;
    private String Occupation;
    private int Photo;

    // Constructor


    public Contact(String name, String occupation, int photo) {
        Name = name;
        Occupation = occupation;
        Photo = photo;
    }


    // Getters

    public String getName() {
        return Name;
    }

    public String getOccupation() {
        return Occupation;
    }

    public int getPhoto() {
        return Photo;
    }

    // Setters


    public void setName(String name) {
        Name = name;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }

}
