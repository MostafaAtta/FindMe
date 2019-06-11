package com.atta.findme.myorders;

import com.atta.findme.model.Order;

import java.util.ArrayList;

public interface MyOrdersContract {

    interface View{

        void showMessage(String error);

        void showRecyclerView(ArrayList<Order> orders);

        void updateText();

        void showOrderDetails(Order order);
    }

    interface Presenter{

        void getMyOrders(int userId) ;

    }
}
