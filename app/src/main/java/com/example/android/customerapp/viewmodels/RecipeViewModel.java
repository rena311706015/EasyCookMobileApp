package com.example.android.customerapp.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.android.customerapp.models.RecipeIngredient;
import com.example.android.customerapp.models.RecipeStep;

import java.util.ArrayList;

public class RecipeViewModel extends ViewModel {

//    private MutableLiveData<String> mText;
    private int recipeId,recipeLikeCount;
    private boolean recipeImage;
    private String recipeName,recipeLink,recipeDescription;
    private ArrayList<RecipeStep> recipeSteps;
    private ArrayList<RecipeIngredient> recipeIngredients;
    public RecipeViewModel(int id, String name, String link, int likeCount, String description, ArrayList<RecipeStep> steps, ArrayList<RecipeIngredient> ingredients){
        this.recipeId=id;
        this.recipeName=name;
        this.recipeLink=link;
        this.recipeLikeCount=likeCount;
        this.recipeDescription=description;
        this.recipeSteps=steps;
        this.recipeIngredients=ingredients;
    }
    public String getRecipeName() {
        return recipeName;
    }
    public String getRecipeDescription() {
        return recipeDescription;
    }
    public String getRecipeLink() {
        return recipeLink;
    }
    public boolean getRecipeImage() {
        return recipeImage;
    }
    public int getRecipeLikeCount() {
        return recipeLikeCount;
    }
    public ArrayList<RecipeStep> getRecipeSteps() {
        return recipeSteps;
    }
    public ArrayList<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }
}