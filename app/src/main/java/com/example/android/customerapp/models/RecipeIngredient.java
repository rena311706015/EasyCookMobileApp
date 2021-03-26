package com.example.android.customerapp.models;

import java.io.Serializable;

public class RecipeIngredient implements Serializable {
    Ingredient ingredient;
    int id,requiredQuantity;
    public RecipeIngredient(int id, Ingredient ingredient, int requiredQuantity){
        this.id=id;
        this.ingredient=ingredient;
        this.requiredQuantity=requiredQuantity;
    }
}
