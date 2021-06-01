package com.example.android.customerapp.ui.recipe;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.android.customerapp.R;
import com.example.android.customerapp.adapters.BindingComponent;
import com.example.android.customerapp.adapters.RecipePagerAdapter;
import com.example.android.customerapp.databinding.FragmentRecipeBinding;
import com.example.android.customerapp.models.LodingDialog;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.viewmodels.RecipeViewModel;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.material.tabs.TabLayout;

public class RecipeFragment extends Fragment {

    private RecipeViewModel mRecipeViewModel;
    private Recipe mRecipe;
    private LodingDialog lodingDialog;
    private TabLayout tabs;
    private ViewPager pager;
    private RecipePagerAdapter pagerAdapter;

    public RecipeFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        DataBindingUtil.setDefaultComponent(new BindingComponent());
        FragmentRecipeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);
        View root = binding.getRoot();
        tabs = root.findViewById(R.id.tabs);
        pager = root.findViewById(R.id.pager);
        lodingDialog = new LodingDialog(getContext());
        lodingDialog.show();
        pagerAdapter = new RecipePagerAdapter(this.getFragmentManager());
        mRecipe = (Recipe) getArguments().getSerializable("recipe");

        mRecipeViewModel.getRecipeById(String.valueOf(mRecipe.getId()));
        mRecipeViewModel.mRecipe.observe(getViewLifecycleOwner(), recipe -> {
            lodingDialog.dismiss();
            pagerAdapter.addFragment(new RecipeDescriptionFragment(recipe), "食譜介紹");
            pagerAdapter.addFragment(new RecipeVideoFragment(recipe), "食譜影片");
            pager.setAdapter(pagerAdapter);
            tabs.setupWithViewPager(pager);
        });


        return root;
    }

}