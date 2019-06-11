package com.atta.findme.model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atta.findme.R;
import com.atta.findme.menu.ShopMenuActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.MyViewHolder> {

    private Context mContext;

    private List<Shop> shops;

    public ShopsAdapter(Context mContext, List<Shop> shops) {
        this.mContext = mContext;
        this.shops = shops;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shop_list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final Shop shop = shops.get(i) ;

        final int id = shop.getId();

        final double rating = shop.getRating();
        final String name = shop.getName();
        final String address = shop.getDistrict();
        final String logoImg = APIUrl.Images_BASE_URL + shop.getLogoImage();
        final String bgImage = APIUrl.Images_BASE_URL + shop.getBgImage();

        Picasso.get()
                .load(logoImg)
                .resize(50, 50)
                .centerCrop()
                .into(myViewHolder.logo);

        Picasso.get()
                .load(bgImage)
                .resize(190, 50)
                .centerCrop()
                .into(myViewHolder.background);

        myViewHolder.shopNameTv.setText(name);
        myViewHolder.rating.setText(String.valueOf(rating));
        myViewHolder.address.setText(address);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShopMenuActivity.class);
                intent.putExtra("shop", shop);
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return shops.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView shopNameTv, rating, address;
        public ImageView background, logo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shopNameTv = itemView.findViewById(R.id.name_textView);
            rating = itemView.findViewById(R.id.rating_textView);
            address = itemView.findViewById(R.id.address);
            background = itemView.findViewById(R.id.bg);
            logo = itemView.findViewById(R.id.logo);
        }
    }
}
