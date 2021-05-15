package com.example.android.customerapp.models;

import java.io.Serializable;

public class RecipeImage implements Serializable {
    private int id;
    private String s3Url;

    public RecipeImage(int id, String s3Url) {
        this.id = id;
        this.s3Url = s3Url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getS3Url() {
        return s3Url;
    }

    public void setS3Url(String s3Url) {
        this.s3Url = s3Url;
    }

}

