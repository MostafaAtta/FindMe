package com.atta.findme.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Shops {

    @SerializedName("shops")
    private ArrayList<Shop> shops;

    public Shops() {
    }

    public ArrayList<Shop> getShops() {
        return shops;
    }

    public void setShops(ArrayList<Shop> shops) {
        this.shops = shops;
    }

}
