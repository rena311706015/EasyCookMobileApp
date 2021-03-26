package com.example.android.customerapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.repositories.RecipeRepository;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView recipeName,recipeLikeCount,recipeVersion;
    public ImageView recipeImage;
    OnRecipeListener onRecipeListener;
    public RecipeViewHolder(View itemView, OnRecipeListener onRecipeListener) {
        super(itemView);

        this.onRecipeListener = onRecipeListener;
        recipeName = itemView.findViewById(R.id.grid_recipe_name);
        recipeImage = itemView.findViewById(R.id.grid_recipe_image);
        recipeLikeCount = itemView.findViewById(R.id.grid_recipe_likesCount);
        recipeVersion = itemView.findViewById(R.id.grid_recipe_version);
        itemView.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        onRecipeListener.onRecipeClick(getAdapterPosition());
    }

}