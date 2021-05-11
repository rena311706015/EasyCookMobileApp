package com.example.android.customerapp.adapters;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.customerapp.R;

public class BindingAdapter extends MyBindingAdapter {

    @Override
    public void setText(TextView view, String value) {
        view.setText(value);
    }

    @Override
    public void setImage(ImageView view, Bitmap value){
        if(value!= null){
            view.setImageBitmap(value);
        }else{
            view.setImageResource(R.drawable.loding);
        }
    }
}