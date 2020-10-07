package com.example.bashlan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Fragment_fridge extends Fragment {
    private static final String TAG = "nathan";
    protected View view;
    private RecyclerView fridge_RCV_product;
    private RecyclerView.Adapter fridge_RCV_adapter;
    private RecyclerView.LayoutManager fridge_RCV_manager;
    private ArrayList<ProductData> listProduct;

    public static Fragment_fridge newInstance(ArrayList<ProductData> list) {
        Fragment_fridge fragment = new Fragment_fridge();
        fragment.listProduct = list;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_fridge, container, false);
        }
        findViews(view);
        return view;
    }

    private void findViews(View view) {

        fridge_RCV_product = view.findViewById(R.id.fridge_RCV_product);
        fridge_RCV_product.setHasFixedSize(true);
        fridge_RCV_manager = new LinearLayoutManager(view.getContext());
        fridge_RCV_adapter = new RecycleAdapterProduct(listProduct);
        fridge_RCV_product.setLayoutManager(fridge_RCV_manager);
        fridge_RCV_product.setAdapter(fridge_RCV_adapter);

    }

}