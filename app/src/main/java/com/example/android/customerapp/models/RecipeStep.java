package com.example.android.customerapp.models;

public class RecipeStep {
    int id;
    String startTime,note;
    RecipeStep(int id, String startTime, String note){
        this.id=id;
        this.startTime=startTime;
        this.note=note;
    }
}
