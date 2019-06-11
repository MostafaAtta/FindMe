package com.atta.findme.orderdetails;

import com.atta.findme.model.Product;

import java.util.ArrayList;

public interface OrderDetailsContract {

    interface View{

        void showMessage(String error);

        void showRecyclerView(ArrayList<Product> products);

        void showOrderDialog(Product product);

        void updateText();
    }

    interface Presenter{

        void getOrderDishes(int orderId) ;

    }
}
