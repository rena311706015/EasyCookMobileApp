package com.example.android.customerapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.databinding.LayoutOrderItemListItemBinding;
import com.example.android.customerapp.models.OrderItem;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemViewHolder> {
    private Context context;
    private List<OrderItem> items;

    public OrderItemAdapter(Context context, List<OrderItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("OrderItemViewHolder","onCreate");


        LayoutOrderItemListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.layout_order_item_list_item, parent, false);
        return new OrderItemViewHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        Log.e("onBind",items.get(position).getRecipe().getName());
        holder.getBinding().setOrderItem(items.get(position));
    }

    public void setItems(List<OrderItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return items == null ? 0 : items.size();
    }
}