package com.example.android.customerapp.ui.recipe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.adapters.OnRecipeListener;
import com.example.android.customerapp.adapters.RecipeAdapter;
import com.example.android.customerapp.models.LodingDialog;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.viewmodels.AllRecipeViewModel;

import java.util.ArrayList;
import java.util.List;


public class AllRecipeFragment extends Fragment implements OnRecipeListener {
    private RecipeAdapter mAdapter;
    private AllRecipeViewModel mAllRecipeViewModel;
    private RecyclerView mRecyclerView;
    private List<Recipe> mRecipeList;
    private LodingDialog lodingDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mAllRecipeViewModel = ViewModelProviders.of(this).get(AllRecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_all_recipe, container, false);
        mRecyclerView = root.findViewById(R.id.recyclerview_recipes);
        mRecipeList = new ArrayList<>();

        lodingDialog = new LodingDialog(getContext());
        lodingDialog.show();
        //TODO 圖片還沒載入時要顯示動畫
        mAllRecipeViewModel.getRecipeList();
        mAllRecipeViewModel.mRecipeList.observe(getViewLifecycleOwner(), recipeList -> {
            lodingDialog.dismiss();
            Log.e("RecipeList","Observe");
            mRecipeList = recipeList;
            mAdapter.setRecipeList(mRecipeList);
        });

        initRecyclerView();
        return root;
    }

    private void initRecyclerView() {
        mAdapter = new RecipeAdapter(getContext(), this, mRecipeList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onRecipeClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", mRecipeList.get(position));
        Navigation.findNavController(getView()).navigate(R.id.action_navigation_all_recipe_to_navigation_recipe, bundle);
    }

}
