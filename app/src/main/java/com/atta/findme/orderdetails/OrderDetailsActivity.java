package com.atta.findme.orderdetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.atta.findme.R;
import com.atta.findme.model.Order;
import com.atta.findme.model.Product;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity implements OrderDetailsContract.View{

    Order order;

    ArrayList<Product> products;

    OrderDetailsPresenter orderDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        orderDetailsPresenter = new OrderDetailsPresenter(this, this);

        if (getIntent().getSerializableExtra("order") != null){
            order = (Order) getIntent().getSerializableExtra("order");

            orderDetailsPresenter.getOrderDishes(order.getId());

        }
    }

    @Override
    public void showMessage(String error) {

    }

    @Override
    public void showRecyclerView(ArrayList<Product> products) {

    }

    @Override
    public void showOrderDialog(Product product) {

    }

    @Override
    public void updateText() {

    }
}
