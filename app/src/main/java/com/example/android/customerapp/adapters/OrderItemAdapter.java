package com.example.android.customerapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.models.Ingredient;
import com.example.android.customerapp.models.OrderItem;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.models.RecipeIngredient;

import java.util.List;

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
        return new OrderItemViewHolder(view,onRecipeListener);


    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        Log.e("ORDER","onBindViewHolder");
        OrderItem orderItem = orderItems[position];
        holder.name.setText(orderItem.getRecipe().getName());
        holder.price.setText(orderItem.getItemPrice());
        if(orderItem.getBitmap()!=null){
            holder.photo.setImageBitmap(orderItem.getBitmap());
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
