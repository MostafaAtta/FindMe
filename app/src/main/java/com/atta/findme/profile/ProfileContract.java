package com.atta.findme.profile;

import com.atta.findme.model.Address;
import com.atta.findme.model.User;

public interface ProfileContract {

    interface View{

        void showAddressesMessage(String message);

        void showAddresses(Address mAddress);

        void showProfile(User user);

        void showMessage(String message);

        void moveToMain();
    }

    interface Presenter{

        void getProfile(int userId) ;

        void updateProfile(User user) ;

    }
}
