package com.atta.findme.model;

public class User {

    private int id;
    private String username, email, password, phone, birthday, location, job;


    public User(int id, String username, String email, String password, String phone, String location) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.birthday = birthday;
        this.location = location;
        this.job = job;
    }


    public User(String username, String email, String password, String phone, String location) {

        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.birthday = birthday;
        this.location = location;
        this.job = job;
    }


    public User(String username, String email, String password, String phone, String birthday, String location) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.birthday = birthday;
        this.location = location;
    }

    public User(int id, String username, String phone, String location){
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.location = location;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getLocation() {
        return location;
    }

    public String getJob() {

        return job;
    }

}
