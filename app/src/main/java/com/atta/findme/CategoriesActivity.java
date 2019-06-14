package com.atta.findme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.atta.findme.fragments.CategoryFragment;

public class CategoriesActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    public static final String ACTION_UPDATE_FRAGMENT = "action_category_updated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this);


        String fragmentName = getIntent().getStringExtra("fragment");


        if (fragmentName == null){
            navigation.setSelectedItemId(R.id.navigation_supermarket);
        }else {
            switch (fragmentName) {
                case "bakery":
                case "Bakery":
                    navigation.setSelectedItemId(R.id.navigation_bakery);
                    break;

                case "butcher":
                case "Butcher":
                    navigation.setSelectedItemId(R.id.navigation_butcher);
                    break;

                case "super market":
                case "Super Market":
                case "Super market":
                case "super Market":

                    navigation.setSelectedItemId(R.id.navigation_supermarket);
                    break;

                case "vegetables":
                case "Vegetables":
                    navigation.setSelectedItemId(R.id.navigation_vegetables);
                    break;

                case "services":
                case "Services":
                    navigation.setSelectedItemId(R.id.navigation_services);
                    break;

                default:
                    navigation.setSelectedItemId(R.id.navigation_supermarket);
                    break;

            }
        }

    }



    private boolean loadFragment(Fragment fragment) {
        //switching frag loadFragment(fragment);ment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();


            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        switch (menuItem.getItemId()) {
            case R.id.navigation_bakery:
                bundle.putString("category", "bakery");
                break;

            case R.id.navigation_butcher:
                bundle.putString("category", "butcher");
                break;

            case R.id.navigation_supermarket:
                bundle.putString("category", "super market");
                break;

            case R.id.navigation_vegetables:
                bundle.putString("category", "vegetables");
                break;

            case R.id.navigation_services:
                bundle.putString("category", "services");
                break;

        }

        fragment.setArguments(bundle);
        return loadFragment(fragment);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
