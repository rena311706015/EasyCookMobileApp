package com.example.android.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.databinding.LayoutRecipeListItemBinding;
import com.example.android.customerapp.models.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private Context context;
    private List<Recipe> recipeList;
    private OnRecipeListener mOnRecipeListener;

    public RecipeAdapter(Context context, OnRecipeListener mOnRecipeListener, List<Recipe> recipes) {
        this.context = context;
        this.mOnRecipeListener = mOnRecipeListener;
        this.recipeList = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DataBindingUtil.setDefaultComponent(new BindingComponent());
        LayoutRecipeListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.layout_recipe_list_item, parent, false);
        return new RecipeViewHolder(binding, mOnRecipeListener);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        LayoutRecipeListItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setRecipe(recipeList.get(position));
        switch(binding.getRecipe().getVersion()){
            case "正常版本":
                binding.gridRecipeVersion.setTextColor(Color.parseColor("#99876F"));
                break;
            case "低脂版本":
                binding.gridRecipeVersion.setTextColor(Color.parseColor("#8093B5"));
                break;
            case "素食版本":
                binding.gridRecipeVersion.setTextColor(Color.parseColor("#7CA390"));
                break;
            case "肉多版本":
                binding.gridRecipeVersion.setTextColor(Color.parseColor("#F09797"));
                break;
        }
        binding.executePendingBindings();
    }

    public void setRecipeList(List<Recipe> recipes) {
        recipeList = recipes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (recipeList != null) {
            return recipeList.size();
        }
        return 0;
    }


}
