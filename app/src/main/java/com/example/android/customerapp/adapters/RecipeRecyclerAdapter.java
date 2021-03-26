package com.example.android.customerapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Recipe> recipeList;
    public OnRecipeListener mOnRecipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener mOnRecipeListener) {
        this.mOnRecipeListener = mOnRecipeListener;
    }
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
            return new RecipeViewHolder(view, mOnRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((RecipeViewHolder)viewHolder).recipeName.setText(recipeList.get(position).getName());
        ((RecipeViewHolder)viewHolder).recipeLikeCount.setText(String.valueOf(recipeList.get(position).getLikesCount()));
        ((RecipeViewHolder)viewHolder).recipeVersion.setText(recipeList.get(position).getVersion());
        ((RecipeViewHolder)viewHolder).recipeImage.setImageResource(R.drawable.sandwich);

    }


    public void setRecipes(List<Recipe> recipes){
        Log.e("GET RECIPE SIZE",String.valueOf(recipes.size()));
        recipeList = (ArrayList<Recipe>)recipes;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(recipeList != null){
            return recipeList.size();
        }
        return 0;
    }
    public Recipe getSelectedRecipe(int position){
        if(recipeList != null){
            if(recipeList.size() > 0){
                return recipeList.get(position);
            }
        }
        return null;
    }
}
