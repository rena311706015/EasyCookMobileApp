package com.example.android.customerapp.models;

import android.content.Context;

import com.google.android.exoplayer2.ui.DefaultTimeBar;

public class ExtendedTimeBar extends DefaultTimeBar {
    private boolean enabled;
    private boolean forceDisabled;

    public ExtendedTimeBar(Context context) {
        super(context);
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        super.setEnabled(!this.forceDisabled && this.enabled);
    }

    public void setForceDisabled(boolean forceDisabled) {
        this.forceDisabled = forceDisabled;
        setEnabled(enabled);
    }
}
