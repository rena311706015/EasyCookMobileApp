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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.adapters.BindingComponent;
import com.example.android.customerapp.adapters.IngredientAdapter;
import com.example.android.customerapp.adapters.OrderItemAdapter;
import com.example.android.customerapp.databinding.FragmentOrderBinding;
import com.example.android.customerapp.models.Order;
import com.example.android.customerapp.models.OrderItem;
import com.example.android.customerapp.viewmodels.AllOrderViewModel;
import com.example.android.customerapp.viewmodels.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    private Order mOrder;
    private RecyclerView mRecyclerView;
    private OrderItemAdapter mAdapter;
    private AllOrderViewModel mViewModel;
//    private IngredientAdapter mAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(this).get(AllOrderViewModel.class);
        DataBindingUtil.setDefaultComponent(new BindingComponent());
        FragmentOrderBinding binding= DataBindingUtil.inflate(inflater, R.layout.fragment_order,container, false);
        View root = binding.getRoot();

        mRecyclerView = root.findViewById(R.id.order_item_list);

        mOrder = (Order)getArguments().getSerializable("order");
//        List<String> list = new ArrayList<>();
//        for(int i = 0 ; i <= 10 ; i++){
//            list.add(String.valueOf(i));
//        }
//        mAdapter = new IngredientAdapter(getContext(),list);
        mAdapter = new OrderItemAdapter(getContext(),mOrder.getOrderItems());
        Log.e("ItemCount",String.valueOf(mAdapter.getItemCount()));
        //TODO RecyclerView載入錯誤
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.setOrder(mOrder);
        return root;
    }
}