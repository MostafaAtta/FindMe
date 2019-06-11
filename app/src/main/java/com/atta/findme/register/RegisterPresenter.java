package com.atta.findme.register;

import android.app.ProgressDialog;
import android.content.Context;

import com.atta.findme.model.APIService;
import com.atta.findme.model.APIUrl;
import com.atta.findme.model.Result;
import com.atta.findme.model.SessionManager;
import com.atta.findme.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterPresenter implements RegisterContract.Presenter{

    private RegisterContract.View mView;

    private ProgressDialog mProgressDialog;

    private Context mContext;


    public RegisterPresenter(RegisterContract.View view, ProgressDialog progressDialog, Context context) {

        mView = view;

        mProgressDialog = progressDialog;

        mContext = context;
    }

    @Override
    public void register(User user) {

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);


        //defining the call
        Call<Result> call = service.createUser(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getLocation()
        );

        //calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //hiding progress dialog
                if(mProgressDialog != null || mProgressDialog.isShowing() ){
                    mProgressDialog.dismiss();
                }

                //displaying the message from the response as toast
                mView.showMessage(response.body().getMessage());
                //if there is no error
                if (!response.body().getError()) {
                    //starting profile activity
                    SessionManager.getInstance(mContext).createLoginSession(response.body().getUser());
                    mView.navigateToMain();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                if(mProgressDialog != null || mProgressDialog.isShowing() ){
                    mProgressDialog.dismiss();
                }
                mView.showMessage(t.getMessage());
            }
        });
    }

}
