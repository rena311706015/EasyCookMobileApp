package com.example.android.customerapp.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
    public int Image,id,likeCount;
    public String name,link,description;
    public ArrayList<RecipeStep> steps;
    public ArrayList<RecipeIngredient> ingredients;
    public Recipe(){
        super();
    }
    public Recipe(int id, String name, String link, int likeCount, String description, ArrayList<RecipeStep> steps, ArrayList<RecipeIngredient> ingredients){
        this.id=id;
        this.name=name;
        this.link=link;
        this.likeCount=likeCount;
        this.description=description;
        this.steps=steps;
        this.ingredients=ingredients;
    }
}
