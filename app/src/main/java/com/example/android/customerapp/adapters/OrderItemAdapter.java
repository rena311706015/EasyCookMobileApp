package com.example.android.customerapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.models.OrderItem;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemViewHolder> {
    private Context context;
    private OrderItem[] orderItems;
    private OnRecipeListener onRecipeListener;

    public OrderItemAdapter(Context context, OnRecipeListener onRecipeListener, OrderItem[] orderItems) {
        this.context = context;
        this.onRecipeListener = onRecipeListener;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_item_list_item, parent, false);
        return new OrderItemViewHolder(view, onRecipeListener);


    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        OrderItem orderItem = orderItems[position];
        holder.name.setText(orderItem.getRecipe().getName());
        holder.price.setText(orderItem.getItemPrice());
        holder.version.setText(orderItem.getRecipe().getVersion());
        switch (orderItem.getRecipe().getVersion()) {
            case "正常版本":
                holder.version.setTextColor(Color.parseColor("#99876F"));
                break;
            case "低脂版本":
                holder.version.setTextColor(Color.parseColor("#8093B5"));
                break;
            case "素食版本":
                holder.version.setTextColor(Color.parseColor("#7CA390"));
                break;
            case "肉多版本":
                holder.version.setTextColor(Color.parseColor("#F09797"));
                break;
        }
        if (orderItem.getBitmap() != null) {
            holder.photo.setImageBitmap(orderItem.getBitmap());
        }
        if (orderItem.isCustomize() == true) {
            holder.isCustomize.setVisibility(View.VISIBLE);
        } else {
            holder.isCustomize.setVisibility(View.GONE);
        }

    }

    public void setOrderItems(OrderItem[] orderItems) {
        this.orderItems = orderItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return orderItems == null ? 0 : orderItems.length;
    }
}
