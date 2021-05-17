package com.example.android.customerapp.ui.account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.example.android.customerapp.R;
import com.example.android.customerapp.viewmodels.AccountViewModel;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private ImageView btnOrder, btnCoupon, btnInfo, btnLogout;
    private TextView btnLogin, welcomeText, btnOrderText, btnCouponText, btnInfoText, btnLogoutText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        welcomeText = root.findViewById(R.id.welcome_message);
        btnLogin = root.findViewById(R.id.btn_login);
        btnOrder = root.findViewById(R.id.btn_order);
        btnCoupon = root.findViewById(R.id.btn_coupon);
        btnInfo = root.findViewById(R.id.btn_info);
        btnLogout = root.findViewById(R.id.btn_logout);

        btnOrderText = root.findViewById(R.id.btn_order_text);
        btnCouponText = root.findViewById(R.id.btn_coupon_text);
        btnInfoText = root.findViewById(R.id.btn_info_text);
        btnLogoutText = root.findViewById(R.id.btn_logout_text);

        setIconVisibility();

        btnCoupon.setOnClickListener(v -> {
            Toast.makeText(getContext(), "尚未啟用", Toast.LENGTH_SHORT).show();
        });

        btnInfo.setOnClickListener(v -> {
            Toast.makeText(getContext(), "尚未啟用", Toast.LENGTH_SHORT).show();
        });

        btnOrder.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_navigation_account_to_navigation_all_order);
        });

        btnLogin.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_navigation_account_to_navigation_login);
        });

        btnLogout.setOnClickListener(view -> {
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

    private String readToken() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Token", null);
    }

    private void clearToken() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    private void setIconVisibility(){
        if (readToken() == null) {

            btnLogin.setVisibility(View.VISIBLE);
            welcomeText.setVisibility(View.GONE);
            btnOrder.setVisibility(View.GONE);
            btnCoupon.setVisibility(View.GONE);
            btnInfo.setVisibility(View.GONE);
            btnLogout.setVisibility(View.GONE);
            btnOrderText.setVisibility(View.GONE);
            btnCouponText.setVisibility(View.GONE);
            btnInfoText.setVisibility(View.GONE);
            btnLogoutText.setVisibility(View.GONE);
        } else {
            btnLogin.setVisibility(View.GONE);
            welcomeText.setVisibility(View.VISIBLE);
            btnOrder.setVisibility(View.VISIBLE);
            btnCoupon.setVisibility(View.VISIBLE);
            btnInfo.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.VISIBLE);
            btnOrderText.setVisibility(View.VISIBLE);
            btnCouponText.setVisibility(View.VISIBLE);
            btnInfoText.setVisibility(View.VISIBLE);
            btnLogoutText.setVisibility(View.VISIBLE);
        }
    }
}
