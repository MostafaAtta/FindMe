package com.atta.findme.main;

import android.content.Context;
import android.os.AsyncTask;

import com.atta.findme.DB.DatabaseClient;
import com.atta.findme.model.CartItem;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {


    private MainContract.View mView;

    private Context mContext;


    public MainPresenter(Context mContext, MainContract.View mView) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getCartItemsNum() {


        class GetTasks extends AsyncTask<Void, Void, List<CartItem>> {

            @Override
            protected List<CartItem> doInBackground(Void... voids) {
                List<CartItem> taskList = DatabaseClient
                        .getInstance(mContext)
                        .getAppDatabase()
                        .itemDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<CartItem> cartItems) {
                super.onPostExecute(cartItems);

                int numOfCartItem = cartItems.size();

                mView.setCartCount(numOfCartItem);

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

}
