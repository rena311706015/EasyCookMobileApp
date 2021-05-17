package com.example.android.customerapp.models;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Recipe implements Serializable {
    private  transient Bitmap photoBitmap;
    private int id, likesCount, price;
    private String name, photo, link, description, version;
    private RecipeStep[] recipeSteps;
    private RecipeIngredient[] recipeIngredients;
    private String blobId, recipeImage;


    public Recipe() {
    }

    public Recipe(int id, String link, RecipeStep[] recipeSteps){
        this.id = id;
        this.link = link;
        this.recipeSteps=recipeSteps;
    }

    public Recipe(Bitmap photoBitmap, int id, int likesCount, int price, String name, String photo, String link, String description, String version, RecipeStep[] recipeSteps, RecipeIngredient[] recipeIngredients, String blobId, String recipeImage) {
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
        this.blobId = blobId;
        this.recipeImage = recipeImage;
    }

    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }

    public void setPhotoBitmap(@Nullable Bitmap photoBitmap) {
        this.photoBitmap = photoBitmap;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
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

    public String getPrice() {
        return "NT$ "+ price;
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
        switch (version) {
            case "NORMAL":
                return "正常版本";
            case "LOWFAT":
                return "低脂版本";
            case "VAGE":
                return "素食版本";
            case "MEAT":
                return "肉多版本";
        }
        return "正常版本";
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

    public String getBlobId() {
        return blobId;
    }

    public void setBlobId(String blobId) {
        this.blobId = blobId;
    }
}
