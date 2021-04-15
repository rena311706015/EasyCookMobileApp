package com.example.android.customerapp.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.customerapp.R;
import com.example.android.customerapp.VideoPlayerActivity;
import com.example.android.customerapp.adapters.BindingComponent;
import com.example.android.customerapp.databinding.FragmentRecipeBinding;
import com.example.android.customerapp.models.Ingredient;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.models.RecipeIngredient;
import com.example.android.customerapp.models.RecipeStep;
import com.example.android.customerapp.viewmodels.AllRecipeViewModel;
import com.example.android.customerapp.viewmodels.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {

    private RecipeViewModel mRecipeViewModel;
    private Button videoButton;
    private Toolbar mToolbar;
    private Recipe mRecipe;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        DataBindingUtil.setDefaultComponent(new BindingComponent());
        FragmentRecipeBinding binding= DataBindingUtil.inflate(inflater, R.layout.fragment_recipe,container, false);
        View root = binding.getRoot();

        videoButton= root.findViewById(R.id.video_button);
        mToolbar = root.findViewById(R.id.toolbar);


        String id=(String)getArguments().getString("id");

        mRecipe = new Recipe();

        mRecipeViewModel.getRecipeById(id);
        mRecipeViewModel.mRecipe.observe(getViewLifecycleOwner(), recipe -> {

            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24, null));
            mToolbar.inflateMenu(R.menu.search_menu);

            videoButton.setOnClickListener(v ->{
                Intent intent = new Intent();
                intent.setClass(getContext(), VideoPlayerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe", recipe);
                intent.putExtras(bundle);
                startActivity(intent);
            });

            List<String> ingredients = new ArrayList<>();
            List<String> steps = new ArrayList<>();
            for(RecipeIngredient ingredient : recipe.getRecipeIngredients()){
                String ingredientName = ingredient.getIngredient().getName();
                String ingredientQuantity = String.valueOf(ingredient.getQuantityRequired());
                String ingredientUnit = ingredient.getIngredient().getUnit();
                ingredients.add(ingredientName+"   "+ingredientQuantity+ingredientUnit);
            }
            for(RecipeStep step:recipe.getRecipeSteps()){
                steps.add(step.getNote());
            }
            binding.setRecipe(recipe);
            binding.setIngredients(ingredients);
            binding.setSteps(steps);
        });


        return root;

    }
}