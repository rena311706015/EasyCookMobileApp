package com.example.android.customerapp.ui.recipe;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.adapters.BindingComponent;
import com.example.android.customerapp.adapters.IngredientAdapter;
import com.example.android.customerapp.databinding.FragmentRecipeDesciptionBinding;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.models.RecipeImage;
import com.example.android.customerapp.viewmodels.RecipeViewModel;

import org.imaginativeworld.whynotimagecarousel.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.ImageCarousel;

import java.util.ArrayList;
import java.util.List;

public class RecipeDescriptionFragment extends Fragment {

    private RecipeViewModel mRecipeViewModel;
    private RecyclerView mIngredientRecyclerView;
    private ImageCarousel mRecipeCarousel;
    private IngredientAdapter mIngredientAdapter;
    private Recipe mRecipe;

    public RecipeDescriptionFragment(Recipe recipe) {
        mRecipe = recipe;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        DataBindingUtil.setDefaultComponent(new BindingComponent());
        FragmentRecipeDesciptionBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_desciption, container, false);
        View root = binding.getRoot();
        mIngredientRecyclerView = root.findViewById(R.id.recipe_ingredients_list);
        mRecipeCarousel = root.findViewById(R.id.recipe_carousel);

        mIngredientAdapter = new IngredientAdapter(getContext(), mRecipe.getRecipeIngredients());
        mIngredientRecyclerView.setAdapter(mIngredientAdapter);
        mIngredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.setRecipe(mRecipe);
        switch (binding.getRecipe().getVersion()) {
            case "正常版本":
                binding.recipeVersion.setTextColor(Color.parseColor("#99876F"));
                break;
            case "低脂版本":
                binding.recipeVersion.setTextColor(Color.parseColor("#8093B5"));
                break;
            case "素食版本":
                binding.recipeVersion.setTextColor(Color.parseColor("#7CA390"));
                break;
            case "肉多版本":
                binding.recipeVersion.setTextColor(Color.parseColor("#F09797"));
                break;
        }


        mRecipeViewModel.getAllPhoto(String.valueOf(mRecipe.getId()));
        List<CarouselItem> list = new ArrayList<>();
        mRecipeViewModel.mImageList.observe(getViewLifecycleOwner(), images -> {
            if (images != null) {
                for (RecipeImage image : images) {
                    list.add(new CarouselItem(image.getS3Url()));
                }
            } else {
                list.add(new CarouselItem(R.drawable.loding));
            }
            mRecipeCarousel.addData(list);
        });

        return root;
    }

}
