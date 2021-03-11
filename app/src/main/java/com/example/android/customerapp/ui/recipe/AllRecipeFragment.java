package com.example.android.customerapp.ui.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.android.customerapp.GET;
import com.example.android.customerapp.R;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.adapters.RecipeAdapter;
import com.example.android.customerapp.viewmodels.RecipeViewModel;

import java.util.ArrayList;

public class AllRecipeFragment extends Fragment {

    private RecipeViewModel recipeViewModel;
    private RecyclerView recyclerView;
    private ArrayList<Recipe> recipeList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recipeViewModel =
                ViewModelProviders.of(this).get(RecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_all_recipe, container, false);
        recipeList=new ArrayList<>();

        GET get = new GET();
        get.execute("http://140.118.9.145:8082/recipe/all");
        get.setOnAsyncResponse(new GET.AsyncResponse() {
            @Override
            public void onDataReceivedSuccess(ArrayList<Recipe> data) {
                Log.e("GET","successed");
                recipeList=data;
                recyclerView = root.findViewById(R.id.recyclerview_recipes);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setAdapter(new RecipeAdapter(getContext(), recipeList));
            }
            @Override
            public void onDataReceivedFailed() {
                Log.e("獲取食譜失敗","fail");
            }
        });


        return root;
    }
}
