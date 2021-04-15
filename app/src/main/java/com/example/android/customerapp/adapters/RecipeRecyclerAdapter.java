package com.example.android.customerapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MediatorLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.databinding.LayoutIngredientListItemBinding;
import com.example.android.customerapp.databinding.LayoutRecipeListItemBinding;
import com.example.android.customerapp.models.Recipe;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private Context context;
    private List<Recipe> recipeList;
    private OnRecipeListener mOnRecipeListener;

    public RecipeRecyclerAdapter(Context context,OnRecipeListener mOnRecipeListener, List<Recipe> recipes) {
        this.context=context;
        this.mOnRecipeListener = mOnRecipeListener;
        this.recipeList=recipes;
    }
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DataBindingUtil.setDefaultComponent(new BindingComponent());
        LayoutRecipeListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.layout_recipe_list_item, parent, false);
        return new RecipeViewHolder(binding, mOnRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        LayoutRecipeListItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setRecipe(recipeList.get(position));
        binding.executePendingBindings();
    }

    public void setRecipeList(List<Recipe> recipes){
        recipeList = recipes;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(recipeList != null){
            return recipeList.size();
        }
        return 0;
    }


}
