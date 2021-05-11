package com.example.android.customerapp.viewmodels;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.requests.BackendAPIClient;
import com.example.android.customerapp.requests.PhotoAPIClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeViewModel extends ViewModel {

    public MediatorLiveData<Recipe> mRecipe;

    public RecipeViewModel() {
        mRecipe = new MediatorLiveData<>();
    }
    public void getRecipeById(String id){
        BackendAPIClient.getInstance().getRecipeById(id).enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                mRecipe.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.e("API","FAIL");
            }
        });
    }

}
