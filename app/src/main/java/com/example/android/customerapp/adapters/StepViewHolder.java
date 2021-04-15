package com.example.android.customerapp.adapters;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.databinding.FragmentRecipeBinding;
import com.example.android.customerapp.databinding.LayoutStepListItemBinding;

public class StepViewHolder extends RecyclerView.ViewHolder {
    private LayoutStepListItemBinding binding;

    public StepViewHolder(LayoutStepListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public LayoutStepListItemBinding getBinding() {
        return binding;
    }
}
