package com.example.android.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.models.RecipeStep;

import java.util.concurrent.TimeUnit;

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder> {
    private Context context;
    private RecipeStep[] steps;

    public StepAdapter(Context context, RecipeStep[] steps) {
        this.context = context;
        this.steps = steps;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_step_list_item, parent, false);
        return new StepViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        long minute = TimeUnit.MILLISECONDS.toMinutes(steps[position].getStartTime());
        long second = TimeUnit.MILLISECONDS.toSeconds(steps[position].getStartTime()) - TimeUnit.MINUTES.toSeconds(minute);

        if (second < 10) {
            holder.stepTime.setText("0" + minute + ":0" + second);
        } else {
            holder.stepTime.setText("0" + minute + ":" + second);
        }
        holder.stepId.setText(String.valueOf(position + 1));
        holder.stepDescription.setText(steps[position].getNote());
    }


    @Override
    public int getItemCount() {

        return steps == null ? 0 : steps.length;
    }
}