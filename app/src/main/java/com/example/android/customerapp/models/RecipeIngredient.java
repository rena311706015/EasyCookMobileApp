package com.example.android.customerapp.models;

public class RecipeIngredient {
    Ingredient ingredient;
    int id,requiredQuantity;
    RecipeIngredient(int id, int requiredQuantity){
        this.id=id;
//        this.ingredient=ingredient;
        this.requiredQuantity=requiredQuantity;
    }
}
