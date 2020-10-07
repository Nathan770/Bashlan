package com.example.bashlan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "nathan";

    private BottomNavigationView main_BNV_menu;
    private MaterialToolbar main_TLB_title;

    private ArrayList<ProductData> listProduct;
    private ArrayList<ProductData> listShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initList();
        initFragmentsFridge();

        main_BNV_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_fridge:
                        main_TLB_title.setTitle("Fridge");
                        main_TLB_title.setNavigationIcon(R.drawable.ic_fridge);
                        initFragmentsFridge();
                        break;
                    case R.id.page_shopping:
                        main_TLB_title.setTitle("Shopping");
                        main_TLB_title.setNavigationIcon(R.drawable.ic_shopping_cart);
                        initFragmentsShopping();
                        break;
                    case R.id.page_recipe:
                        main_TLB_title.setTitle("Recipes");
                        main_TLB_title.setNavigationIcon(R.drawable.ic_recipe);
                        initFragmentsRecipe();
                        break;
                    case R.id.page_profile:
                        main_TLB_title.setTitle("Profile");
                        main_TLB_title.setNavigationIcon(R.drawable.ic_profile);
                        initFragmentsProfile();
                        break;
                }
                return true;
            }
        });


    }

    private void initList() {
        Log.d(TAG, "initList: ");
        listProduct = new ArrayList<>();
        listShopping = new ArrayList<>();

        listProduct.add(new ProductData("tomato", R.drawable.ic_tomato, false));
        listProduct.add(new ProductData("cucumber", R.drawable.ic_cucumber, false));
        listProduct.add(new ProductData("onion", R.drawable.ic_onion, false));
        listProduct.add(new ProductData("apples", R.drawable.ic_apple, false));
        listProduct.add(new ProductData("banana", R.drawable.ic_banana, false));
        listProduct.add(new ProductData("pear", R.drawable.ic_pear, false));
        listProduct.add(new ProductData("egg", R.drawable.ic_egg, false));
        listProduct.add(new ProductData("milk", R.drawable.ic_milk, false));
        listProduct.add(new ProductData("flour", R.drawable.ic_flour, false));
        listProduct.add(new ProductData("sugar", R.drawable.ic_sugar, false));

        listShopping.add(new ProductData("tomato", R.drawable.ic_tomato, false));
        listShopping.add(new ProductData("cucumber", R.drawable.ic_cucumber, false));
        listShopping.add(new ProductData("onion", R.drawable.ic_onion, false));
        listShopping.add(new ProductData("apples", R.drawable.ic_apple, false));
        listShopping.add(new ProductData("banana", R.drawable.ic_banana, false));
        listShopping.add(new ProductData("pear", R.drawable.ic_pear, false));
        listShopping.add(new ProductData("egg", R.drawable.ic_egg, false));
        listShopping.add(new ProductData("milk", R.drawable.ic_milk, false));
        listShopping.add(new ProductData("flour", R.drawable.ic_flour, false));
        listShopping.add(new ProductData("sugar", R.drawable.ic_sugar, false));

    }

    private void initFragmentsFridge() {
        Fragment_fridge fragment_fridge = Fragment_fridge.newInstance(listProduct);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_LAY_app, fragment_fridge);
        transaction.commit();

    }

    private void initFragmentsShopping() {
        Fragment_shopping fragment_shopping = Fragment_shopping.newInstance(listShopping);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_LAY_app, fragment_shopping);
        transaction.commit();
    }

    private void initFragmentsRecipe() {
        Fragment_recipes fragment_recipe = Fragment_recipes.newInstance(listProduct);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_LAY_app, fragment_recipe);
        transaction.commit();
    }

    private void initFragmentsProfile() {
        String email = getIntent().getStringExtra("email");
        String name = getIntent().getStringExtra("name");
        String urlPicture = getIntent().getStringExtra("urlPicture");
        Fragment_profile fragment_profile = Fragment_profile.newInstance(email, name, urlPicture);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_LAY_app, fragment_profile);
        transaction.commit();
    }

    private void findViews() {
        main_BNV_menu = findViewById(R.id.main_BNV_menu);
        main_TLB_title = findViewById(R.id.main_TLB_title);
    }
}