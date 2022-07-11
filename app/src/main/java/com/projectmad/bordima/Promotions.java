package com.projectmad.bordima;

public class Promotions {

    //Model class
    String name;
    String imageUrl;

    //constructors
    public Promotions(){

    }

    public Promotions(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
