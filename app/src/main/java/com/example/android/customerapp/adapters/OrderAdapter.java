package com.example.android.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.databinding.LayoutOrderListItemBinding;
import com.example.android.customerapp.models.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    private Context context;
    private List<Order> orderList;
    private OnOrderListener mOnOrderListener;

    public OrderAdapter(Context context, OnOrderListener mOnOrderListener, List<Order> orders) {
        this.context = context;
        this.mOnOrderListener = mOnOrderListener;
        this.orderList = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DataBindingUtil.setDefaultComponent(new BindingComponent());
        LayoutOrderListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.layout_order_list_item, parent, false);
        return new OrderViewHolder(binding, mOnOrderListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        LayoutOrderListItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setOrder(orderList.get(position));
        binding.executePendingBindings();
    }


    public void setOrderList(List<Order> orders) {
        orderList = orders;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (orderList != null) {
            return orderList.size();
        }
        return 0;
    }


}
