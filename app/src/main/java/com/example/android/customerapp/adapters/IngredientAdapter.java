package com.example.android.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.databinding.LayoutIngredientListItemBinding;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder> {
    private Context context;
    private List<String> ingredients;

    public IngredientAdapter(Context context, List<String> ingredients){
        this.context = context;
        this.ingredients = ingredients;
    }
    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutIngredientListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.layout_ingredient_list_item, parent, false);
        return new IngredientViewHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.getBinding().setIngredient(ingredients.get(position));
    }


    @Override
    public int getItemCount() {

        return ingredients == null ? 0 : ingredients.size();
    }
}
