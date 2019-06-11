package com.atta.findme.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.atta.findme.main.MainActivity;
import com.atta.findme.R;
import com.atta.findme.model.User;

import java.util.Arrays;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, RegisterContract.View {



    ProgressDialog progressDialog;
    // login button
    Button register;

    Spinner locationSpinner;

    List<String> locationsArray;

    ArrayAdapter<String> locationsAdapter;
    // National ID, password edit text
    EditText emailText, passwordText, nameText, phoneText, birthdayText, confirmPasswordText;

    String locationString;


    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setDialog();
        registerPresenter = new RegisterPresenter(this, progressDialog, this);

        initiateViews();
    }

    private void initiateViews() {

        // National ID, Password input text
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        nameText = findViewById(R.id.name);
        phoneText = findViewById(R.id.phone);
        confirmPasswordText = findViewById(R.id.password_confirm);
        locationSpinner = findViewById(R.id.location);

        // Register button
        register = findViewById(R.id.btnRegister);
        register.setOnClickListener(this);


        String[] locations = getResources().getStringArray(R.array.locations);


        locationsArray = Arrays.asList(locations);


        locationsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, locationsArray);
        locationsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationsAdapter);
        locationSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.skip) {

            navigateToMain();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view == register) {
            if (!validate(nameText.getText().toString(), emailText.getText().toString(), passwordText.getText().toString(),
                    confirmPasswordText.getText().toString(), phoneText.getText().toString(), locationString)) {
                register.setEnabled(true);
                return;
            }


            progressDialog.show();

            //Defining the user object as we need to pass it with the call
            User user = new User(nameText.getText().toString(), emailText.getText().toString(), passwordText.getText().toString(),
                    phoneText.getText().toString(), locationString);


            registerPresenter.register(user);

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

    }

    @Override
    public void showMessage(String error) {

        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showViewError(String view, String error) {

        int id = getResources().getIdentifier(view, "id", this.getPackageName());
        EditText editText = (EditText)findViewById(id);
        editText.setError(error);
    }

    @Override
    public void navigateToMain() {

        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void setDialog() {

        if(progressDialog != null){
            progressDialog.dismiss();
        }
        progressDialog = new ProgressDialog(RegisterActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating your profile...");
    }

    @Override
    public boolean validate(String name, String email, String password, String passwordConfirm, String phone, String locationSting) {
        boolean valid = true;

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (name.isEmpty()) {

            showViewError("name","Enter your name");
            valid = false;
        } else {

            showViewError("name",null);
        }

        if (!email.matches(emailPattern) || email.isEmpty())
        {
            showViewError("email","Invalid email address");
            valid = false;

        }else {
            showViewError("email",null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            showViewError("password","password must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else if (passwordConfirm.isEmpty() || passwordConfirm.length() < 4 || passwordConfirm.length() > 10 ) {

            showViewError("password_confirm","password must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else if (!password.equals(passwordConfirm)){

            showViewError("password","passwords not Matched");
            valid = false;
        }else {
            showViewError("password",null);
            showViewError("password_confirm",null);
        }

        if (phone.isEmpty() || phone.length()!= 11) {
            showViewError("phone","Enter a valid Phone number");
            valid = false;
        } else {
            showViewError("phone",null);
        }

        if(locationSting == null){

            showMessage("Enter Your Location");
            valid = false;
        }else if (locationSting.isEmpty() || locationSting.equals("null")){

            showMessage("Enter Your Location");
            valid = false;
        }






        return valid;
    }
}
