package com.example.android.customerapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.R;
import com.example.android.customerapp.ServiceAdapter;
import com.example.android.customerapp.ServiceBot;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private SearchView searchView;
    private ImageView toBot;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        searchView=(SearchView)root.findViewById(R.id.searchview);
        searchView.bringToFront();
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setQueryHint("請輸入關鍵字");


        ImageView toBot = (ImageView) root.findViewById(R.id.button1);
        toBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext() , ServiceBot.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
