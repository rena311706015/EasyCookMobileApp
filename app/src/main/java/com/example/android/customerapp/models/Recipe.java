package com.example.android.customerapp.models;

import android.os.Parcelable;

import org.checkerframework.checker.nullness.compatqual.NullableType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable{
    private boolean[] photos;
    private int id,likesCount,price;
    private String name,link,description,version;
    private RecipeStep[] recipeSteps;
    private RecipeIngredient[] recipeIngredients;

    public boolean[] getPhotos() {
        return photos;
    }

    public void setPhotos(boolean[] photos) {
        this.photos = photos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public RecipeStep[] getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(RecipeStep[] recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public RecipeIngredient[] getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(RecipeIngredient[] recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }
    public Recipe(){}
    public Recipe(int id, int likesCount, int price, String name, String link, String description, String version, RecipeStep[] recipeSteps, RecipeIngredient[] recipeIngredients) {
        this.id = id;
        this.likesCount = likesCount;
        this.price = price;
        this.name = name;
        this.link = link;
        this.description = description;
        this.version = version;
        this.recipeSteps = recipeSteps;
        this.recipeIngredients = recipeIngredients;
    }

}
