package com.atta.findme.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atta.findme.R;
import com.atta.findme.menu.ShopMenuContract;
import com.atta.findme.menu.ShopMenuPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private ShopMenuContract.View mView;

    private ShopMenuPresenter shopMenuPresenter;

    private List<Product> products;

    Context mContext;

    public ProductsAdapter(Context mContext, ShopMenuPresenter shopMenuPresenter, ShopMenuContract.View mView, List<Product> products) {
        this.mView = mView;
        this.products = products;
        this.shopMenuPresenter = shopMenuPresenter;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        final Product product = products.get(i) ;

        final int id = product.getId();

        final double price = product.getPrice();
        final String name = product.getName();
        final String brand = product.getBrand();
        final String amount = product.getAmount();
        final boolean inCart = product.isInCart();

        if (inCart){

            myViewHolder.linearLayout.setVisibility(View.VISIBLE);
            myViewHolder.addToCard.setVisibility(View.GONE);
            myViewHolder.constraintLayout.setBackgroundResource(R.drawable.border);
            final int cartCount = product.getCartCount();

            myViewHolder.count.setText(String.valueOf(cartCount));
        }

        final String image = APIUrl.Images_BASE_URL + product.getImage();

        Picasso.get()
                .load(image)
                .into(myViewHolder.image);


        myViewHolder.nameTv.setText(name);
        myViewHolder.brandTv.setText(brand);
        myViewHolder.amountTv.setText(amount);
        myViewHolder.priceTv.setText("EGP" + price);

        if (product.getStock() > 0){
            myViewHolder.addToCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myViewHolder.linearLayout.setVisibility(View.VISIBLE);
                    myViewHolder.addToCard.setVisibility(View.GONE);
                    myViewHolder.constraintLayout.setBackgroundResource(R.drawable.border);
                    mView.addToCart(product);
                }
            });
        }else {
            myViewHolder.addToCard.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            myViewHolder.addToCard.setText("out of stock");
        }



        myViewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopMenuPresenter.increaseCartItem(product.getId());
                product.setCartCount(product.getCartCount()+1);
                myViewHolder.count.setText(String.valueOf(product.getCartCount()));
            }
        });

        myViewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getCartCount() == 1) {
                    myViewHolder.linearLayout.setVisibility(View.INVISIBLE);
                    myViewHolder.addToCard.setVisibility(View.VISIBLE);
                    myViewHolder.constraintLayout.setBackground(null);
                    product.setInCart(false);
                    product.setCartCount(0);
                    mView.decreaseCartSign();
                    shopMenuPresenter.removeCartItem(product.getId());

                }else {
                    shopMenuPresenter.decreaseCartItem(product.getId());
                    product.setCartCount(product.getCartCount()-1);
                    myViewHolder.count.setText(String.valueOf(product.getCartCount()));
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv, brandTv, amountTv, priceTv, addToCard, count;
        ImageView image, add, remove;

        ConstraintLayout constraintLayout;

        LinearLayout linearLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.item_name);
            brandTv = itemView.findViewById(R.id.item_brand);
            amountTv = itemView.findViewById(R.id.item_amount);
            priceTv = itemView.findViewById(R.id.item_price);
            image = itemView.findViewById(R.id.item_image);
            add = itemView.findViewById(R.id.add);
            remove = itemView.findViewById(R.id.remove);
            count = itemView.findViewById(R.id.item_count);

            addToCard = itemView.findViewById(R.id.add_to_cart);

            constraintLayout = itemView.findViewById(R.id.layout);

            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
