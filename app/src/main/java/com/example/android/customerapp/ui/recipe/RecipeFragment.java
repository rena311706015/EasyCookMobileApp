package com.example.android.customerapp.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.customerapp.R;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.viewmodels.AllRecipeViewModel;
import com.example.android.customerapp.viewmodels.RecipeViewModel;

public class RecipeFragment extends Fragment {

    private RecipeViewModel recipeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //TODO 設計食譜詳細資料頁面
        //TODO 接收bundle
        recipeViewModel =
                ViewModelProviders.of(this).get(RecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);


        return root;
    }
}