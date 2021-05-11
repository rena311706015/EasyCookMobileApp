package com.example.android.customerapp.ui.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.MainActivity;
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
    private Toolbar mToolbar;
    private List<Recipe> mRecipeList;
    private LodingDialog lodingDialog;
    public AllRecipeFragment(){}
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mAllRecipeViewModel = ViewModelProviders.of(this).get(AllRecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_all_recipe, container, false);
        mRecyclerView = root.findViewById(R.id.recyclerview_recipes);

        mToolbar = root.findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24, null));
        mToolbar.inflateMenu(R.menu.search_menu);

        mRecipeList = new ArrayList<>();

        lodingDialog = new LodingDialog(getContext());
        lodingDialog.show();

        mAllRecipeViewModel.getRecipeList();
        mAllRecipeViewModel.mRecipeList.observe(getViewLifecycleOwner(), recipeList -> {
            mRecipeList=recipeList;
            mAdapter.setRecipeList(recipeList);
        });
        initRecyclerView();
        return root;
    }

    private void initRecyclerView(){
        mAdapter = new RecipeAdapter(getContext(),this,mRecipeList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        lodingDialog.dismiss();
    }

    @Override
    public void onRecipeClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe",mRecipeList.get(position));
        ((MainActivity)getActivity()).onRecipeClick(bundle);
//        Navigation.findNavController(getView()).navigate(R.id.action_navigation_all_recipe_to_navigation_recipe,bundle);
    }

}
