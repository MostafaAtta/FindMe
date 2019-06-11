package com.atta.findme.orderdetails;

import android.content.Context;

import com.atta.findme.model.APIService;
import com.atta.findme.model.APIUrl;
import com.atta.findme.model.Product;
import com.atta.findme.model.Products;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderDetailsPresenter implements OrderDetailsContract.Presenter {

    private OrderDetailsContract.View mView;

    private Context mContext;

    public OrderDetailsPresenter(OrderDetailsContract.View mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getOrderDishes(int orderId) {

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Defining the user object as we need to pass it with the call
        //User user = new User(name, email, password, phone, birthdayString, locationSting);

        //defining the call
        Call<Products> call = service.getOrderProducts(orderId);

        //calling the api
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {

                if (response.body() != null){

                        if (response.body().getProducts() != null){


                            ArrayList<Product> products = response.body().getProducts();

                            if (products.size() > 0){

                            }
                        }else {
                            mView.showMessage("An error");
                        }


                }else {
                    mView.showMessage("An error");
                }

            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {

                mView.showMessage(t.getMessage());
            }
        });
    }


}
