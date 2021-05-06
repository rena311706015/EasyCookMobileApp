package com.example.android.customerapp.requests;

import com.example.android.customerapp.models.Member;
import com.example.android.customerapp.models.Order;
import com.example.android.customerapp.models.Recipe;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class BackendAPIClient {

    private static final String TAG = "RecipeApiClient";
    //標準的單例模式
    private static BackendAPIClient instance;
    private BackendAPI backendAPI;

    public static BackendAPIClient getInstance() {
        if (instance == null) {
            instance = new BackendAPIClient();
        }
        return instance;
    }
    private BackendAPIClient() {
        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        backendAPI = retrofit.create(BackendAPI.class);

    }
    public Call<List<Recipe>> getAllRecipe(){
        return backendAPI.getRecipeOverview();
    }
    public Call<Recipe> getRecipeById(String id){
        return backendAPI.getRecipeById(id);
    }
    public Call<Member> memberRegister(Member member){
        return backendAPI.memberRegister(member);
    }
    public Call<JsonObject> memberLogin(Member member){
        return backendAPI.memberLogin(member);
    }
    public Call<List<Order>> getAllOrder(String auth) { return backendAPI.getAllOrder(auth);}
}









