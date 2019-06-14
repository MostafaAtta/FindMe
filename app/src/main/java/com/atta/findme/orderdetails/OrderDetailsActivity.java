package com.atta.findme.orderdetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atta.findme.R;
import com.atta.findme.main.MainActivity;
import com.atta.findme.model.LinearAdapter;
import com.atta.findme.model.Order;
import com.atta.findme.model.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity implements OrderDetailsContract.View, View.OnClickListener {

    Order order;

    List<Product> products;

    TextView timeTv, addressTv, mobileTv, subTotalTv, deliveryFeesTv, totalTx;

    RecyclerView recyclerView;

    ImageView submittedImage, preparedImage, deliveredImage, backtoMain;

    OrderDetailsPresenter orderDetailsPresenter;

    LinearAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        initiateViews();

        orderDetailsPresenter = new OrderDetailsPresenter(this);

        if (getIntent().getSerializableExtra("order") != null){
            order = (Order) getIntent().getSerializableExtra("order");

            setOrderData();

            orderDetailsPresenter.getOrderDishes(order.getId());
        }


    }


    private void initiateViews() {
        // National ID, Password input text
        timeTv = findViewById(R.id.tv_delivery_time_sum);
        addressTv = findViewById(R.id.tv_address_sum);
        addressTv.setOnClickListener(this);
        mobileTv = findViewById(R.id.tv_phone_sum);
        mobileTv.setOnClickListener(this);
        subTotalTv = findViewById(R.id.tv_subtotal_sum);
        deliveryFeesTv = findViewById(R.id.tv_delivery_sum);
        totalTx = findViewById(R.id.tv_total_sum);

        recyclerView = findViewById(R.id.recycler_cart2);


        submittedImage = findViewById(R.id.imageView);
        preparedImage = findViewById(R.id.imageView2);
        deliveredImage = findViewById(R.id.imageView3);

        backtoMain  = findViewById(R.id.btn_back_to_main);
        backtoMain.setOnClickListener(this);

    }

    private void setOrderData() {
        timeTv.setText(order.getOrderTime());
        addressTv.setText(order.getFullAddress().getFullAddress());
        mobileTv.setText(order.getMobile());
        subTotalTv.setText(order.getSubtotalPrice() + " EGP");
        deliveryFeesTv.setText(order.getDelivery() + " EGP");
        totalTx.setText(order.getTotalPrice() + " EGP");

        switch (order.getStatus()){
            case 0:
                submittedImage.setImageResource(R.drawable.dot_and_circle);
                preparedImage.setImageResource(R.drawable.circle);
                deliveredImage.setImageResource(R.drawable.circle);
                preparedImage.setOnClickListener(this);
                break;

            case 1:
                submittedImage.setImageResource(R.drawable.circle);
                preparedImage.setImageResource(R.drawable.dot_and_circle);
                deliveredImage.setImageResource(R.drawable.circle);
                preparedImage.setOnClickListener(null);
                deliveredImage.setOnClickListener(this);
                break;

            case 2:
                submittedImage.setImageResource(R.drawable.circle);
                preparedImage.setImageResource(R.drawable.circle);
                deliveredImage.setImageResource(R.drawable.dot_and_circle);
                preparedImage.setOnClickListener(null);
                deliveredImage.setOnClickListener(null);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == addressTv){


            double latitude = order.getFullAddress().getLatitude();
            double longitude = order.getFullAddress().getLongitude();
            String label = "Deliver Address!";
            String uriBegin = "geo:" + latitude + "," + longitude;
            String query = latitude + "," + longitude + "(" + label + ")";
            String encodedQuery = Uri.encode(query);
            String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
            Uri uri = Uri.parse(uriString);
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);


        }else if (v == mobileTv){

            String mobile = order.getMobile();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + mobile));
            startActivity(intent);
        }else if (v == backtoMain){

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void showMessage(String error) {

        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRecyclerView(ArrayList<Product> products) {
        this.products = products;

        myAdapter = new LinearAdapter(this, products, order);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);
    }

}
