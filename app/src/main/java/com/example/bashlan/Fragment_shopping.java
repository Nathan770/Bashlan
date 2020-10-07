package com.example.bashlan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class Fragment_shopping extends Fragment {

    protected View view;
    private RecyclerView shopping_RCV_product;
    private RecyclerView.Adapter shopping_RCV_adapter;
    private RecyclerView.LayoutManager shopping_RCV_manager;
    private ArrayList<ProductData> mShoppinglist;


    public static Fragment_shopping newInstance(ArrayList shoppingList) {
        Fragment_shopping fragment = new Fragment_shopping();
        fragment.mShoppinglist = shoppingList;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_shopping, container, false);
        }
        findViews(view);

        return view;
    }

    private void findViews(View view) {

        shopping_RCV_product = view.findViewById(R.id.shopping_RCV_product);
        shopping_RCV_product.setHasFixedSize(true);
        shopping_RCV_manager = new LinearLayoutManager(view.getContext());
        shopping_RCV_adapter = new RecycleAdapterProduct(mShoppinglist);
        shopping_RCV_product.setLayoutManager(shopping_RCV_manager);
        shopping_RCV_product.setAdapter(shopping_RCV_adapter);

    }
}
