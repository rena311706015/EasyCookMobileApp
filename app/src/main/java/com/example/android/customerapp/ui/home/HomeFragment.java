package com.example.android.customerapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.android.customerapp.MainActivity;
import com.example.android.customerapp.R;
import com.example.android.customerapp.VideoPlayerActivity;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.models.RecipeStep;
import com.example.android.customerapp.repositories.RecipeRepository;
import com.example.android.customerapp.viewmodels.AllRecipeViewModel;
import com.example.android.customerapp.viewmodels.HomeViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private AllRecipeViewModel homeViewModel;
    private ImageView toVideoPlayer;
    private List<Recipe> mRecipe;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(AllRecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        toVideoPlayer = root.findViewById(R.id.button1);

        toVideoPlayer.setOnClickListener(v -> {
            mRecipe=homeViewModel.getRecipes().getValue();
            String[] recipeNameList=new String[mRecipe.size()];
            int index=0;
            for(Recipe recipe:mRecipe){
                recipeNameList[index]=recipe.getName();
                index+=1;
            }
            AlertDialog.Builder dialog_list = new AlertDialog.Builder(getContext());
            dialog_list.setTitle("請選擇要導覽的食譜");
            dialog_list.setItems(recipeNameList, (dialog, index1) -> {
                homeViewModel.getRecipeById(String.valueOf(mRecipe.get(index1).getId()));

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Recipe selectedRecipe=homeViewModel.getRecipe().getValue();
                        Log.e("HOME",String.valueOf(selectedRecipe==null));
                        Intent intent = new Intent();
                        intent.setClass(getContext(), VideoPlayerActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("recipe", selectedRecipe);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                },2000);
            });
            dialog_list.show();
        });
        return root;
    }
}
