package com.example.android.customerapp.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.android.customerapp.R;
import com.example.android.customerapp.viewmodels.AccountViewModel;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private Button goLoginButton,goRegisterButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        goLoginButton=root.findViewById(R.id.goLogin);
        goLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_account_to_navigation_login);
            }
        });
        goRegisterButton=root.findViewById(R.id.goRegister);
        goRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_account_to_navigation_register);
            }
        });
        return root;
    }
}
