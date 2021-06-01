package com.example.android.customerapp.ui.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.android.customerapp.R;
import com.example.android.customerapp.models.Member;
import com.example.android.customerapp.viewmodels.LoginViewModel;

import java.security.NoSuchAlgorithmException;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private Button loginButton;
    private EditText account, password;
    private TextView goRegister;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                ViewModelProviders.of(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        account = root.findViewById(R.id.login_account);
        password = root.findViewById(R.id.login_password);
        loginButton = root.findViewById(R.id.login);

        loginButton.setOnClickListener(view -> {
            final Member member = new Member(account.getText().toString(), password.getText().toString());
            try {
                loginViewModel.memberLogin(member);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            loginViewModel.token.observe(getViewLifecycleOwner(), token -> {
                if (token != "fail") {
                    saveToken(token);
                    Toast.makeText(getContext(), "登入成功", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getView()).navigate(R.id.action_navigation_login_to_navigation_account);
                } else {
                    Toast.makeText(getContext(), "登入失敗", Toast.LENGTH_SHORT).show();
                }
            });

        });

        goRegister = root.findViewById(R.id.go_register);
        goRegister.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_navigation_login_to_navigation_register));
        return root;

    }

    private boolean saveToken(String token) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Token", token);
        return editor.commit();
    }
}