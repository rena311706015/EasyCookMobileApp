package com.example.android.customerapp.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.Nullable;

import org.checkerframework.checker.nullness.compatqual.NullableType;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable{
    private Bitmap photoBitmap;
    private int id, likesCount, price;
    private String name, photo, link, description, version;
    private RecipeStep[] recipeSteps;
    private RecipeIngredient[] recipeIngredients;

    public Recipe(){}

    public Recipe(Bitmap photoBitmap, int id, int likesCount, int price, String name, String photo, String link, String description, String version, RecipeStep[] recipeSteps, RecipeIngredient[] recipeIngredients) {
        this.photoBitmap = photoBitmap;
        this.id = id;
        this.likesCount = likesCount;
        this.price = price;
        this.name = name;
        this.photo = photo;
        this.link = link;
        this.description = description;
        this.version = version;
        this.recipeSteps = recipeSteps;
        this.recipeIngredients = recipeIngredients;
    }

    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }

    public void setPhotoBitmap(@Nullable Bitmap photoBitmap) {
        this.photoBitmap = photoBitmap;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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




}
