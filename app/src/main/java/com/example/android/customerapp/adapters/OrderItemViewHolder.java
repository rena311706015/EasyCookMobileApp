package com.example.android.customerapp.adapters;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.databinding.LayoutOrderItemListItemBinding;

public class OrderItemViewHolder extends RecyclerView.ViewHolder {
    private LayoutOrderItemListItemBinding binding;

    public OrderItemViewHolder(LayoutOrderItemListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public LayoutOrderItemListItemBinding getBinding() {
        return binding;
    }
}

