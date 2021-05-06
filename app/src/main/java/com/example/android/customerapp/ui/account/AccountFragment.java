package com.example.android.customerapp.ui.account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.android.customerapp.R;
import com.example.android.customerapp.VideoPlayerActivity;
import com.example.android.customerapp.viewmodels.AccountViewModel;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private Button orderButton, loginButton, logoutButton;
    private TextView welcomeText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        orderButton = root.findViewById(R.id.go_order);
        loginButton = root.findViewById(R.id.login);
        logoutButton =  root.findViewById(R.id.logout);
        welcomeText = root.findViewById(R.id.welcome_message);

        if(readToken()==null){
            loginButton.setVisibility(View.VISIBLE);
            orderButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.GONE);
            welcomeText.setText("請先登入");
        }else{
            loginButton.setVisibility(View.GONE);
            orderButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.VISIBLE);
            welcomeText.setText("歡迎回來!");
        }

        orderButton.setOnClickListener(view ->{
            Navigation.findNavController(view).navigate(R.id.action_navigation_account_to_navigation_all_order);
        });

        loginButton.setOnClickListener(view ->{
            Navigation.findNavController(view).navigate(R.id.action_navigation_account_to_navigation_login);
        });

        logoutButton.setOnClickListener(view ->{
            AlertDialog.Builder a = new AlertDialog.Builder(getContext());
            a.setTitle("確定要登出嗎?");

            a.setPositiveButton("確定", (dialog, num1) -> {
                clearToken();
                Navigation.findNavController(view).navigate(R.id.action_navigation_account_to_navigation_login);
            });
            a.setNegativeButton("取消", (dialog, num1) -> {
                dialog.cancel();
            });
            a.show();
        });

        return root;
    }
    private String readToken(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Token",null);
    }
    private void clearToken(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
