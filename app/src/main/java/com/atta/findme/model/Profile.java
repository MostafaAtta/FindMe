package com.atta.findme.model;

import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("error")
    private Boolean error;


    @SerializedName("user")
    private User user;


    @SerializedName("addresses_error")
    private Boolean addressesError;


    @SerializedName("error_msg")
    private String errorMsg;

    @SerializedName("address")
    private Address address;

    public Profile() {
    }

    public User getUser() {
        return user;
    }

    public Boolean getAddressesError() {
        return addressesError;
    }

    public Address getAddress() {
        return address;
    }

    public Boolean getError() {
        return error;
    }


    public String getErrorMsg() {
        return errorMsg;
    }
}
