package com.atta.findme.model;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atta.findme.R;
import com.atta.findme.cart.CartContract;
import com.atta.findme.cart.CartPresenter;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.TasksViewHolder>  {


    private Context mCtx;
    private List<CartItem> cartItems;
    private CartPresenter cartPresenter;
    private boolean summary;

    CartContract.View mView;

    public CartAdapter(Context mCtx, List<CartItem> cartItems, CartPresenter cartPresenter, CartContract.View mView, boolean summary) {
        this.mCtx = mCtx;
        this.cartItems = cartItems;
        this.cartPresenter = cartPresenter;
        this.mView = mView;
        this.summary = summary;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.cart_item, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TasksViewHolder holder, final int position) {
        final CartItem cartItem = cartItems.get(position);

        double price = Double.valueOf(cartItem.getProductPrice()) * cartItem.getCount();

        holder.textViewProductName.setText(cartItem.getProductName());
        holder.textViewProductPrice.setText(String.valueOf(price) + " EGP");
        holder.textViewProductCount.setText(String.valueOf(cartItem.getCount()));


        holder.addOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cartPresenter.increaseCartItem(cartItem);

                int count = cartItem.getCount() + 1;

                cartItem.setCount(count);

                holder.textViewProductCount.setText(String.valueOf(count));

                double price = Double.valueOf(cartItem.getProductPrice())* count;

                holder.textViewProductPrice.setText(price + " EGP");


                mView.setTotalPrice(cartPresenter.totalPriceCalculation(cartItems), 5);


            }
        });
        holder.removeOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cartItem.getCount() != 1){

                    //int count = cartItem.getCount();

                    //cartItem.setCount(count - 1);

                    cartPresenter.decreaseCartItem(cartItem);



                    int count = cartItem.getCount() - 1;

                    cartItem.setCount(count);

                    holder.textViewProductCount.setText(String.valueOf(count));

                    double price = Double.valueOf(cartItem.getProductPrice())* count;

                    holder.textViewProductPrice.setText(price + " EGP");

                    mView.setTotalPrice(cartPresenter.totalPriceCalculation(cartItems), 5);


                }else {

                    cartPresenter.removeCartItem(cartItem.getProductId());
                    cartItems.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, cartItems.size());


                    if (cartItems.size() == 0){
                        //mView.backToMenu();
                    }else {

                        mView.setTotalPrice(cartPresenter.totalPriceCalculation(cartItems), 5);
                    }
                }
            }
        });
        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cartPresenter.removeCartItem(cartItem.getProductId());
                cartItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());

                if (cartItems.size() == 0){
                    //mView.backToMenu();
                }else {

                    mView.setTotalPrice(cartPresenter.totalPriceCalculation(cartItems), 5);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class TasksViewHolder  extends RecyclerView.ViewHolder {


        TextView textViewProductName, textViewProductPrice, textViewProductCount;

        ImageView addOne, removeOne, removeItem;

        ConstraintLayout constraintLayout;

        public TasksViewHolder(View itemView) {
            super(itemView);

            textViewProductName = itemView.findViewById(R.id.item_name);
            textViewProductPrice = itemView.findViewById(R.id.item_price);
            textViewProductCount = itemView.findViewById(R.id.count);

            addOne = itemView.findViewById(R.id.add_one);
            removeOne = itemView.findViewById(R.id.remove_one);
            removeItem = itemView.findViewById(R.id.remove_item);

            constraintLayout = itemView.findViewById(R.id.background_layout);

            if (summary){

                addOne.setVisibility(View.GONE);
                removeOne.setVisibility(View.GONE);
                removeItem.setVisibility(View.GONE);

                constraintLayout.setBackgroundResource(0);

                constraintLayout.requestLayout();

                constraintLayout.getLayoutParams().height = dpToPx(40);
/*
                final float scale = mCtx.getResources().getDisplayMetrics().density;
                int pixels = (int) (60 * scale + 0.5f);

                RelativeLayout.LayoutParams constraintLayout = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, pixels);

                constraintLayout.height*/
            }


        }

    }

    public int dpToPx(int dp) {

        float density = mCtx.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
}
