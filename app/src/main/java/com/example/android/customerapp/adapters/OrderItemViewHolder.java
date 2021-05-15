package com.example.android.customerapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;

public class OrderItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView name, price;
    public ImageView photo;
    public OnRecipeListener onRecipeListener;
    public OrderItemViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
        super(itemView);
        name = itemView.findViewById(R.id.order_item_name);
        price = itemView.findViewById(R.id.order_item_price);
        photo = itemView.findViewById(R.id.order_item_photo);

        this.onRecipeListener = onRecipeListener;
        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        onRecipeListener.onRecipeClick(getAdapterPosition());
    }
}