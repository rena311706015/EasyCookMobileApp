package com.example.android.customerapp.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.MainActivity;
import com.example.android.customerapp.R;
import com.example.android.customerapp.VideoPlayerActivity;
import com.example.android.customerapp.adapters.BindingComponent;
import com.example.android.customerapp.adapters.IngredientAdapter;
import com.example.android.customerapp.adapters.StepAdapter;
import com.example.android.customerapp.databinding.FragmentRecipeBinding;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.models.RecipeIngredient;
import com.example.android.customerapp.models.RecipeStep;
import com.example.android.customerapp.viewmodels.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {

    private RecipeViewModel mRecipeViewModel;
    private RecyclerView mIngredientRecyclerView;
    private RecyclerView mStepRecyclerView;

    private IngredientAdapter mIngredientAdapter;
    private StepAdapter mStepAdapter;
    private Button videoButton;
    private Toolbar mToolbar;
    private Recipe mRecipe;

    public RecipeFragment(){}
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        DataBindingUtil.setDefaultComponent(new BindingComponent());
        FragmentRecipeBinding binding= DataBindingUtil.inflate(inflater, R.layout.fragment_recipe,container, false);
        View root = binding.getRoot();

        videoButton= root.findViewById(R.id.video_button);
        mToolbar = root.findViewById(R.id.toolbar);
        mIngredientRecyclerView = root.findViewById(R.id.recipe_ingredients_list);
        mStepRecyclerView=root.findViewById(R.id.recipe_steps_list);

        mRecipe = (Recipe)getArguments().getSerializable("recipe");

        mRecipeViewModel.getRecipeById(String.valueOf(mRecipe.getId()));
        mRecipeViewModel.mRecipe.observe(getViewLifecycleOwner(), recipe -> {

            recipe.setPhotoBitmap(mRecipe.getPhotoBitmap());

            videoButton.setOnClickListener(v ->{
                Intent intent = new Intent();
                intent.setClass(getContext(), VideoPlayerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe", recipe);
                startActivity(intent,bundle);
            });

            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24, null));
            mToolbar.inflateMenu(R.menu.search_menu);

            List<String> ingredients = new ArrayList<>();
            List<String> steps = new ArrayList<>();
            for(RecipeIngredient ingredient : recipe.getRecipeIngredients()){
                String ingredientName = ingredient.getIngredient().getName();
                String ingredientQuantity = String.valueOf((int)ingredient.getQuantityRequired());
                String ingredientUnit = ingredient.getIngredient().getUnit();
                ingredients.add(ingredientName+"   "+ingredientQuantity+ingredientUnit);
            }
            for(RecipeStep step:recipe.getRecipeSteps()){
                steps.add(step.getNote());
            }

            mIngredientAdapter = new IngredientAdapter(getContext(),ingredients);
            mIngredientRecyclerView.setAdapter(mIngredientAdapter);
            mIngredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            mStepAdapter = new StepAdapter(getContext(),steps);
            mStepRecyclerView.setAdapter(mStepAdapter);
            mStepRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            binding.setRecipe(recipe);
        });

        return root;
    }
}