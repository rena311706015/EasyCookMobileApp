package com.example.android.customerapp.viewmodels;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.android.customerapp.R;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.requests.PhotoAPIClient;
import com.example.android.customerapp.requests.RecipeAPIClient;
import com.google.protobuf.StringValue;

import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllRecipeViewModel extends ViewModel {

    public MediatorLiveData<List<Recipe>> mRecipeList;

    public AllRecipeViewModel() {
        mRecipeList = new MediatorLiveData<>();
    }

    public void getRecipeList(){
        RecipeAPIClient.getInstance().getAllRecipe().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.e("RECIPE","onResponse");
                mRecipeList.setValue(response.body());
                getPhoto();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("RECIPE","FAIL");
                Log.e("RECIPE",t.getMessage());
            }
        });
    }

    public void getPhoto(){
        List<Recipe> recipeList = mRecipeList.getValue();
        for(Recipe recipe : recipeList){
            if(recipe.getPhoto().contains("http")){
                String name = recipe.getPhoto().substring(recipe.getPhoto().lastIndexOf("/")+1);
                PhotoAPIClient.getInstance().getPhoto(name).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            byte[] bytes;
                            bytes = response.body().bytes();
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 4;
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

                            Recipe recipeWithPhoto = recipe;
                            recipeWithPhoto.setPhotoBitmap(bitmap);
                            recipeList.set(recipeList.indexOf(recipe), recipeWithPhoto);
                            mRecipeList.setValue(recipeList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("PHOTO",t.getMessage());
                    }
                });
            }else{
            }

        }

    }
}