package com.example.android.customerapp.viewmodels;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.requests.BackendAPIClient;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllRecipeViewModel extends ViewModel {

    public MediatorLiveData<List<Recipe>> mRecipeList;
    public MediatorLiveData<byte[]> mByte;

    public AllRecipeViewModel() {
        mRecipeList = new MediatorLiveData<>();
        mByte = new MediatorLiveData<>();
    }

    public void getRecipeList() {
        //enqueue ->會另外開執行緒，所以可以不用再另外用runnable
        BackendAPIClient.getInstance().getAllRecipe().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                mRecipeList.setValue(response.body());
                getPhoto();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("RECIPE", "FAIL");
                Log.e("RECIPE", t.getMessage());
            }
        });
    }

    public void getPhoto() {
        List<Recipe> recipeList = mRecipeList.getValue();
        for (Recipe recipe : recipeList) {
            if (recipe.getBlobId() != "No Image") {
                BackendAPIClient.getInstance().getImage(recipe.getBlobId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.body() != null) {

                                byte[] bytes;
                                bytes = response.body().bytes();
                                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                Recipe recipeWithPhoto = recipe;
                                recipeWithPhoto.setPhotoBitmap(bmp);
                                recipeList.set(recipeList.indexOf(recipe), recipeWithPhoto);
                                mRecipeList.setValue(recipeList);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("API", t.getMessage());
                    }
                });
            }

        }
    }

}
