package com.atta.findme.fragments;

import com.atta.findme.model.Shop;

import java.util.ArrayList;

public interface FragmentsContract {

    interface View {

        void showRecyclerView(ArrayList<Shop> shops);

        void showMessage(String message);
    }

    interface Presenter {

        void getShops(String cat);
    }
}
