package com.example.android.customerapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.customerapp.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private SearchView mSearchView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mSearchView=root.findViewById(R.id.searchview);
        mSearchView.bringToFront();
        mSearchView.setIconified(false);
        mSearchView.setQueryHint("請輸入關鍵字");
        return root;
    }
}
