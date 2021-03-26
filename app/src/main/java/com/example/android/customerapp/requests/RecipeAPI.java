package com.example.android.customerapp.requests;

import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.requests.responses.RecipeResponse;
import com.example.android.customerapp.requests.responses.RecipeSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeAPI {
    @GET("recipe/all")
    Call<List<Recipe>> getAllRecipe();

    @GET("recipe/{id}")
    Call<Recipe> getRecipeById(
            @Path("id") String id
    );

    @GET("recipe/overview")
    Call<List<Recipe>> getRecipeOverview();

}

