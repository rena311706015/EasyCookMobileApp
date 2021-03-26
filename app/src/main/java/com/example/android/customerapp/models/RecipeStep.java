package com.example.android.customerapp.models;

import java.io.Serializable;

public class RecipeStep implements Serializable {
    int id;
    int startTime;
    int timer;
    String note;
    public RecipeStep(){};
    public RecipeStep(int id, int startTime, String note, int timer){
        this.id=id;
        this.startTime=startTime;
        this.timer=timer;
        this.note=note;
    }
    public RecipeStep(int id, int startTime, String note){
        this.id=id;
        this.startTime=startTime;
        this.timer=0;
        this.note=note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }


}
