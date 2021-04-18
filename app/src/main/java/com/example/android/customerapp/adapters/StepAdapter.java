package com.example.android.customerapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.databinding.LayoutStepListItemBinding;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder> {
    private Context context;
    private List<String> steps;

    public StepAdapter(Context context, List<String> steps) {
        this.context = context;
        this.steps = steps;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutStepListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.layout_step_list_item, parent, false);
        return new StepViewHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.getBinding().setStep(steps.get(position));
        holder.getBinding().setStepNumber("第"+(position+1)+"步");
    }


    @Override
    public int getItemCount() {

        return steps == null ? 0 : steps.size();
    }
}