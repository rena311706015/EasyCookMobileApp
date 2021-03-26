package com.example.android.customerapp.ui.recipe;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.example.android.customerapp.CustomerServiceActivity;
import com.example.android.customerapp.MainActivity;
import com.example.android.customerapp.R;
import com.example.android.customerapp.VideoPlayerActivity;
import com.example.android.customerapp.adapters.OnRecipeListener;
import com.example.android.customerapp.adapters.RecipeRecyclerAdapter;
import com.example.android.customerapp.models.LodingDialog;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.viewmodels.AllRecipeViewModel;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class AllRecipeFragment extends Fragment implements OnRecipeListener {
    private RecipeRecyclerAdapter mAdapter;
    private AllRecipeViewModel mRecipeViewModel;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private List<Recipe> recipeList;
    private LodingDialog lodingDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRecipeViewModel = ViewModelProviders.of(this).get(AllRecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_all_recipe, container, false);
        mRecyclerView=root.findViewById(R.id.recyclerview_recipes);
        mSearchView=root.findViewById(R.id.searchview);

        mAdapter = new RecipeRecyclerAdapter(this);
        recipeList=new ArrayList<>();
        lodingDialog = new LodingDialog(getContext());
        lodingDialog.show();
        /*要抓資料要抓id為3的*/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                initRecyclerView();
                initSearchView();
            }
        },3000);
        return root;
    }
    private void initRecyclerView(){
        mAdapter.setRecipes(mRecipeViewModel.getRecipes().getValue());
        recipeList=mRecipeViewModel.getRecipes().getValue();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        lodingDialog.dismiss();
    }
    private void initSearchView(){

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                mRecipeViewModel.searchRecipesApi(s);
                mSearchView.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public void onRecipeClick(int position) {
        mRecipeViewModel.getRecipeById(String.valueOf(recipeList.get(position).getId()));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe", mRecipeViewModel.getRecipe().getValue());
                Navigation.findNavController(mRecyclerView).navigate(R.id.action_navigation_all_recipe_to_navigation_recipe,bundle);
            }
        },1000);

    }

    @Override
    public void onCategoryClick(String category) {

    }

}
