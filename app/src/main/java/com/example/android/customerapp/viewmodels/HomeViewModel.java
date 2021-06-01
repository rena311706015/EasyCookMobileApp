package com.example.android.customerapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.requests.BackendAPIClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    public MediatorLiveData<Recipe> mRecipe;
    public MediatorLiveData<List<Recipe>> mRecipeList;

    public HomeViewModel() {
        mRecipe = new MediatorLiveData<>();
        mRecipeList = new MediatorLiveData<>();
    }

    public void getRecipeList() {
        BackendAPIClient.getInstance().getAllRecipe().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.e("ViewModel", "onResponse");
                mRecipeList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("API", "FAIL");
            }
        });
    }

    public void getRecipeById(String id) {
        BackendAPIClient.getInstance().getRecipeById(id).enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                Log.e("ViewModel", "onResponse");
                mRecipe.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.e("API", "FAIL");
            }
        });
    }
}