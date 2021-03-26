package com.example.android.customerapp.requests.responses;

import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.models.RecipeIngredient;
import com.example.android.customerapp.models.RecipeStep;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RecipeResponse {
    @SerializedName("recipe")
    private List<Recipe> recipeList;
    public List<Recipe> getRecipeList(){
        return recipeList;
    }
}
