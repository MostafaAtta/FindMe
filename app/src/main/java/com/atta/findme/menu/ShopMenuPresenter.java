package com.atta.findme.menu;

import android.content.Context;
import android.os.AsyncTask;

import com.atta.findme.DB.DatabaseClient;
import com.atta.findme.model.APIService;
import com.atta.findme.model.APIUrl;
import com.atta.findme.model.CartItem;
import com.atta.findme.model.Product;
import com.atta.findme.model.Products;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopMenuPresenter implements ShopMenuContract.Presenter {


    private ShopMenuContract.View mView;

    private Context mContext;


    public ShopMenuPresenter(Context mContext, ShopMenuContract.View mView) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getProducts(String cat, int shopId) {

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
        Call<Products> call = service.getProducts(cat, shopId);

        //calling the api
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {

                if (response.body() != null){

                        if (response.body().getProducts() != null){


                            ArrayList<Product> products = response.body().getProducts();

                            if (products.size() > 0){

                                checkCartItems(products);

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

    @Override
    public void addToCart(final Product product) {


        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                CartItem cartItem = new CartItem();
                cartItem.setProductId(product.getId());
                cartItem.setProductName(product.getName());
                cartItem.setCount(1);
                cartItem.setProductPrice(String.valueOf(product.getPrice()));

                //adding to database
                DatabaseClient.getInstance(mContext).getAppDatabase()
                        .itemDao()
                        .insert(cartItem);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mView.increaseCartSign();
                product.setInCart(true);
                product.setCartCount(1);
            }
        }

        SaveTask st = new SaveTask();
        st.execute();

    }


    @Override
    public void decreaseCartItem(final int id) {


        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //adding to database
                DatabaseClient.getInstance(mContext).getAppDatabase()
                        .itemDao()
                        .decreaseCartItem(id);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        SaveTask st = new SaveTask();
        st.execute();

    }

    @Override
    public void increaseCartItem(final int id) {


        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //adding to database
                DatabaseClient.getInstance(mContext).getAppDatabase()
                        .itemDao()
                        .increaseCartItem(id);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        SaveTask st = new SaveTask();
        st.execute();

    }

    @Override
    public void removeCartItem(final int id) {


        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //adding to database
                DatabaseClient.getInstance(mContext).getAppDatabase()
                        .itemDao()
                        .deleteCartItem(id);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mView.decreaseCartSign();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();

    }
    @Override
    public void removeCartItems() {


        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //adding to database
                DatabaseClient.getInstance(mContext).getAppDatabase()
                        .itemDao()
                        .deleteAll();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mView.clearCartSign();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();

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

                mView.setCounterFabCount(numOfCartItem);

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


    @Override
    public void checkCartItems(final ArrayList<Product> products) {


        for (int i = 0; i < products.size(); ++ i) {


            final Product product = products.get(i);

            final int finalI = i;
            class GetTasks extends AsyncTask<Void, Void, CartItem> {

                @Override
                protected CartItem doInBackground(Void... voids) {
                    CartItem cartItem = DatabaseClient
                            .getInstance(mContext)
                            .getAppDatabase()
                            .itemDao()
                            .checkItem(product.getId());
                    return cartItem;
                }

                @Override
                protected void onPostExecute(CartItem cartItem) {
                    super.onPostExecute(cartItem);

                    if (cartItem == null) {
                        product.setInCart(false);
                    } else {
                        product.setInCart(true);
                        product.setCartCount(cartItem.getCount());
                    }

                    if (finalI == products.size()-1){


                        mView.showRecyclerView(products);
                    }

                }
            }

            GetTasks gt = new GetTasks();
            gt.execute();

        }

    }
}
