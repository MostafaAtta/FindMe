package com.atta.findme.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.atta.findme.R;
import com.atta.findme.model.Shop;
import com.atta.findme.model.ShopsAdapter;

import java.util.ArrayList;

public class CategoryFragment extends Fragment implements FragmentsContract.View {

    FragmentsPresenter fragmentsPresenter;

    RecyclerView recyclerView;

    String category;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //searchPresenter = new SearchPresenter(this, getContext());
        //return rootView;
        android.view.View view = inflater.inflate(R.layout.fragment,container,false);
        recyclerView = view.findViewById(R.id.recycler);

        fragmentsPresenter = new FragmentsPresenter(this);


        if (getArguments() != null) {

            category = getArguments().getString("category");
        }

        fragmentsPresenter.getShops(category);

        final SwipeRefreshLayout mySwipeRefreshLayout = view.findViewById(R.id.swiperefresh);

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //searchPresenter.getProperties();
                        mySwipeRefreshLayout.setRefreshing(false);

                        fragmentsPresenter.getShops(category);
                    }
                }
        );

/*
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("action_category_updated".equals(intent.getAction())) {

                    String category = intent.getStringExtra("category");
                    fragmentsPresenter.getShops(category);
                }
            }
        };
        filter = new IntentFilter("action_category_updated");

        getActivity().registerReceiver(mReceiver, filter);*/

        return view;
    }

    @Override
    public void showRecyclerView(ArrayList<Shop> shops) {

        ShopsAdapter myAdapter = new ShopsAdapter(getContext(), shops);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }
}
