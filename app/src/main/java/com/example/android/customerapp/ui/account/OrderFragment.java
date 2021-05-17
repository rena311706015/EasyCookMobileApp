package com.example.android.customerapp.ui.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.adapters.BindingComponent;
import com.example.android.customerapp.adapters.OnRecipeListener;
import com.example.android.customerapp.adapters.OrderItemAdapter;
import com.example.android.customerapp.databinding.FragmentOrderBinding;
import com.example.android.customerapp.models.Order;
import com.example.android.customerapp.models.OrderItem;
import com.example.android.customerapp.viewmodels.AllOrderViewModel;
import com.example.android.customerapp.viewmodels.AllRecipeViewModel;
import com.example.android.customerapp.viewmodels.OrderViewModel;

public class OrderFragment extends Fragment implements OnRecipeListener {
    private Order mOrder;
    private RecyclerView mRecyclerView;
    private OrderItemAdapter mAdapter;
    private OrderViewModel mOrderViewModel;
    private OrderItem[] mOrderItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mOrderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        DataBindingUtil.setDefaultComponent(new BindingComponent());
        FragmentOrderBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);
        View root = binding.getRoot();

        mRecyclerView = root.findViewById(R.id.order_item_list);
        mOrder = (Order) getArguments().getSerializable("order");
        mOrderItems = mOrder.getOrderItems();
        mAdapter = new OrderItemAdapter(getContext(), this, mOrderItems);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mOrderViewModel.getPhoto(mOrderItems);
        mOrderViewModel.mOrderItemList.observe(getViewLifecycleOwner(), orderItemList -> {
            orderItemList.toArray(mOrderItems);
            mAdapter.setOrderItems(mOrderItems);
        });

        binding.setOrder(mOrder);

        return root;
    }
    @Override
    public void onRecipeClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", mOrder.getOrderItems()[position].getRecipe());
        Navigation.findNavController(getView()).navigate(R.id.action_navigation_order_to_navigation_recipe, bundle);
    }
}