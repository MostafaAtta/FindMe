package com.atta.findme.profile;

import android.content.Context;

import com.atta.findme.model.APIService;
import com.atta.findme.model.APIUrl;
import com.atta.findme.model.Address;
import com.atta.findme.model.Profile;
import com.atta.findme.model.Result;
import com.atta.findme.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View mView;

    private Context mContext;

    public ProfilePresenter(ProfileContract.View mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getProfile(int userId) {

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
        Call<Profile> call = service.getProfile(userId);

        //calling the api
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {

                if (response.body() != null){
                    if (!response.body().getError()){

                        User user = response.body().getUser();

                        mView.showProfile(user);

                        if (!response.body().getAddressesError()){

                            Address address = response.body().getAddress();



                            mView.showAddresses(address);
                        }else {

                            String error = response.body().getErrorMsg();
                            mView.showAddressesMessage(error);

                        }


                    }
                }else {
                    mView.showMessage("An error");
                }

            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

                mView.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void updateProfile(User user) {

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
        Call<Result> call = service.updateProfile(
                user.getId(),
                user.getName(),
                user.getPhone(),
                user.getLocation()
        );

        //calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                //displaying the message from the response as toast
                if (response.body() != null) {


                    mView.showMessage(response.body().getMessage());


                    if (!response.body().getError()){


                        mView.moveToMain();
                    }


                }else {
                    mView.showMessage("An error");
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                mView.showMessage(t.getMessage());
            }
        });
    }


}
