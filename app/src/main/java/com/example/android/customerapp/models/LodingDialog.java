package com.example.android.customerapp.models;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.customerapp.R;
import com.wang.avi.AVLoadingIndicatorView;

public class LodingDialog extends Dialog {
    private AVLoadingIndicatorView avi;

    public LodingDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.layout_loding);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
    }

    public LodingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);

    }

    protected LodingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void show() {
        super.show();
        avi.smoothToShow();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        avi.smoothToHide();
    }
}
