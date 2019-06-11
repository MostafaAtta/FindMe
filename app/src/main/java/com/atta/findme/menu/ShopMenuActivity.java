package com.atta.findme.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.atta.findme.cart.CartActivity;
import com.atta.findme.R;
import com.atta.findme.model.Product;
import com.atta.findme.model.ProductsAdapter;
import com.atta.findme.model.Shop;

import java.util.ArrayList;

public class ShopMenuActivity extends AppCompatActivity implements ShopMenuContract.View{

    RecyclerView recyclerView;

    ShopMenuPresenter shopMenuPresenter;

    CounterFab counterFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_menu);


        recyclerView = findViewById(R.id.recycler);

        counterFab = findViewById(R.id.fab);

        shopMenuPresenter = new ShopMenuPresenter( this, this);

        Shop shop = (Shop) getIntent().getSerializableExtra("shop");

        shopMenuPresenter.getProducts(shop.getCategory(), shop.getId());

        shopMenuPresenter.getCartItemsNum();

        counterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (counterFab.getCount() > 0) {
                    Intent intent = new Intent(ShopMenuActivity.this, CartActivity.class);
                    startActivity(intent);
                }else showMessage("Cart is empty");

            }
        });
    }

    @Override
    public void showRecyclerView(ArrayList<Product> products) {

        ProductsAdapter myAdapter = new ProductsAdapter(this, shopMenuPresenter, this, products);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void addToCart(Product product) {

        shopMenuPresenter.addToCart(product);
    }

    @Override
    public void increaseCartSign() {

        counterFab.increase();
    }


    @Override
    public void decreaseCartSign() {

        counterFab.decrease();
        counterFab.setCount(0);
    }

    @Override
    public void clearCartSign() {

        counterFab.setCount(0);
    }

    @Override
    public void setCounterFabCount(int numOfCartItem) {

        counterFab.setCount(numOfCartItem);
    }
}
