package com.atta.findme.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrdersResult {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("products")
    private ArrayList<Product> products;

    @SerializedName("address")
    private Address address;



    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Address getAddress() {
        return address;
    }

}
