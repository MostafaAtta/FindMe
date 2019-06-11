package com.atta.findme.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.atta.findme.model.CartItem;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM cartItem")
    List<CartItem> getAll();


    @Query("SELECT * FROM cartItem WHERE id = :id")
    CartItem getItem(int id);


    @Query("SELECT * FROM cartItem WHERE productId = :productId")
    CartItem checkItem(int productId);

    @Query("DELETE FROM cartItem")
    void deleteAll();


    @Query("SELECT * from cartItem where productName = :productName")
    CartItem getItem(String productName);

    @Insert
    void insert(CartItem cartItem);

    @Delete
    void delete(CartItem cartItem);

    @Update
    void update(CartItem cartItem);

    @Query("UPDATE cartItem set count = count+1 where productId = :productId")
    void increaseCartItem(int productId);


    @Query("UPDATE cartItem set count = count-1 where productId = :productId")
    void decreaseCartItem(int productId);


    @Query("DELETE FROM cartItem where productId = :productId")
    void deleteCartItem(int productId);
}
