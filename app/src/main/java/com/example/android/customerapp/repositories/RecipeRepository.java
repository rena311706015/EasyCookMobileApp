package com.example.android.customerapp.repositories;


import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.requests.RecipeAPIClient;

import org.w3c.dom.Text;

import java.util.List;

public class RecipeRepository {
    private static String TAG="RecipePepository";
    private static RecipeRepository instance;
    private RecipeAPIClient mRecipeApiClient;
    private String mQuery;
    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<Recipe>> mRecipes = new MediatorLiveData<>();
    private MutableLiveData<Recipe> mRecipe = new MediatorLiveData<>();

    public static RecipeRepository getInstance(){
        if(instance == null){
            instance = new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository(){
        mRecipeApiClient = RecipeAPIClient.getInstance();
        initMediators();
    }

    private void initMediators(){
        LiveData<List<Recipe>> recipeListApiSource = mRecipeApiClient.getAllRecipeList();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecipes.setValue(recipeListApiSource.getValue());
                mRecipes.addSource(recipeListApiSource, new Observer<List<Recipe>>() {
                    @Override
                    public void onChanged(@Nullable List<Recipe> recipes) {
                        if(recipes != null){
                            mRecipes.setValue(recipes);
                            doneQuery(recipes);
                        }
                        else{
                            doneQuery(null);
                        }
                    }
                });
            }
        },2000);

    }

    private void doneQuery(List<Recipe> list){
        if(list != null){
            if (list.size() % 30 != 0) {
                mIsQueryExhausted.setValue(true);
            }
        }
        else{
            mIsQueryExhausted.setValue(true);
        }
    }

    public LiveData<Boolean> isQueryExhausted(){
        return mIsQueryExhausted;
    }

    public LiveData<List<Recipe>> getAllRecipe(){
        return mRecipes;
    }

    public LiveData<Recipe> getSelectedRecipe(){return  mRecipe;}

    public void getRecipe(String recipeId){
        LiveData<Recipe> recipeApiSource = mRecipeApiClient.getSelectedRecipe(recipeId);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecipe.setValue(recipeApiSource.getValue());
            }
        },1000);
    }


    public void searchRecipesApi(String query){
        Log.e(TAG,"searchRecipesApi");
        mQuery = query;
        mIsQueryExhausted.setValue(false);
        mRecipeApiClient.getAllRecipes();
    }


    public LiveData<Boolean> isRecipeRequestTimedOut(){
        return mRecipeApiClient.isRecipeRequestTimedOut();
    }
}




