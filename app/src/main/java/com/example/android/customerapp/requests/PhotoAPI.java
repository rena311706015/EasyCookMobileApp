package com.example.android.customerapp.requests;

import android.graphics.Bitmap;

import com.example.android.customerapp.models.Recipe;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PhotoAPI {
    @GET("{name}")
    Call<ResponseBody> getPhoto(
            @Path("name") String name
    );
}
