package com.example.android.customerapp;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class VideoPlayerActivity extends AppCompatActivity{
        private VideoView videoview;
        private MediaController mediaController;
        private SpeechRecognizer recognizer;
        private ArrayList<Integer> timeList;
        private int index;
        private Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);;
        final Handler handler = new Handler();

        @SuppressLint("SourceLockedOrientationActivity")
        protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態列
                requestWindowFeature(Window.FEATURE_NO_TITLE);//隱藏標題列
                setContentView(R.layout.activity_videoplayer);
                getSupportActionBar().hide(); //隱藏標題列

                Intent intent1 = getIntent(); /* 取得傳入的 Intent 物件 */
                Bundle bundle = intent1.getExtras();
                String url= bundle.getString("url");

                timeList= bundle.getIntegerArrayList("time");
                videoview=findViewById(R.id.videoview);
                mediaController = new MediaController(VideoPlayerActivity.this);
                videoview.setMediaController(mediaController);
                displaySpeechRecognizer();
                loadView(url);

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//強制橫屏
        }
        public void displaySpeechRecognizer(){
                Log.e("here","invoke");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                recognizer = SpeechRecognizer.createSpeechRecognizer(this);
                recognizer.setRecognitionListener(new MyRecognizerListener());
                recognizer.startListening(intent);
                Log.e("started"," recognizer");
//                startActivityForResult(intent, SPEECH_REQUEST_CODE);
        }
        public void loadView(String path){
                index=0;
                Uri uri = Uri.parse(path);
                videoview.setVideoURI(uri);
                videoview.start();

                videoview.setOnTouchListener(new View.OnTouchListener()
                {
                        @Override
                        public boolean onTouch(View v, MotionEvent motionEvent)
                        {
                        if(timeList.size()>index){

                                try {
//                                        Log.e("stop",String.valueOf(timeList.get(index)));
                                        videoview.seekTo(timeList.get(index));
                                        index+=1;
                                        Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                        }
                        return false;   //傳true會造成touch重複執行

                        }
                });
                displaySpeechRecognizer();
        }

        private class MyRecognizerListener implements RecognitionListener {

                @Override
                public void onResults(Bundle results) {
                        List<String> resList = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                        StringBuffer sb = new StringBuffer();
                        for(String res: resList) {
                                sb.append(res);
                                break;
                        }
                        Log.d("RECOGNIZER", sb.toString());
                        if(sb.toString().equals("下一步")){
                                Log.d("RECOGNIZER", "那我就下一步");
                        }
                        Log.d("RECOGNIZER", "onResults: " + sb.toString());
                }

                @Override
                public void onError(int error) {
                        Log.d("RECOGNIZER", "Error Code: " + error);
                }

                @Override
                public void onReadyForSpeech(Bundle params) {
                        Log.d("RECOGNIZER", "ready");
                }

                @Override
                public void onBeginningOfSpeech() {
                        Log.d("RECOGNIZER", "beginning");
                }

                @Override
                public void onRmsChanged(float rmsdB) {
                }

                @Override
                public void onBufferReceived(byte[] buffer) {
                        Log.d("RECOGNIZER", "onBufferReceived");
                }

                @Override
                public void onEndOfSpeech() {

                        Log.d("RECOGNIZER", "onEndOfSpeech");
                        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                        // Do something after 5s = 5000ms

                                        Log.d("RECOGNIZER","done");
                                        recognizer.startListening(intent);
                                }
                        }, 1000);
                }

                @Override
                public void onPartialResults(Bundle partialResults) {
                        Log.d("RECOGNIZER", "onPartialResults" + partialResults.toString());
                }

                @Override
                public void onEvent(int eventType, Bundle params) {
                        Log.d("RECOGNIZER", "onPartialResults" + params.toString());
                }

        }

}