package com.example.android.customerapp;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.models.RecipeIngredient;
import com.example.android.customerapp.models.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GET extends AsyncTask<String,String,String> {
    public AsyncResponse asyncResponse;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        Request request = new Request.Builder()
                .url(params[0])
                .build();
        try (Response response = client.newCall(request).execute()) {
            String result=response.body().string();
            String result1 = result.replaceAll("\\\\", "");
            String result2 = result1.replaceAll(Matcher.quoteReplacement("$"), "");
            Log.e("RESPONSE",result2);
//            String responsebody = result2.substring(1, result2.length()-1);
            return result2;
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public void setOnAsyncResponse(AsyncResponse asyncResponse)
    {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null)
        {
            ArrayList<Recipe> recipes = new ArrayList<>();
            ArrayList<RecipeStep> steps = new ArrayList<>();
            ArrayList<RecipeIngredient> ingredients = new ArrayList<>();
            try {
                if (result != null) {
                    JSONArray recipeArray = new JSONArray(result);
                    for (int i = 0; i < recipeArray.length(); i++) {
                        JSONObject recipe = recipeArray.getJSONObject(i);
                        int id = recipe.getInt("id");
                        String name = recipe.getString("name");
                        String link = recipe.getString("link");
                        int likeCount = recipe.getInt("likesCount");
                        String description = recipe.getString("description");

                        JSONArray stepArray = recipe.getJSONArray("recipeSteps");
                        for (int j = 0; j < stepArray.length(); j++) {
                            JSONObject step = stepArray.getJSONObject(j);
                            int stepId = step.getInt("id");
                            String startTime = step.getString("startTime");
                            String note = step.getString("note");
                            steps.add(new RecipeStep(stepId,startTime,note));
                        }
                        JSONArray recipeIngredientArray = recipe.getJSONArray("recipeIngredients");
                        for (int j = 0; j < recipeIngredientArray.length(); j++) {
                            JSONObject recipeIngredient = recipeIngredientArray.getJSONObject(j);
                            int ingredientId = recipeIngredient.getInt("id");
//                            JSONObject ingredientObject = recipeIngredientArray.getJSONObject(1);
//                            Ingredient ingredient=new Ingredient();
                            int quantityRequired = recipeIngredient.getInt("quantityRequired");
                            ingredients.add(new RecipeIngredient(ingredientId,quantityRequired));
                        }
                        recipes.add(new Recipe(id,name,link,likeCount,description,steps,ingredients));
                    }
                }
                asyncResponse.onDataReceivedSuccess(recipes);
            }
            catch(JSONException e) {
                e.printStackTrace();
            }
        }else {
            asyncResponse.onDataReceivedFailed();
        }
    }

    public interface AsyncResponse {
        void onDataReceivedSuccess(ArrayList<Recipe> data);
        void onDataReceivedFailed();
    }

}
