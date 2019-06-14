package com.atta.findme.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atta.findme.R;
import com.atta.findme.orderdetails.OrderDetailsContract;

import java.util.ArrayList;
import java.util.List;

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.MyViewHolder> {

    private List<Product> products;

    Order order;

    OrderDetailsContract.View orderView;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.name_txt);
            count = view.findViewById(R.id.count_sam);

        }
    }

    public List<Product> getList(){
        return products;
    }


    public LinearAdapter(OrderDetailsContract.View view, ArrayList<Product> data, Order order) {

        this.products = data;
        this.orderView = view;
        this.order = order;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;


            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        final Product product = products.get(position) ;

        final String name = product.getName();

        holder.title.setText(name);


            int count = order.getProductsList().get(position).getQuantity();

            holder.count.setText("X" + count);




    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void removeItem(int position) {
        products.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

}
