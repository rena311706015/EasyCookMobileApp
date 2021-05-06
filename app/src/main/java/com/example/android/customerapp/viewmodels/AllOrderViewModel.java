package com.example.android.customerapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.customerapp.models.Order;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.requests.BackendAPIClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllOrderViewModel extends ViewModel {
    public MediatorLiveData<List<Order>> mOrderList;

    public AllOrderViewModel() {
        mOrderList = new MediatorLiveData<>();
    }

    public void getOrderList(String auth){
        //enqueue ->會另外開執行緒，所以可以不用再另外用runnable
        BackendAPIClient.getInstance().getAllOrder(auth).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                Log.e("ORDER","onResponse");
                mOrderList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e("ORDER",t.getMessage());
            }
        });
    }
}


