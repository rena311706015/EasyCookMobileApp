package com.example.android.customerapp;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLOutput;

public class VideoPlayerActivity extends AppCompatActivity {
        private VideoView videoview;
        private MediaController mediaController;
        @SuppressLint("SourceLockedOrientationActivity")
        protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態列
                requestWindowFeature(Window.FEATURE_NO_TITLE);//隱藏標題列
                setContentView(R.layout.activity_videoplayer);
                getSupportActionBar().hide(); //隱藏標題列
                Intent intent = getIntent(); /* 取得傳入的 Intent 物件 */
                Bundle bundle = intent.getExtras();
                String url= bundle.getString("url");
                videoview=findViewById(R.id.videoview);
                mediaController = new MediaController(this);
                videoview.setMediaController(mediaController);
                loadView(url);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//強制橫屏
        }
        public void loadView(String path){
                Uri uri = Uri.parse(path);
                videoview.setVideoURI(uri);
                videoview.start();
        }
}