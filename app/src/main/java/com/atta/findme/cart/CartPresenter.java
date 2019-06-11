package com.atta.findme.cart;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.atta.findme.DB.DatabaseClient;
import com.atta.findme.model.APIService;
import com.atta.findme.model.APIUrl;
import com.atta.findme.model.Address;
import com.atta.findme.model.CartAdapter;
import com.atta.findme.model.CartItem;
import com.atta.findme.model.Order;
import com.atta.findme.model.OrdersResult;
import com.atta.findme.model.Profile;
import com.atta.findme.model.Result;
import com.atta.findme.model.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartPresenter implements CartContract.Presenter {


    private CartContract.View mView;

    private Context mContext;

    private RecyclerView mRecyclerView;

    private RecyclerView mSummaryRecyclerView;


    private ProgressDialog mProgressDialog;

    public CartPresenter(CartContract.View view, Context context, RecyclerView recyclerView, RecyclerView summaryRecyclerView, ProgressDialog progressDialog) {

        mView = view;

        mContext = context;

        mRecyclerView = recyclerView;

        mSummaryRecyclerView = summaryRecyclerView;

        mProgressDialog = progressDialog;
    }

    @Override
    public void getCartItems(final boolean view, @Nullable final int userId, @Nullable final String location,
                             final int deliveryAdd, @Nullable final String mobile, @Nullable final String schedule, @Nullable final String orderTime) {


        class GetTasks extends AsyncTask<Void, Void, List<CartItem>> {

            @Override
            protected List<CartItem> doInBackground(Void... voids) {
                List<CartItem> cartItems = DatabaseClient
                        .getInstance(mContext)
                        .getAppDatabase()
                        .itemDao()
                        .getAll();
                return cartItems;
            }

            @Override
            protected void onPostExecute(List<CartItem> cartItems) {
                super.onPostExecute(cartItems);

                double totalPrice = totalPriceCalculation(cartItems);
                int deliveryPrice = 5;
                int discount = 5;

                if (view){

                    if (!cartItems.isEmpty()){
                        mView.enableOrderBtn();

                        CartAdapter adapter = new CartAdapter(mContext, cartItems, CartPresenter.this, mView, false);
                        mRecyclerView.setAdapter(adapter);
/*

                        CartAdapter summaryAdapter = new CartAdapter(mContext, cartItems, CartPresenter.this, mView, true);
                        mSummaryRecyclerView.setAdapter(summaryAdapter);
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext,
                                LinearLayoutManager.VERTICAL);
                        dividerItemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.line_divider));
                        mSummaryRecyclerView.addItemDecoration(dividerItemDecoration);*/


                        mView.setTotalPrice(totalPrice, deliveryPrice);
                    }else {

                        mView.disableOrderBtn();
                    }
                }else {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

                    String creationTime = sdf.format(new Date());

                    Map<String, String> products = new HashMap<String, String>(), count = new HashMap<String, String>();
                    for (CartItem element : cartItems) {
                        products.put("products[" + cartItems.indexOf(element) + "]", String.valueOf(element.getProductId()));
                        count.put("quantities[" + cartItems.indexOf(element) + "]", String.valueOf(element.getCount()));
                    }

                    Order order = new Order(products, count,  totalPrice, deliveryPrice , totalPrice+deliveryPrice
                            , discount, userId, location, deliveryAdd, mobile,  schedule, orderTime, creationTime);

                    addOrder(order);
                }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    @Override
    public double totalPriceCalculation(List<CartItem> cartItems) {
        double total = 0;

        for (CartItem cartItem : cartItems){
            total += (Double.valueOf(cartItem.getProductPrice())* cartItem.getCount());
        }
        return total;
    }


    @Override
    public void decreaseCartItem(final CartItem cartItem) {


        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //adding to database
                DatabaseClient.getInstance(mContext).getAppDatabase()
                        .itemDao()
                        .decreaseCartItem(cartItem.getProductId());
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
    public void increaseCartItem(final CartItem cartItem) {


        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //adding to database
                DatabaseClient.getInstance(mContext).getAppDatabase()
                        .itemDao()
                        .increaseCartItem(cartItem.getProductId());
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

                mView.backToMenu();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();

    }

    @Override
    public void addOrder(Order order) {


        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);


        //defining the call
        Call<OrdersResult> call = service.addOrder(
                order.getProducts(),
                order.getCount(),
                order.getSubtotalPrice(),
                order.getDelivery(),
                order.getDiscount(),
                order.getTotalPrice(),
                order.getAddress(),
                order.getMobile(),
                order.getUserId(),
                order.getSchedule(),
                order.getOrderTime(),
                order.getCreationTime(),
                order.getLocation()
        );

        //calling the api
        call.enqueue(new Callback<OrdersResult>() {
            @Override
            public void onResponse(Call<OrdersResult> call, Response<OrdersResult> response) {
                //hiding progress dialog
                if(mProgressDialog != null || mProgressDialog.isShowing() ){
                    mProgressDialog.dismiss();
                }

                if (response.body() != null) {

                    //displaying the message from the response as toast
                    mView.showMessage(response.body().getMessage());
                    //if there is no error
                    if (!response.body().getError()) {
                        //starting Main activity
                        removeCartItems();
                        mView.navigateToMain();
                    }

                }
            }

            @Override
            public void onFailure(Call<OrdersResult> call, Throwable t) {
                if(mProgressDialog != null || mProgressDialog.isShowing() ){
                    mProgressDialog.dismiss();
                }
                mView.showMessage(t.getMessage());
            }
        });
    }


    @Override
    public void login(String email, String password) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        Call<Result> call = service.userLogin(email, password);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {


                mView.dismissProgressDialog();
                if (!response.body().getError()) {
                    mView.showMessage("logged in successfully");
                    SessionManager.getInstance(mContext).createLoginSession(response.body().getUser());
                    mView.navigateToMain();
                } else {

                    mView.showMessage("Invalid email or password");
                }


            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {


                mView.dismissProgressDialog();

                mView.showMessage(t.getMessage());



            }
        });

    }

    @Override
    public boolean validate(String email, String password) {


        boolean valid = true;
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



        if (!email.matches(emailPattern) || email.isEmpty())
        {
            mView.showMessage("Invalid email address");
            mView.showViewError("email","Invalid email address");
            valid = false;

        }else {

            mView.showViewError("email",null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mView.showMessage("password must be between 4 and 10 alphanumeric characters");
            mView.showViewError("password","password must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mView.showViewError("password",null);
        }



        return valid;

    }


    @Override
    public void getAddresses(int userId) {

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
        Call<Profile> call = service.getAddresses(userId);

        //calling the api
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {

                if (response.body() != null){
                    if (!response.body().getAddressesError()){

                        Address address = response.body().getAddress();



                        mView.showAddresses(address);
                    }else {

                        String error = response.body().getErrorMsg();
                        mView.showAddressesMessage(error);

                    }
                }else {
                    mView.showAddressesMessage("something wrong, please try again");
                }

            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

                mView.showMessage(t.getMessage());
            }
        });
    }
}
