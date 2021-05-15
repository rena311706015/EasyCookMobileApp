package com.example.android.customerapp.models;

import java.io.Serializable;

public class RecipeIngredient implements Serializable {
    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(double quantityRequired) {
        this.quantityRequired = quantityRequired;
    }

    Ingredient ingredient;
    double quantityRequired;
    int id;

    public RecipeIngredient(int id, Ingredient ingredient, double quantityRequired) {
        this.id = id;
        this.ingredient = ingredient;
        this.quantityRequired = quantityRequired;
    }
}
