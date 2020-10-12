package com.example.bashlan;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Random;

public class RecycleAdapterRecipes extends RecyclerView.Adapter<RecycleAdapterRecipes.ViewHolder> {
    private static final String TAG = "nathan";
    private ArrayList<RecipesData> recipesData;
    private View mView;
    private RecipesItemClickListener recipesItemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView recycle_IMG_recipes;
        public TextView recycle_LBL_title_recipes;
        public TextView recycle_LBL_missig_recipes;
        public TextView recycle_LBL_time_recipes;
        public MaterialButton recycle_BTN_instruction;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recycle_IMG_recipes = itemView.findViewById(R.id.recycle_IMG_recipes);
            recycle_LBL_title_recipes = itemView.findViewById(R.id.recycle_LBL_title_recipes);
            recycle_LBL_missig_recipes = itemView.findViewById(R.id.recycle_LBL_missig_recipes);
            recycle_LBL_time_recipes = itemView.findViewById(R.id.recycle_LBL_time_recipes);
            recycle_BTN_instruction = itemView.findViewById(R.id.recycle_BTN_instruction);
            mView = itemView;
        }


    }

    public interface RecipesItemClickListener {
        void itemClicked(RecipesData item);
    }

    public void setClickListeners(RecipesItemClickListener recipesItemClickListener) {
        this.recipesItemClickListener = recipesItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_recipes, parent, false);
        return new ViewHolder(v);
    }

    public RecycleAdapterRecipes(ArrayList<RecipesData> recipesDataList) {
        this.recipesData = recipesDataList;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final RecipesData currentItem = recipesData.get(position);


        int cookTime = new Random().nextInt(30) + 30;
        holder.recycle_IMG_recipes.setImageResource(R.drawable.ic_missing_recipes);

        Glide
                .with(mView.getContext())
                .load(currentItem.getImage())
                .centerCrop()
                .into(holder.recycle_IMG_recipes);

        holder.recycle_LBL_title_recipes.setText(currentItem.getTitle());
        holder.recycle_LBL_missig_recipes.setText("Missing Ingrediant : " + currentItem.getMissedIngredientCount());
        holder.recycle_LBL_time_recipes.setText("Cook Time : " + cookTime + " min");

        holder.recycle_BTN_instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipesItemClickListener.itemClicked(currentItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipesData.size();
    }

    RecipesData getItem(int position) {
        return recipesData.get(position);
    }


}
