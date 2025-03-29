package com.example.stylescannerapp;

public class Image {
    private String image;
    private String country;
    private String locality;

    public Image(String image, String country, String locality) {
        this.image = image;
        this.country = country;
        this.locality = locality;
    }

    public String getImage() {
        return image;
    }

    public String getCountry() {
        return country;
    }

    public String getLocality() {
        return locality;
    }
}
