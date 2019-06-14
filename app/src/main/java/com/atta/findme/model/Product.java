package com.atta.findme.model;

import java.io.Serializable;

public class Product implements Serializable {

    private int id, shopId, stock, cartCount, itemId, quantity;

    private double price;

    private String name, description, brand, amount, image, category;

    boolean inCart;

    public Product(int id, int shopId, int itemId, double price, String name, String description, String brand, String amount, String image, String category, int stock) {
        this.id = id;
        this.shopId = shopId;
        this.itemId = itemId;
        this.price = price;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.amount = amount;
        this.image = image;
        this.category = category;
        this.stock = stock;
    }

    public Product(int id, double price, String name, String description, String brand, String amount, String image, String category) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.amount = amount;
        this.image = image;
        this.category = category;
    }

    public Product(int id, int shopId, int stock, int cartCount, double price, String name, String description, String brand, String amount, String image, String category, boolean inCart) {
        this.id = id;
        this.shopId = shopId;
        this.stock = stock;
        this.cartCount = cartCount;
        this.price = price;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.amount = amount;
        this.image = image;
        this.category = category;
        this.inCart = inCart;
    }

    public Product(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public String getAmount() {
        return amount;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }

    public int getShopId() {
        return shopId;
    }

    public int getCartCount() {
        return cartCount;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setCartCount(int cartCount) {
        this.cartCount = cartCount;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public int getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }
}
