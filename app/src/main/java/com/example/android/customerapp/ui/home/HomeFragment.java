package com.example.android.customerapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
import com.example.android.customerapp.RecipeVideoView;
import com.example.android.customerapp.ServiceAdapter;
import com.example.android.customerapp.ServiceBot;
import com.example.android.customerapp.VideoPlayerActivity;
import com.google.firebase.firestore.util.Util;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private SearchView searchView;
    private ImageView toVideoPlayer;
    ArrayList<Integer> timeList;
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
        ImageView toVideoPlayer = (ImageView) root.findViewById(R.id.button1);

        timeList=new ArrayList<>();
        setFakeData();
//                "http://www.youtube.com/watch?v=_B24na6mhNM&ab_channel=%E8%A9%B9%E5%A7%86%E6%96%AF%E5%8E%A8%E6%88%BFJames%E2%80%99skitchen"
//        final String youtubeUrl=RecipeVideoView.getUrlVideoRTSP("http://www.youtube.com/watch?v=1FJHYqE0RDg");
//        Log.e("Video url for playing=========>>>>>", youtubeUrl);
        final String youtubeUrl="https://firebasestorage.googleapis.com/v0/b/servicebotproject.appspot.com/o/R003.mp4?alt=media&token=d46a20b0-18d9-45e0-8e8c-5ef63020aae9";

        toVideoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), VideoPlayerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",youtubeUrl);
                bundle.putIntegerArrayList("time",timeList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return root;
    }
    public void setFakeData(){
        timeList.add(5);
        timeList.add(10);
        timeList.add(15);
        timeList.add(20);
    }
}
