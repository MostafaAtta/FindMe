package com.atta.findme.model;

import java.io.Serializable;

public class Shop implements Serializable {

    private int id;
    private String name, district, city, category, description, phone, logoImage, bgImage;
    private double rating;

    public Shop(int id, String name, String district, String city, String category, String description, String phone, String logoImage, String bgImage, double rating) {
        this.id = id;
        this.name = name;
        this.district = district;
        this.city = city;
        this.category = category;
        this.description = description;
        this.phone = phone;
        this.logoImage = logoImage;
        this.bgImage = bgImage;
        this.rating = rating;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone() {
        return phone;
    }

    public double getRating() {
        return rating;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public String getBgImage() {
        return bgImage;
    }
}
