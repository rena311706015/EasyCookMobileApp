package com.example.android.customerapp.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.android.customerapp.AppExecutors;
import com.example.android.customerapp.models.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RecipeAPIClient {

    private static final String TAG = "RecipeApiClient";

    private static RecipeAPIClient instance;
    private RecipeAPI recipeAPI;

    public static RecipeAPIClient getInstance() {
        if (instance == null) {
            instance = new RecipeAPIClient();
        }
        return instance;
    }
    private RecipeAPIClient() {
        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        recipeAPI = retrofit.create(RecipeAPI.class);

    }
    public Call<List<Recipe>> getAllRecipe(){
        return recipeAPI.getRecipeOverview();
    }
    public Call<Recipe> getRecipeById(String id){
        return recipeAPI.getRecipeById(id);
    }

}




//    public LiveData<List<Recipe>> getAllRecipeList() {
//        return allRecipeList;
//    }
    //    public void setAllRecipeList(){
//        getAllRecipes();
//    }
//    public LiveData<Recipe> getSelectedRecipe(){
//        return selectedRecipe;
//    }
//    public void setSelectedRecipe(String id){
//        getRecipeById(id);
//    }

//    public LiveData<Boolean> isRecipeRequestTimedOut() {
//        return mRecipeRequestTimeout;
//    }

//    public void getAllRecipes() {
//        if (mRetrieveAllRecipeRunnable != null) {
//            mRetrieveAllRecipeRunnable = null;
//        }
//        mRetrieveAllRecipeRunnable = new RetrieveAllRecipeRunnable();
//        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveAllRecipeRunnable);
//        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
//            @Override
//            public void run() {
//                // let the user know its timed out
//                Log.e(TAG,"timeout");
//                handler.cancel(true);
//            }
//        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
//    }
//
//    public void getRecipeById(String recipeId) {
//        if (mRetrieveGetRecipeRunnable != null) {
//            mRetrieveGetRecipeRunnable = null;
//        }
//        mRetrieveGetRecipeRunnable = new RetrieveGetRecipeRunnable(recipeId);
//
//        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveGetRecipeRunnable);
//
//        mRecipeRequestTimeout.setValue(false);
//        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
//            @Override
//            public void run() {
//                // let the user know it's timed out
//                mRecipeRequestTimeout.postValue(true);
//                handler.cancel(true);
//            }
//        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
//
//    }
//
//    private class RetrieveAllRecipeRunnable implements Runnable {
//
//        boolean cancelRequest;
//
//        public RetrieveAllRecipeRunnable() {
//            cancelRequest = false;
//        }
//
//        @Override
//        public void run() {
//            try {
//                Call<List<Recipe>> call = getAllRecipe();
//                call.enqueue(new Callback<List<Recipe>>() {
//                    @Override
//                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
//                        if(response.code()==200){
//                            Log.e(TAG,"200");
//                            allRecipeList.postValue(response.body());
//                            allRecipeList.setValue(response.body());
//                            //TODO onPostExecute()再notifyDataChange()?
//                        }else{
//                            Log.e(TAG,response.message());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call call, Throwable t) {
//                        Log.e(TAG, "response: " + t.toString());
//                    }
//                });
//            } catch (Exception e) {
//                Log.e(TAG,"Exception");
//                e.printStackTrace();
//                allRecipeList.postValue(null);
//            }
//
//        }
//
//        private Call<List<Recipe>> getAllRecipe() {
//            return ServiceGenerator.getRecipeApi().getRecipeOverview();
//        }
//
//        private void cancelRequest() {
//            Log.d(TAG, "cancelRequest: canceling the search request.");
//            cancelRequest = true;
//        }
//    }
//
//    private class RetrieveGetRecipeRunnable implements Runnable {
//
//        private String recipeId;
//        boolean cancelRequest;
//
//        public RetrieveGetRecipeRunnable(String recipeId) {
//            this.recipeId = recipeId;
//            cancelRequest = false;
//        }
//
//        @Override
//        public void run() {
//            Call<Recipe> call = getRecipeById(recipeId);
//            call.enqueue(new Callback<Recipe>() {
//                @Override
//                public void onResponse(Call<Recipe> call, Response<Recipe> response) {
//                    if(response.code()==200){
//                        selectedRecipe.postValue(response.body());
//                    }else{
//                        Log.e(TAG,response.message());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call call, Throwable t) {
//                    Log.e(TAG, "response: " + t.toString());
//                }
//            });
//
//        }
//
//        private Call<Recipe> getRecipeById(String id) {
//            return ServiceGenerator.getRecipeApi().getRecipeById(id);
//        }
//
//    }










