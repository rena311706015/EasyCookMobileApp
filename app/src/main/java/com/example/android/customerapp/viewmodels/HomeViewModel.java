package com.example.android.customerapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.repositories.RecipeRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;

    public HomeViewModel() {
        mRecipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeRepository.getAllRecipe();
    }

}