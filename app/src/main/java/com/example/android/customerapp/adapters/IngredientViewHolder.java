package com.example.android.customerapp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;

public class IngredientViewHolder extends RecyclerView.ViewHolder {
    public TextView ingredient;

    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        ingredient = itemView.findViewById(R.id.ingredient);
    }
}
