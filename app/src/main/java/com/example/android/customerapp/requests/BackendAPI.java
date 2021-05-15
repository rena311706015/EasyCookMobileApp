package com.example.android.customerapp.requests;

import com.example.android.customerapp.models.Member;
import com.example.android.customerapp.models.Order;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.models.RecipeImage;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BackendAPI {

    @GET("recipe/{id}")
    Call<Recipe> getRecipeById(@Path("id") String id);

    @GET("recipe/overview")
    Call<List<Recipe>> getRecipeOverview();

    @POST("member/register")
    Call<Member> memberRegister(@Body Member member);

    @POST("login")
    Call<JsonObject> memberLogin(@Body Member member);

    @GET("order/all")
    Call<List<Order>> getAllOrder(@Header("Authorization") String auth);

    @GET("recipe/images/all/{id}")
    Call<List<RecipeImage>> getAllImage(@Path("id") String id);
}

