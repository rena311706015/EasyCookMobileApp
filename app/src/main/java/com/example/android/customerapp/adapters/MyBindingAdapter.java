package com.example.android.customerapp.adapters;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public abstract class MyBindingAdapter {

    @BindingAdapter("android:text")
    public abstract void setText(TextView view, String value);

    @BindingAdapter("android:src")
    public abstract void setImage(ImageView iv, Bitmap bitmap);

}
