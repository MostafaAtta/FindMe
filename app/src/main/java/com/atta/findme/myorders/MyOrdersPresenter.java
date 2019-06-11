package com.atta.findme.myorders;

import android.content.Context;

import com.atta.findme.model.APIService;
import com.atta.findme.model.APIUrl;
import com.atta.findme.model.MyOrdersResult;
import com.atta.findme.model.Order;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyOrdersPresenter implements MyOrdersContract.Presenter {

    private MyOrdersContract.View mView;

    private Context mContext;

    public MyOrdersPresenter(MyOrdersContract.View mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getMyOrders(int userId) {

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
        Call<MyOrdersResult> call = service.getMyOrders(userId);

        //calling the api
        call.enqueue(new Callback<MyOrdersResult>() {
            @Override
            public void onResponse(Call<MyOrdersResult> call, Response<MyOrdersResult> response) {

                if (response.body() != null){
                    if (!response.body().getError()){

                        if (response.body().getOrders() != null){


                            ArrayList<Order> orders = response.body().getOrders();

                            if (orders.size() > 0){

                                mView.showRecyclerView(orders);
                            }else {

                                mView.updateText();
                            }
                        }else {

                            mView.updateText();
                        }

                    }
                }else {
                    mView.showMessage("An error");
                }

            }

            @Override
            public void onFailure(Call<MyOrdersResult> call, Throwable t) {

                mView.showMessage(t.getMessage());
            }
        });
    }

}
