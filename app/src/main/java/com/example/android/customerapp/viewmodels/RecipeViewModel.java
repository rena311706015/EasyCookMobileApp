package com.example.android.customerapp.viewmodels;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.models.RecipeImage;
import com.example.android.customerapp.requests.BackendAPIClient;
import com.example.android.customerapp.requests.PhotoAPIClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeViewModel extends ViewModel {

    public MediatorLiveData<Recipe> mRecipe;
    public MediatorLiveData<List<RecipeImage>> mImageList;
    public MediatorLiveData<List<Bitmap>> mBitmapList;

    public RecipeViewModel() {
        mRecipe = new MediatorLiveData<>();
        mImageList = new MediatorLiveData<>();
        mBitmapList = new MediatorLiveData<>();
    }

    public void getRecipeById(String id) {
        BackendAPIClient.getInstance().getRecipeById(id).enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                Log.e("Recipe", response.body().getName());
                mRecipe.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.e("CALLBACK", t.getMessage());
            }
        });
    }

    public void getAllPhoto(String id) {
        BackendAPIClient.getInstance().getAllImage(id).enqueue(new Callback<List<RecipeImage>>() {
            @Override
            public void onResponse(Call<List<RecipeImage>> call, Response<List<RecipeImage>> response) {
                mImageList.setValue(response.body());
//                getPhoto();
            }

            @Override
            public void onFailure(Call<List<RecipeImage>> call, Throwable t) {
                Log.e("API", "FAIL");
            }
        });
    }

    public void getPhoto() {
        List<Bitmap> bitmaps = new ArrayList<>();
        for (RecipeImage image : mImageList.getValue()) {
            if (image.getS3Url().contains("http")) {
                String name = image.getS3Url().substring(image.getS3Url().lastIndexOf("/") + 1);
                PhotoAPIClient.getInstance().getPhoto(name).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.body() != null) {
                                byte[] bytes;
                                bytes = response.body().bytes();
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize = 4;
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                                bitmaps.add(bitmap);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("PHOTO", t.getMessage());
                    }
                });
            }

        }
        mBitmapList.setValue(bitmaps);
    }

}
