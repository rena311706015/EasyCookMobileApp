package com.example.android.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.models.Ingredient;
import com.example.android.customerapp.models.RecipeIngredient;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder> {
    private Context context;
    private RecipeIngredient[] ingredients;

    public IngredientAdapter(Context context, RecipeIngredient[] ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ingredient_list_item, parent, false);
        return new IngredientViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients[position].getIngredient();
        holder.ingredient.setText(ingredient.getName() + "   " + (int)ingredients[position].getQuantityRequired() + ingredient.getUnit());
    }


    @Override
    public int getItemCount() {

        return ingredients == null ? 0 : ingredients.length;
    }
}
