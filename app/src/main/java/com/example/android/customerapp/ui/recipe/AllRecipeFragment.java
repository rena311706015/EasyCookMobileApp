package com.example.android.customerapp.ui.recipe;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android.customerapp.R;
import com.example.android.customerapp.adapters.OnRecipeListener;
import com.example.android.customerapp.adapters.RecipeRecyclerAdapter;
import com.example.android.customerapp.models.LodingDialog;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.viewmodels.AllRecipeViewModel;

import java.util.ArrayList;
import java.util.List;


public class AllRecipeFragment extends Fragment implements OnRecipeListener {
    private RecipeRecyclerAdapter mAdapter;
    private AllRecipeViewModel mRecipeViewModel;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private List<Recipe> mRecipeList;
    private LodingDialog lodingDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecipeViewModel = ViewModelProviders.of(this).get(AllRecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_all_recipe, container, false);
        mRecyclerView = root.findViewById(R.id.recyclerview_recipes);

        mToolbar = root.findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24, null));
        mToolbar.inflateMenu(R.menu.search_menu);

        mRecipeList = new ArrayList<>();

        lodingDialog = new LodingDialog(getContext());
        lodingDialog.show();

        mRecipeViewModel.getRecipeList();
        mRecipeViewModel.mRecipeList.observe(getViewLifecycleOwner(), recipeList -> {
            Log.e("RecipeList","OBSERVE");
            mRecipeList=recipeList;
            mAdapter.setRecipeList(recipeList);
            lodingDialog.dismiss();
        });
        initRecyclerView();
        return root;
    }

    private void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(getContext(),this,mRecipeList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onRecipeClick(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(mRecipeList.get(position).getId()));
            Navigation.findNavController(getView()).navigate(R.id.action_navigation_all_recipe_to_navigation_recipe,bundle);
    }

}
