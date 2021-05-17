package com.example.android.customerapp.ui.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.adapters.OnOrderListener;
import com.example.android.customerapp.adapters.OrderAdapter;
import com.example.android.customerapp.models.LodingDialog;
import com.example.android.customerapp.models.Order;
import com.example.android.customerapp.viewmodels.AllOrderViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllOrderFragment extends Fragment implements OnOrderListener {
    private OrderAdapter mAdapter;
    private AllOrderViewModel mAllOrderViewModel;
    private RecyclerView mRecyclerView;
    private ImageView emptyImage;
    private List<Order> mOrderList;
    private String auth = "";
    private LodingDialog lodingDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mAllOrderViewModel =
                ViewModelProviders.of(this).get(AllOrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_all_order, container, false);
        mRecyclerView = root.findViewById(R.id.recyclerview_orders);
        emptyImage = root.findViewById(R.id.empty);
        mOrderList = new ArrayList<>();
        lodingDialog = new LodingDialog(getContext());
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
        auth = sharedPreferences.getString("Token", null);
        mAllOrderViewModel.getOrderList(auth);
        mAllOrderViewModel.mOrderList.observe(getViewLifecycleOwner(), orderList -> {
            lodingDialog.dismiss();
            mOrderList = orderList;
            mAdapter.setOrderList(orderList);
            if(mAdapter.getItemCount()<=0){
                emptyImage.setVisibility(View.VISIBLE);
            }else{
                emptyImage.setVisibility(View.GONE);
            }
        });
        lodingDialog.show();
        initRecyclerView();
        return root;
    }

    private void initRecyclerView() {
        mAdapter = new OrderAdapter(getContext(), (OnOrderListener) this, mOrderList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onOrderClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", mOrderList.get(position));
//        ((MainActivity)getActivity()).onOrderClick(bundle);
        Log.e("Click", " " + position);
        Navigation.findNavController(getView()).navigate(R.id.action_navigation_all_order_to_navigation_order, bundle);
    }

}