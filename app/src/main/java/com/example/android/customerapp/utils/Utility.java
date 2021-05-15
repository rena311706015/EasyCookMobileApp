package com.example.android.customerapp.utils;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Utility {
    //    @SuppressLint("RestrictedApi")
    @BindingAdapter("bind:bindText")
    public static void setAdapterAndData(TextView view, String value) {
        view.setText(value);
//        TextViewBindingAdapter.setText(view, value);
    }

    @BindingAdapter("bind:bindRecyclerView")
    public static void setAdapterAndData(RecyclerView recyclerView, List<String> ingredients) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
//        recyclerView.setAdapter(new IngredientAdapter(recyclerView.getContext(), ingredients));
    }
}