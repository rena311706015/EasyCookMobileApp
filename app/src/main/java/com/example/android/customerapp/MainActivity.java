package com.example.android.customerapp;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    //    private AllRecipeFragment allRecipeFragment;
//    private RecipeFragment recipeFragment;
//    private HomeFragment homeFragment;
//    private AccountFragment accountFragment;
//    private LoginFragment loginFragment;
//    private AllOrderFragment allOrderFragment;
//    private OrderFragment orderFragment;
//
//    private FragmentManager mFragmentManager;
//    private FragmentTransaction mFragmentTransaction;
    private NavController navController;

    //    public static final String HOME = "HOME";
//    public static final String ACCOUNT = "ACCOUNT";
//    public static final String LOGIN = "LOGIN";
//    public static final String ALLRECIPE = "ALLRECIPE";
//    public static final String RECIPE = "RECIPE";
//    public static final String ALLORDER = "ALLORDER";
//    public static final String ORDER = "ORDER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
//        mFragmentManager = getSupportFragmentManager();
//        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavController.OnDestinationChangedListener listener = (controller1, destination, arguments) -> {
//            mFragmentTransaction = mFragmentManager.beginTransaction();
//            switch (destination.getId()) {
//                case R.id.navigation_home:
//                    if (homeFragment == null) homeFragment = new HomeFragment();
//                    if (allRecipeFragment != null) mFragmentTransaction.hide(allRecipeFragment);
//                    if (recipeFragment != null ) mFragmentTransaction.hide(recipeFragment);
//                    if (accountFragment != null) mFragmentTransaction.hide(accountFragment);
//                    if (allOrderFragment != null) mFragmentTransaction.hide(allOrderFragment);
//                    if (orderFragment != null) mFragmentTransaction.hide(orderFragment);
//                    if (homeFragment.isAdded()) {
//                        mFragmentTransaction.show(homeFragment);
//                    } else {
//                        mFragmentTransaction.add(R.id.main_container, homeFragment, HOME);
//                    }
//                    break;
//                case R.id.navigation_all_recipe:
//
//                    if (allRecipeFragment == null) allRecipeFragment = new AllRecipeFragment();
//                    if (homeFragment != null) mFragmentTransaction.hide(homeFragment);
//                    if (recipeFragment != null ) mFragmentTransaction.hide(recipeFragment);
//                    if (accountFragment != null) mFragmentTransaction.hide(accountFragment);
//                    if (allOrderFragment != null) mFragmentTransaction.hide(allOrderFragment);
//                    if (orderFragment != null) mFragmentTransaction.hide(orderFragment);
//                    if (allRecipeFragment.isAdded()) {
//                        mFragmentTransaction.show(allRecipeFragment);
//                    } else {
//                        mFragmentTransaction.add(R.id.main_container, allRecipeFragment, ALLRECIPE);
//                    }
//                    break;
//                case R.id.navigation_account:
//
//                    if (accountFragment == null) accountFragment = new AccountFragment();
//                    if (homeFragment != null) mFragmentTransaction.hide(homeFragment);
//                    if (recipeFragment != null ) mFragmentTransaction.hide(recipeFragment);
//                    if (allRecipeFragment != null) mFragmentTransaction.hide(allRecipeFragment);
//                    if (allOrderFragment != null) mFragmentTransaction.hide(allOrderFragment);
//                    if (orderFragment != null) mFragmentTransaction.hide(orderFragment);
//                    if (loginFragment != null) mFragmentTransaction.hide(loginFragment);
//                    if (accountFragment.isAdded()) {
//                        mFragmentTransaction.show(accountFragment);
//                    } else {
//                        mFragmentTransaction.add(R.id.main_container, accountFragment, ACCOUNT);
//                    }
//                    break;
//                case R.id.navigation_all_order:
//                    if (allOrderFragment == null) allOrderFragment = new AllOrderFragment();
//                    if (allRecipeFragment != null) mFragmentTransaction.hide(allRecipeFragment);
//                    if (recipeFragment != null ) mFragmentTransaction.hide(recipeFragment);
//                    if (accountFragment != null) mFragmentTransaction.hide(accountFragment);
//                    if (orderFragment != null) mFragmentTransaction.hide(orderFragment);
//                    if (allOrderFragment.isAdded()) {
//                        mFragmentTransaction.show(allOrderFragment);
//                    } else {
//                        mFragmentTransaction.add(R.id.main_container, allOrderFragment, ALLORDER);
//                    }
//                    break;
//                case R.id.navigation_login:
//                    if (loginFragment == null) loginFragment = new LoginFragment();
//                    if (allRecipeFragment != null) mFragmentTransaction.hide(allRecipeFragment);
//                    if (recipeFragment != null ) mFragmentTransaction.hide(recipeFragment);
//                    if (accountFragment != null) mFragmentTransaction.hide(accountFragment);
//                    if (orderFragment != null) mFragmentTransaction.hide(orderFragment);
//                    if (allOrderFragment !=null) mFragmentTransaction.hide(allOrderFragment);
//                    if (loginFragment.isAdded()) {
//                        mFragmentTransaction.show(loginFragment);
//                    } else {
//                        mFragmentTransaction.add(R.id.main_container, loginFragment, LOGIN);
//                    }
//            }
//
//            if(mFragmentManager.getBackStackEntryCount()>0) {
//                switch (mFragmentManager.getBackStackEntryAt(mFragmentManager.getBackStackEntryCount() - 1).getName()) {
//                    case HOME:
//                        mFragmentTransaction.addToBackStack(HOME);
//                    case ALLRECIPE:
//                        mFragmentTransaction.addToBackStack(ALLRECIPE);
//                    case ACCOUNT:
//                        mFragmentTransaction.addToBackStack(ACCOUNT);
//                }
//            }else{mFragmentTransaction.addToBackStack(HOME);}
//            mFragmentTransaction.commit();
//        };
//        navController.addOnDestinationChangedListener(listener);
        BottomNavigationView bottomNavView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_recipe, R.id.navigation_login)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavView, navController);
    }

