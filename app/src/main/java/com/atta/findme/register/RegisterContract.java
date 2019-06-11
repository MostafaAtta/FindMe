package com.atta.findme.register;

import com.atta.findme.model.User;

public interface RegisterContract {

    interface View{

        void showMessage(String error);

        void showViewError(String view, String error);

        void navigateToMain();

        void setDialog();

        boolean validate(String name, String email, String password, String passwordConfirm, String phone, String locationSting);

    }

    interface Presenter{

        void register(User user);



    }
}
