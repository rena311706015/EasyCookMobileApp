package com.example.android.customerapp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;


public class StepViewHolder extends RecyclerView.ViewHolder {
    public TextView stepId, stepTime, stepDescription;

    public StepViewHolder(@NonNull View itemView) {
        super(itemView);
        stepId = itemView.findViewById(R.id.step_id);
        stepTime = itemView.findViewById(R.id.step_time);
        stepDescription = itemView.findViewById(R.id.step_description);
    }
}
