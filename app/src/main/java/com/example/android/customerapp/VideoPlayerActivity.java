package com.example.android.customerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Ringtone;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.MediaController;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.customerapp.models.RecipeVideoView;

import java.util.ArrayList;
import java.util.List;

public class VideoPlayerActivity extends AppCompatActivity{

        private ImageView speakImage;
        private ScaleAnimation animation;
        private Ringtone rt;
        private RecipeVideoView recipeVideoView;
        private MediaController mediaController;
        private ArrayList<Integer> timeList;
        private int index;
        private SpeechRecognizer recognizer;
        private Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        final Handler handler = new Handler();
        private boolean isSpeechRecognizerAlive = false;
        private String activationKeyword = "嘿";

        @SuppressLint("SourceLockedOrientationActivity")
        protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態列
                requestWindowFeature(Window.FEATURE_NO_TITLE);//隱藏標題列
                getSupportActionBar().hide(); //隱藏標題列
                setContentView(R.layout.activity_videoplayer);
                recipeVideoView=findViewById(R.id.videoview);
                speakImage=findViewById(R.id.speak_image);
                //取得傳入的 Intent 物件
                Intent intent1 = getIntent();
                Bundle bundle = intent1.getExtras();
                String url= bundle.getString("url");
                timeList= bundle.getIntegerArrayList("time");
                //設定recognizer
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
                intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,5000);
                intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 5000);

                recognizer = SpeechRecognizer.createSpeechRecognizer(this);
                recognizer.setRecognitionListener(new MyRecognizerListener());
                //設定動畫
                setAnime();
                //設定影片控制器
                index=0;
                mediaController = new MediaController(VideoPlayerActivity.this);
                recipeVideoView.setMediaController(mediaController);
                mediaController.setMediaPlayer(recipeVideoView);     //MediaPlayer與MediaController互相連接
                recipeVideoView.setPlayPauseListener(new RecipeVideoView.PlayPauseListener() {
                        public void onPlay() {
                                speakImage.setVisibility(View.GONE);
                                while(recipeVideoView.isPlaying()){

                                        int currentTime=recipeVideoView.getCurrentPosition();
                                        if(currentTime/1000==timeList.get(index)){
                                                recipeVideoView.pause();
                                        }
                                }
                        }

                        public void onPause() {
                                speakImage.setVisibility(View.VISIBLE);
                                recognizer.startListening(intent);
                        }
                });

                loadView(url);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//強制橫屏
        }
        public void loadView(String path){
                index=0;
                Uri uri = Uri.parse(path);
                recipeVideoView.setVideoURI(uri);
                recipeVideoView.requestFocus();
                recipeVideoView.start();
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
//                                Toast.makeText(VideoPlayerActivity.this, "下一步", Toast.LENGTH_LONG).show();
                                index+=1;
                                recognizer.cancel();
                                recipeVideoView.start();
                        }else if(sb.toString().equals("上一步")){
                                recognizer.cancel();
                                recipeVideoView.seekTo(timeList.get(index));
                                recipeVideoView.start();
                        }
                }

                @Override
                public void onError(int error) {
                        /*error 7 -> no match
                          error 8 -> busy*/
                        Log.d("RECOGNIZER","ERROR"+error);
                        if(error==8){  //不可以startListening，否則會產生更多錯誤，因此直接return
                                if(!isSpeechRecognizerAlive){
                                        recognizer.startListening(intent);
                                }else{
                                        recognizer.cancel();
                                        return;
                                }
                        }else{    /*no match 使recognizer停留在stoplistening的狀態，因此需重新startListening
                                    但仍須先cancel，否則又會產生error:code 8*/
                                recognizer.cancel();
                                recognizer.startListening(intent);
                        }


                }

                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {
                        Log.d("RECOGNIZER","beginning");
                        isSpeechRecognizerAlive = true;
                        speakImage.startAnimation(animation);
                }

                @Override
                public void onRmsChanged(float rmsdB) {
                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {
                        Log.d("RECOGNIZER","end");
//                        recognizer.cancel();
//                        recognizer.startListening(intent);
                        handler.postDelayed(() -> {
                                recognizer.cancel();
                                recognizer.startListening(intent);
                        }, 500);
                }

                @Override
                public void onPartialResults(Bundle partialResults) {
//
//                        Log.d("RECOGNIZER","partialResults");
//                        List<String> resList = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//                        if(resList!=null){
//                                StringBuffer sb = new StringBuffer();
//                                for(String res: resList) {
//                                        sb.append(res);
//                                        break;
//                                }
//                                Log.d("RECOGNIZER", sb.toString());
//                        }

                }

                @Override
                public void onEvent(int eventType, Bundle params) {
                }

        }
        @Override
        public void onDestroy() {
                Log.d("RECOGNIZER","destroy");
                super.onDestroy();
                if (recognizer != null) {
                        recognizer.destroy();
                }
        }
        public void setAnime(){
                animation = new ScaleAnimation(
                        1.0f,
                        1.2f,
                        1.0f,
                        1.2f,
                        Animation.RELATIVE_TO_SELF, 0.5f,  //設定起始點，X、Y皆設0.5代表從圖的中心開始
                        Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(500);
                animation.setRepeatMode(Animation.REVERSE);
        }
}