//    public void onRecipeClick(Bundle bundle){
//        recipeFragment = new RecipeFragment();
//        recipeFragment.setArguments(bundle);
//        mFragmentTransaction = mFragmentManager.beginTransaction();
//        if (allRecipeFragment != null && !allRecipeFragment.isHidden()) {
//            mFragmentTransaction.hide(allRecipeFragment);
//            mFragmentTransaction.addToBackStack(ALLRECIPE);
//        }
//        mFragmentTransaction.add(R.id.main_container, recipeFragment, RECIPE).commit();
//    }
//
//    public void onOrderClick(Bundle bundle){
//        orderFragment = new OrderFragment();
//        orderFragment.setArguments(bundle);
//        mFragmentTransaction = mFragmentManager.beginTransaction();
//        if (allOrderFragment != null && !allOrderFragment.isHidden()) {
//            mFragmentTransaction.hide(allOrderFragment);
//            mFragmentTransaction.addToBackStack(ALLORDER);
//        }
//        mFragmentTransaction.add(R.id.main_container, orderFragment, ORDER).commit();
//    }
//    public NavController getNavController(){
//        return navController;
//    }
//    @Override
//    public void onBackPressed() {
//        if(mFragmentManager.getBackStackEntryCount()>0){
//            for(int i =0 ; i< mFragmentManager.getBackStackEntryCount(); i++){
//                Log.e("Fragment",String.valueOf(mFragmentManager.getBackStackEntryAt(i).getName()));
//            }
//            switch(mFragmentManager.getBackStackEntryAt(mFragmentManager.getBackStackEntryCount()-1).getName()){
//                case HOME:
//                    navController.navigate(R.id.navigation_home);
//                    mFragmentManager.popBackStack(HOME, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    break;
//                case ALLRECIPE:
//                    navController.navigate(R.id.navigation_all_recipe);
//                    mFragmentManager.popBackStack(ALLRECIPE, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    break;
//                case ACCOUNT:
//                    navController.navigate(R.id.navigation_account);
//                    mFragmentManager.popBackStack(ACCOUNT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    break;
//                case ALLORDER:
//                    navController.navigate(R.id.navigation_all_order);
//                    mFragmentManager.popBackStack(ALLORDER, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    break;
//
//            }
//        }else{
//            AlertDialog a = new AlertDialog.Builder(this).setTitle("確定退出?")
//                    .setPositiveButton("確定", (dialog1, num1) -> {
//                        finish();
//                    })
//                    .setNegativeButton("取消", (dialog1, num1) -> {
//                        return;
//                    }).create();
//            a.setOnShowListener(dialog12 -> {
//                Button positiveButton = a.getButton(AlertDialog.BUTTON_POSITIVE);
//                positiveButton.setTextColor(Color.DKGRAY);
//                Button negativeButton = a.getButton(AlertDialog.BUTTON_NEGATIVE);
//                negativeButton.setTextColor(Color.DKGRAY);
//            });
//            a.show();
//        }
//
//    }
}
