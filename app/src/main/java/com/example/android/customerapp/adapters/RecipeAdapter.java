package com.example.android.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.models.Recipe;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private Context context;
    public RecipeViewHolder holder;
    public TextView recipeName;
    public ImageView recipeImage;
    public ArrayList<Recipe> recipeList;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        this.context = context;
        this.recipeList=recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.layout_recipe_list_item, parent, false);
            return new RecipeViewHolder(view);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.grid_recipe_name);
            recipeImage = itemView.findViewById(R.id.grid_recipe_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.action_navigation_all_recipe_to_navigation_recipe);
                }
            });



        }

    }
    @Override
    public void onBindViewHolder(final RecipeViewHolder holder,final int position) {
        recipeName.setText(recipeList.get(position).name);
        recipeImage.setImageResource(R.drawable.cake);
    }
    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}
