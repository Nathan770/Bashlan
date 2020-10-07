package com.example.bashlan;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleAdapterProduct extends RecyclerView.Adapter<RecycleAdapterProduct.ViewHolder> {
    private ArrayList<ProductData> productData;
    private static final String TAG = "nathan";

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView recycle_LBL_product;
        public ImageView recycle_IMG_product;
        public CheckBox recycle_CBO_product;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recycle_LBL_product = itemView.findViewById(R.id.recycle_LBL_product);
            recycle_IMG_product = itemView.findViewById(R.id.recycle_IMG_product);
            recycle_CBO_product = itemView.findViewById(R.id.recycle_CBO_product);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_product, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public RecycleAdapterProduct(ArrayList<ProductData> productDataList) {
        this.productData = productDataList;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ProductData currentItem = productData.get(position);
        holder.recycle_LBL_product.setText(currentItem.getName());
        holder.recycle_IMG_product.setImageResource(currentItem.getImagePosition());
        holder.recycle_CBO_product.setChecked(currentItem.isHaving());

        holder.recycle_CBO_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {
                    Log.d(TAG, "true " + currentItem.getName());
                    currentItem.setHaving(true);
                    holder.recycle_CBO_product.setChecked(currentItem.isHaving());
                } else {
                    Log.d(TAG, "false ");
                    currentItem.setHaving(false);
                    holder.recycle_CBO_product.setChecked(currentItem.isHaving());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productData.size();
    }


}
