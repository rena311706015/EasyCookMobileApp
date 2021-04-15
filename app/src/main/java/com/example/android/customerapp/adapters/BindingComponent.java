package com.example.android.customerapp.adapters;

import androidx.databinding.DataBindingComponent;

public class BindingComponent implements DataBindingComponent {

    private MyBindingAdapter bindingAdapter = new BindingAdapter();

    @Override
    public MyBindingAdapter getMyBindingAdapter() {
        return bindingAdapter;
    }
}