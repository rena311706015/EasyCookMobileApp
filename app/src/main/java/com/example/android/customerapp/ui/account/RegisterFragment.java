package com.example.android.customerapp.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.android.customerapp.R;
import com.example.android.customerapp.models.Member;
import com.example.android.customerapp.viewmodels.RegisterViewModel;

import java.security.NoSuchAlgorithmException;

public class RegisterFragment extends Fragment {

    private RegisterViewModel registerViewModel;
    private Button registerButton;
    private EditText account, password, phone, email, username;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registerViewModel =
                ViewModelProviders.of(this).get(RegisterViewModel.class);
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        account = root.findViewById(R.id.registerAccount);
        password = root.findViewById(R.id.registerPassword);
        phone = root.findViewById(R.id.registerPhone);
        email = root.findViewById(R.id.registerEmail);
        username = root.findViewById(R.id.registerName);
        registerButton = root.findViewById(R.id.register);

        registerButton.setOnClickListener(view -> {
            final Member member = new Member(account.getText().toString(), password.getText().toString(), phone.getText().toString(), email.getText().toString(), username.getText().toString());
            try {
                registerViewModel.memberRegister(member);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            registerViewModel.mMember.observe(getViewLifecycleOwner(), member1 -> {
                Navigation.findNavController(view).navigate(R.id.action_navigation_register_to_navigation_login);
            });
        });
        return root;

    }

}