package com.atta.findme.model;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    //The register call
    @FormUrlEncoded
    @POST("register")
    Call<Result> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("location") String location
    );


    @FormUrlEncoded
    @POST("get_shops_by_category")
    Call<Shops> getShops(
            @Field("category") String cat
    );


    @FormUrlEncoded
    @POST("get_products")
    Call<Products> getProducts(
            @Field("category") String cat,
            @Field("shop_id") int shopId
    );



    //the signin call
    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("get_profile")
    Call<Profile> getProfile(
            @Field("user_id") int userId
    );

    @FormUrlEncoded
    @POST("update_user")
    Call<Result> updateProfile(
            @Field("id") int id,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("location") String location

    );

    @FormUrlEncoded
    @POST("add_address")
    Call<Result> addAddress(
            @Field("user_id") int userId,
            @Field("floor") String floor,
            @Field("apartmentNumber") String apartmentNumber,
            @Field("buildingNumber") String buildingNumber,
            @Field("area") String area,
            @Field("addressName") String addressName,
            @Field("fullAddress") String fullAddress,
            @Field("street") String street,
            @Field("landMark") String landMark,
            @Field("latitude") float latitude,
            @Field("longitude") float longitude

    );


    @PUT("edit_address/{id}/{user_id}/{floor}/{apartmentNumber}/{buildingNumber}/{area}/{addressName}/{fullAddress}/{street}/{landMark}/{latitude}/{longitude}")
    Call<Result> editAddress(
            @Path("id") int id,
            @Path("user_id") int userId,
            @Path("floor") String floor,
            @Path("apartmentNumber") String apartmentNumber,
            @Path("buildingNumber") String buildingNumber,
            @Path("area") String area,
            @Path("addressName") String addressName,
            @Path("fullAddress") String fullAddress,
            @Path("street") String street,
            @Path("landMark") String landMark,
            @Path("latitude") float latitude,
            @Path("longitude") float longitude

    );

    @FormUrlEncoded
    @POST("get_addresses")
    Call<Profile> getAddresses(
            @Field("user_id") int userId
    );


    @FormUrlEncoded
    @POST("add_order")
    Call<OrdersResult> addOrder(
            @FieldMap Map<String, String> products ,
            @FieldMap Map<String, String> quantities,
            @Field("subtotal") double subtotal,
            @Field("delivery") int delivery,
            @Field("discount") int discount,
            @Field("total") double total,
            @Field("address_id") int address,
            @Field("phone") String phone,
            @Field("user_id") int userId,
            @Field("schedule") String schedule,
            @Field("order_time") String orderTime,
            @Field("creation_time") String creationTime,
            @Field("location") String location
    );


    @FormUrlEncoded
    @POST("get_order_products")
    Call<Products> getOrderProducts(
            @Field("order_id") int id
    );



    @FormUrlEncoded
    @POST("get_orders")
    Call<MyOrdersResult> getMyOrders(
            @Field("user_id") int userId
    );
}
