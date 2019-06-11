package com.atta.findme.menu;

import com.atta.findme.model.Product;

import java.util.ArrayList;

public interface ShopMenuContract {

    interface View {

        void showRecyclerView(ArrayList<Product> products);

        void showMessage(String message);

        void addToCart(Product product);

        void increaseCartSign();

        void decreaseCartSign();

        void clearCartSign();

        void setCounterFabCount(int numOfCartItem);

    }

    interface Presenter {

        void getProducts(String cat, int shopId);

        void addToCart(Product product);

        void getCartItemsNum();

        void checkCartItems(final ArrayList<Product> products) ;

        void decreaseCartItem(final int id);

        void increaseCartItem(final int id);

        void removeCartItem(final int id);

        void removeCartItems();
    }
}
