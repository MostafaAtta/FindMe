package com.atta.findme.fragments;

import com.atta.findme.model.APIService;
import com.atta.findme.model.APIUrl;
import com.atta.findme.model.Shop;
import com.atta.findme.model.Shops;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentsPresenter implements FragmentsContract.Presenter {


    private FragmentsContract.View mView;


    public FragmentsPresenter(FragmentsContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getShops(String cat) {

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
        Call<Shops> call = service.getShops(cat);

        //calling the api
        call.enqueue(new Callback<Shops>() {
            @Override
            public void onResponse(Call<Shops> call, Response<Shops> response) {

                if (response.body() != null){

                        if (response.body().getShops() != null){


                            ArrayList<Shop> shops = response.body().getShops();

                            if (shops.size() > 0){

                                mView.showRecyclerView(shops);
                            }
                        }else {
                            mView.showMessage("An error");
                        }
                }else {
                    mView.showMessage("An error");
                }

            }

            @Override
            public void onFailure(Call<Shops> call, Throwable t) {

                mView.showMessage(t.getMessage());
            }
        });
    }
}
