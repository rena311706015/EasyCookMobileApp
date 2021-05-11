package com.example.android.customerapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.ui.home.HomeFragment;
import com.example.android.customerapp.ui.recipe.AllRecipeFragment;
import com.example.android.customerapp.ui.recipe.RecipeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity{
    private AllRecipeFragment allRecipeFragment;
    private RecipeFragment recipeFragment;
    private HomeFragment homeFragment;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private NavController navController;
    public static final String HOME = "HOME";
    public static final String ALLRECIPE = "ALLRECIPE";
    public static final String RECIPE = "RECIPE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavController.OnDestinationChangedListener listener = (controller1, destination, arguments) -> {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            switch (destination.getId()) {
                case R.id.navigation_home:
                    if (homeFragment == null) homeFragment = new HomeFragment();
                    if (allRecipeFragment != null) mFragmentTransaction.hide(allRecipeFragment);
                    if (recipeFragment != null ) mFragmentTransaction.hide(recipeFragment);
                    if (homeFragment.isAdded()) {
                        mFragmentTransaction.show(homeFragment);
                    } else {
                        mFragmentTransaction.add(R.id.main_container, homeFragment, HOME);
                    }
                    break;
                case R.id.navigation_all_recipe:

                    if (allRecipeFragment == null) allRecipeFragment = new AllRecipeFragment();
                    if (homeFragment != null) mFragmentTransaction.hide(homeFragment);
                    if (recipeFragment != null ) mFragmentTransaction.hide(recipeFragment);
                    if (allRecipeFragment.isAdded()) {
                        mFragmentTransaction.show(allRecipeFragment);
                    } else {
                        mFragmentTransaction.add(R.id.main_container, allRecipeFragment, ALLRECIPE);
                        mFragmentTransaction.addToBackStack(HOME);
                    }
                    break;
            }
            mFragmentTransaction.commit();
        };
        navController.addOnDestinationChangedListener(listener);

        BottomNavigationView bottomNavView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_recipe, R.id.navigation_login)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavView, navController);
    }

    public void onRecipeClick(Bundle bundle){
        recipeFragment = new RecipeFragment();
        recipeFragment.setArguments(bundle);
        mFragmentTransaction = mFragmentManager.beginTransaction();
        if (allRecipeFragment != null && !allRecipeFragment.isHidden()) {
            mFragmentTransaction.hide(allRecipeFragment);
            mFragmentTransaction.addToBackStack(ALLRECIPE);
        }
        mFragmentTransaction.add(R.id.main_container, recipeFragment, RECIPE).commit();
    }
    @Override
    public void onBackPressed() {
        if(mFragmentManager.getBackStackEntryCount()!=0){
            for(int i =0 ; i< mFragmentManager.getBackStackEntryCount(); i++){
                Log.e("Fragment",String.valueOf(mFragmentManager.getBackStackEntryAt(i).getName()));
            }
            switch(mFragmentManager.getBackStackEntryAt(mFragmentManager.getBackStackEntryCount()-1).getName()){
                case HOME:
                    navController.navigate(R.id.navigation_home);
                    mFragmentManager.popBackStack(HOME, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    break;
                case ALLRECIPE:
                    navController.navigate(R.id.navigation_all_recipe);
                    mFragmentManager.popBackStack(ALLRECIPE, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    break;
            }
        }else{
            AlertDialog a = new AlertDialog.Builder(this).setTitle("確定退出?")
                    .setPositiveButton("確定", (dialog1, num1) -> {
                        finish();
                    })
                    .setNegativeButton("取消", (dialog1, num1) -> {
                        return;
                    }).create();
            a.setOnShowListener(dialog12 -> {
                Button positiveButton = a.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.DKGRAY);
                Button negativeButton = a.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setTextColor(Color.DKGRAY);
            });
            a.show();
        }

    }
}
