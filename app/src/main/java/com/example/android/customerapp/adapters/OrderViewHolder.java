package com.example.android.customerapp.adapters;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.databinding.LayoutOrderListItemBinding;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private LayoutOrderListItemBinding binding;
    private OnOrderListener onOrderListener;
    public OrderViewHolder(LayoutOrderListItemBinding binding, OnOrderListener onOrderListener) {

        super(binding.getRoot());
        this.binding = binding;
        this.onOrderListener = onOrderListener;
        itemView.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        onOrderListener.onOrderClick(getAdapterPosition());
    }

    public LayoutOrderListItemBinding getBinding() {
        return binding;
    }

}
