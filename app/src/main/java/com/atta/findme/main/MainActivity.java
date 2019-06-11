package com.atta.findme.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.atta.findme.CategoriesActivity;
import com.atta.findme.R;
import com.atta.findme.cart.CartActivity;
import com.atta.findme.login.LoginActivity;
import com.atta.findme.model.SessionManager;
import com.atta.findme.myorders.MyOrdersActivity;
import com.atta.findme.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, MainContract.View {



    private static final String TAG = "MainActivity";

    private static final int FINE_LOCATION_REQUEST_CODE = 101;
    private static final int COARSE_LOCATION_REQUEST_CODE = 102;

    CardView bakeryCardView, butcherCardView, supermarketCardView, vegetablesCardView, servicesCardView;

    int cartCount = 0;

    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        bakeryCardView = findViewById(R.id.cardView3);
        bakeryCardView.setOnClickListener(this);
        butcherCardView = findViewById(R.id.cardView4);
        butcherCardView.setOnClickListener(this);
        supermarketCardView = findViewById(R.id.cardView1);
        supermarketCardView.setOnClickListener(this);
        vegetablesCardView = findViewById(R.id.cardView2);
        vegetablesCardView.setOnClickListener(this);
        servicesCardView = findViewById(R.id.cardView5);
        servicesCardView.setOnClickListener(this);

        mainPresenter = new MainPresenter(this, this);
        mainPresenter.getCartItemsNum();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission(android.Manifest.permission.ACCESS_FINE_LOCATION,
                    FINE_LOCATION_REQUEST_CODE);
            requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION,
                    COARSE_LOCATION_REQUEST_CODE);
            return;
        }

        if (SessionManager.getInstance(getApplicationContext()).isLoggedIn()){
            renameLoginItem();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation View item clicks here.
        int id = item.getItemId();

        Intent intent;
        if (id == R.id.nav_bakery) {
            intent = new Intent(MainActivity.this, CategoriesActivity.class);
            intent.putExtra("fragment", "bakery");
            startActivity(intent);
        } else if (id == R.id.nav_butcher) {
            intent = new Intent(MainActivity.this, CategoriesActivity.class);
            intent.putExtra("fragment", "butcher");
            startActivity(intent);

        } else if (id == R.id.nav_supermarket) {
            intent = new Intent(MainActivity.this, CategoriesActivity.class);
            intent.putExtra("fragment", "super market");
            startActivity(intent);

        } else if (id == R.id.nav_vegetables) {
            intent = new Intent(MainActivity.this, CategoriesActivity.class);
            intent.putExtra("fragment", "vegetables");
            startActivity(intent);

        } else if (id == R.id.nav_services) {
            intent = new Intent(MainActivity.this, CategoriesActivity.class);
            intent.putExtra("fragment", "services");
            startActivity(intent);

        }  else if (id == R.id.nav_cart) {

            if (cartCount > 0) {
                intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }else showMessage("Cart is empty");
        }  else if (id == R.id.nav_my_order) {

            if (SessionManager.getInstance(getApplicationContext()).isLoggedIn()){

                intent = new Intent(MainActivity.this, MyOrdersActivity.class);
                startActivity(intent);

            }else showMessage("You need to login first");

        }  else if (id == R.id.login) {

            if (SessionManager.getInstance(getApplicationContext()).isLoggedIn()){

                intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);

            }else {
                renameLoginItem();
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }

        } else if (id == R.id.logout) {

            if (SessionManager.getInstance(getApplicationContext()).isLoggedIn()){

                SessionManager.getInstance(getApplicationContext()).logoutUser();

            }

            intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void renameLoginItem() {

        NavigationView navigationView =  findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.login).setIcon(R.drawable.avatar);
        nav_Menu.findItem(R.id.login).setTitle("Profile");
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;
        if (v == bakeryCardView){
            intent = new Intent(MainActivity.this, CategoriesActivity.class);
            intent.putExtra("fragment", "bakery");
        } else if (v == butcherCardView) {
            intent = new Intent(MainActivity.this, CategoriesActivity.class);
            intent.putExtra("fragment", "butcher");

        } else if (v == supermarketCardView) {
            intent = new Intent(MainActivity.this, CategoriesActivity.class);
            intent.putExtra("fragment", "super market");

        } else if (v == vegetablesCardView) {
            intent = new Intent(MainActivity.this, CategoriesActivity.class);
            intent.putExtra("fragment", "vegetables");

        } else if (v == servicesCardView) {
            intent = new Intent(MainActivity.this, CategoriesActivity.class);
            intent.putExtra("fragment", "services");

        }


        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void setCartCount(int numOfCartItem) {

        cartCount = numOfCartItem;
    }



    protected void requestPermission(String permissionType, int requestCode) {
        int permission = ContextCompat.checkSelfPermission(this,
                permissionType);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permissionType}, requestCode
            );
        }
    }
}
