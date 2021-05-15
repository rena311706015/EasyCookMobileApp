package com.example.android.customerapp.models;

import android.graphics.Bitmap;

import java.io.Serializable;

public class OrderItem implements Serializable {
    int id, itemPrice;
    Recipe recipe;
    String recipeImage;
    Bitmap bitmap;
    boolean isCustomize;


    public OrderItem(int id, int itemPrice, Recipe recipe, String recipeImage, boolean isCustomize) {
        this.id = id;
        this.itemPrice = itemPrice;
        this.recipe = recipe;
        this.recipeImage = recipeImage;
        this.isCustomize = isCustomize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemPrice() {
        return "NT$"+itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isCustomize() {
        return isCustomize;
    }

    public void setCustomize(boolean customize) {
        isCustomize = customize;
    }


}
