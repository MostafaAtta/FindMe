package com.atta.findme.main;

public interface MainContract {

    interface View {


        void showMessage(String message);

        void setCartCount(int numOfCartItem);

        void renameLoginItem();

    }

    interface Presenter {


        void getCartItemsNum();
    }
}
