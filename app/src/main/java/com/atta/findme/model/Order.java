package com.atta.findme.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Order implements Serializable {


    private Map<String, String> products, count ;


    @SerializedName("productsList")
    private ArrayList<Product> productsList;


    private int orderId, delivery, discount, userId, address, status;
    private String location, schedule, orderTime, creationTime, phone, kitchen;

    private double subtotal, total;


    @SerializedName("fullAddress")
    private Address fullAddress;


    public Order(int orderId, ArrayList<Product> productsList, int status, double subtotal, int delivery, double total, int discount, int userId, String location,
                 int address, String phone, String schedule, String orderTime, String creationTime, Address fullAddress, String kitchen) {
        this.productsList = productsList;
        this.orderId = orderId;
        this.status = status;
        this.subtotal = subtotal;
        this.delivery = delivery;
        this.total = total;
        this.discount = discount;
        this.userId = userId;
        this.location = location;
        this.address = address;
        this.schedule = schedule;
        this.phone = phone;
        this.orderTime = orderTime;
        this.creationTime = creationTime;
        this.fullAddress = fullAddress;
        this.kitchen = kitchen;
    }

    public Order(Map<String, String> products, Map<String, String> count, double subtotal, int delivery, double total, int discount, int userId, String location,
                 int address, String phone, String schedule, String orderTime, String creationTime) {
        this.products = products;
        this.count = count;
        this.subtotal = subtotal;
        this.delivery = delivery;
        this.total = total;
        this.discount = discount;
        this.userId = userId;
        this.location = location;
        this.address = address;
        this.schedule = schedule;
        this.phone = phone;
        this.orderTime = orderTime;
        this.creationTime = creationTime;
    }

    public String getSchedule() {


        return schedule;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public Map<String, String> getProducts() {

        return products;
    }

    public Map<String, String> getCount() {
        return count;
    }

    public double getSubtotalPrice() {
        return subtotal;
    }

    public int getDelivery() {
        return delivery;
    }

    public double getTotalPrice() {
        return total;
    }

    public int getDiscount() {
        return discount;
    }

    public String getLocation() {
        return location;
    }

    public int getAddress() {
        return address;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {

        return userId;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public void setCreationTime(String creationTime) {
        creationTime = creationTime;
    }

    public String getCreationTime() {

        return creationTime;
    }

    public String getMobile() {
        return phone;
    }

    public ArrayList<Product> getProductsList() {
        return productsList;
    }

    public Address getFullAddress() {
        return fullAddress;
    }


    public int getId() {
        return orderId;
    }

    public String getKitchen() {
        return kitchen;
    }

    public int getStatus() {
        return status;
    }
}
