package com.example.android.customerapp.adapters;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.databinding.LayoutRecipeListItemBinding;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private LayoutRecipeListItemBinding binding;
    private OnRecipeListener onRecipeListener;

    public RecipeViewHolder(LayoutRecipeListItemBinding binding, OnRecipeListener onRecipeListener) {

        super(binding.getRoot());
        this.binding = binding;
        this.onRecipeListener = onRecipeListener;
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        onRecipeListener.onRecipeClick(getAdapterPosition());
    }

    public LayoutRecipeListItemBinding getBinding() {
        return binding;
    }

}