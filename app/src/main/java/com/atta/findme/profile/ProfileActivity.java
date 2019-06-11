package com.atta.findme.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.atta.findme.R;
import com.atta.findme.main.MainActivity;
import com.atta.findme.model.Address;
import com.atta.findme.model.SessionManager;
import com.atta.findme.model.User;
import com.atta.findme.newaddress.NewAddressActivity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, ProfileContract.View {

    TextView addressTv, editAddress;

    ProfilePresenter profilePresenter;

    SessionManager sessionManager;

    TextInputEditText nameText, emailText, mobileText;

    Spinner locationSpinner;

    ArrayAdapter<String> locationsAdapter;

    List<String> locationsArray;

    Address address;

    String locationString, phone;

    Button saveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initiateViews();

        sessionManager = new SessionManager(this);

        profilePresenter = new ProfilePresenter(this, this);

        profilePresenter.getProfile(sessionManager.getUserId());
    }


    private void initiateViews() {


        addressTv = findViewById(R.id.address_tv);
        editAddress = findViewById(R.id.add_addresses);
        editAddress.setOnClickListener(this);



        nameText = findViewById(R.id.name_tv);
        emailText = findViewById(R.id.email_tv);

        mobileText = findViewById(R.id.mobile_tv);

        saveProfile = findViewById(R.id.save_profile);
        saveProfile.setOnClickListener(this);


        locationSpinner = findViewById(R.id.profile_location);

        String[] locations = getResources().getStringArray(R.array.locations);


        locationsArray = Arrays.asList(locations);


        locationsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, locationsArray);
        locationsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationsAdapter);
        locationSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == editAddress){

            Intent intent = new Intent(ProfileActivity.this, NewAddressActivity.class);
            if (address != null){
                intent.putExtra("address", address);
            }
            startActivity(intent);
        }else if (v == saveProfile){
            User user = new User(SessionManager.getInstance(this).getUserId(), nameText.getText().toString(), mobileText.getText().toString(), locationString);

            profilePresenter.updateProfile(user);


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0){

            if (position != 1){
                locationSpinner.setSelection(1);
                locationString = locationsArray.get(0);
                //locationString = "Maadi";
                showMessage("Available on 6 of October only, Coming Soon");
            }else {
                locationString = locationsArray.get(position);


            }
        }else {
            locationString = null;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (parent == locationSpinner){

            locationString = locationsArray.get(locationSpinner.getSelectedItemPosition());


        }
    }

    @Override
    public void showProfile(User user) {
        nameText.setText(user.getName());
        emailText.setText(user.getEmail());


        mobileText.setText(user.getPhone());


        locationSpinner.setSelection(locationsArray.indexOf(user.getLocation()));

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


    }


    @Override
    public void moveToMain() {

        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void showAddressesMessage(String message) {

        addressTv.setText(message);
    }

    @Override
    public void showAddresses(Address mAddress) {

        address = mAddress;

        addressTv.setText(mAddress.getFullAddress());
    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
