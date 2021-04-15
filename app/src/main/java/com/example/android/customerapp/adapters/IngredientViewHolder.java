package com.example.android.customerapp.adapters;

import androidx.recyclerview.widget.RecyclerView;
import com.example.android.customerapp.databinding.FragmentRecipeBinding;
import com.example.android.customerapp.databinding.LayoutIngredientListItemBinding;
import com.example.android.customerapp.models.Ingredient;

public class IngredientViewHolder extends RecyclerView.ViewHolder {
    private LayoutIngredientListItemBinding binding;

    public IngredientViewHolder(LayoutIngredientListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public LayoutIngredientListItemBinding getBinding() {
        return binding;
    }
}
