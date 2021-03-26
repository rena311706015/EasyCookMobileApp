package com.example.android.customerapp.requests.responses;

import com.example.android.customerapp.models.Recipe;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeSearchResponse {
    @SerializedName("recipe")
    private List<Recipe> searchRecipeList;
    public List<Recipe> getSearchRecipeList(){
        return searchRecipeList;
    }
}
