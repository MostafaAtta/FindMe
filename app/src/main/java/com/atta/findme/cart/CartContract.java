package com.atta.findme.cart;

import android.widget.EditText;

import com.atta.findme.model.Address;
import com.atta.findme.model.CartItem;
import com.atta.findme.model.Order;

import java.util.List;

public interface CartContract {

    interface View{

        void backToMenu();

        void enableOrderBtn();

        void disableOrderBtn();

        void setTotalPrice(double subtotal, int delivery);

        void showMessage(String message);

        void setProgressDialog();

        void navigateToMain();

        void createOrder(int deliveryAdd);

        void showDialog();

        void setDialog();

        boolean validateOrder(int deliveryAdd, EditText timeText, EditText dateText);

        void dismissProgressDialog();

        void navigateToRegister();

        void showViewError(String view, String error);

        void showAddresses(Address mAddress);

        void showAddressesMessage(String message);

    }

    interface Presenter{

        void getCartItems(boolean view, int userId, String location, int deliveryAdd, String mobile, String schedule, String orderTime);

        double totalPriceCalculation(List<CartItem> cartItems);

        void decreaseCartItem(CartItem cartItem);

        void increaseCartItem(CartItem cartItem);

        void removeCartItem(final int id);

        void removeCartItems();

        void addOrder(Order order);

        void login(String email, String password);

        boolean validate(String email, String password);

        void getAddresses(int userId);
    }
}
