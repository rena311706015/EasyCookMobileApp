package com.example.android.customerapp;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.android.customerapp.models.LodingDialog;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.models.RecipeStep;
import com.example.android.customerapp.models.RecipeVideoView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.FLAG_NO_CREATE;
import static android.media.AudioManager.FLAG_SHOW_UI;

public class VideoPlayerActivity extends AppCompatActivity{

        private TextView stepText, hintText;
        private ImageView speakImage;
        private ScaleAnimation animation;
        private RecipeVideoView recipeVideoView;
        private MediaController mMediaController;
        private AudioManager mAudioManager;
        private Recipe mRecipe;
        private int currentTime;  //目前在第幾秒
        private int index;  //目前在第幾步

        private ArrayList<RecipeStep> stepList;
        private SpeechRecognizer recognizer;
        private LodingDialog lodingDialog;
        private Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        final Handler handler = new Handler();
        private boolean isSpeechRecognizerAlive = false;

        @SuppressLint("SourceLockedOrientationActivity")
        protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態列
                requestWindowFeature(Window.FEATURE_NO_TITLE);//隱藏標題列
                getSupportActionBar().hide(); //隱藏標題列
                setContentView(R.layout.activity_videoplayer);
                recipeVideoView=findViewById(R.id.videoview);
                speakImage=findViewById(R.id.speak_image);
                stepText=findViewById(R.id.step_text);
                hintText=findViewById(R.id.hint_text);

                currentTime = 0;
                index = 0;

                //取得傳入的 Intent 物件
                Intent recipeIntent = getIntent();
                mRecipe = (Recipe) recipeIntent.getSerializableExtra("recipe");

                stepList=new ArrayList<>();
                for(RecipeStep steps : mRecipe.getRecipeSteps()){
                        stepList.add(steps);
                }


                //設定動畫
                setAnime();

                lodingDialog = new LodingDialog(this);
                //設定recognizer
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
                intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,5000);
                intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 5000);
                recognizer = SpeechRecognizer.createSpeechRecognizer(this);
                recognizer.setRecognitionListener(new MyRecognizerListener());

                //設定音量控制器

                mAudioManager =(AudioManager) this.getSystemService(Service.AUDIO_SERVICE);
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,10,FLAG_SHOW_UI);
                mMediaController = new MediaController(this);
                //設定影片控制器
                recipeVideoView.setMediaController(mMediaController);
                mMediaController.setMediaPlayer(recipeVideoView);     //MediaPlayer與MediaController互相連接
//                mMediaController.setVisibility(View.GONE);         //隱藏進度條
                recipeVideoView.setPlayPauseListener(new RecipeVideoView.PlayPauseListener() {
                        public void onPlay() {
                                while(recipeVideoView.isPlaying()){
                                        currentTime=recipeVideoView.getCurrentPosition();
                                        if(currentTime==stepList.get(index).getStartTime()){
                                                recipeVideoView.pause();
                                        }
                                }
                        }

                        public void onPause() {
                                //STOP ACTIVITY時這裡會先執行
                                currentTime=recipeVideoView.getCurrentPosition();
                                if(stepList.get(index).getTimer()!=0){
                                        hintText.setText("請說「開始計時」");
                                }else{
                                        hintText.setText("請說「重播」/「上/下一步」");
                                }
                                stepText.setText(stepList.get(index).getNote());
                                speakImage.setAnimation(animation);
                                setVisible();
                                recognizer.startListening(intent);
                        }
                });

                Uri uri = Uri.parse(mRecipe.getLink());
                recipeVideoView.setVideoURI(uri);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//強制橫屏


        }
        @Override
        public void onResume(){
                super.onResume();
                Log.e("LifeCycle", "onResume");
//                Log.e("CurrentTime","  "+currentTime);
                recipeVideoView.start();
//                recipeVideoView.seekTo(currentTime);
////                recipeVideoView.requestFocus();

        }

        @Override
        public void onPause() {
                super.onPause();
                recipeVideoView.pause();
                recipeVideoView.clearFocus();
                Log.e("LifeCycle","onPause");
//                Log.e("CurrentTime","  "+currentTime);
//                recognizer.stopListening();
//                recognizer.cancel();
//                setGone();
        }

        @Override
        public void onDestroy() {
                Log.e("LifeCycle","onDestroy");
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
        public void setGone(){
                speakImage.setVisibility(View.GONE);
                stepText.setVisibility(View.GONE);
                hintText.setVisibility(View.GONE);
        }
        public void setVisible(){
                speakImage.setVisibility(View.VISIBLE);
                stepText.setVisibility(View.VISIBLE);
                hintText.setVisibility(View.VISIBLE);
        }
        public void sendNotification(){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        CharSequence name = getString(R.string.channel_name);
                        int importance = NotificationManager.IMPORTANCE_DEFAULT;
                        NotificationChannel channel = new NotificationChannel("cook", name, importance);
                        channel.setDescription("description");
                        NotificationManager notificationManager = getSystemService(NotificationManager.class);
                        notificationManager.createNotificationChannel(channel);
                }
                Intent intent = new Intent(this, VideoPlayerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_NO_CREATE);

//                intent.setAction(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_LAUNCHER);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "cook")
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("時間到")
                        .setContentText("請回到影片導覽以繼續教學")
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                notificationManager.notify(1, builder.build());
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
                        Log.e("RECOGNIZER", sb.toString());
                        if(stepList.get(index).getTimer()!=0 && sb.toString().equals("開始計時")) {
                                recognizer.cancel();
                                CountDownTimer timer = new CountDownTimer(stepList.get(index).getTimer(), 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                                stepText.setText("還剩"+(millisUntilFinished / 1000)+"秒");
                                        }
                                        @Override
                                        public void onFinish() {
                                                Log.e("VIDEO","onFinish");
                                                sendNotification();
                                                stepText.setText("時間到");
                                                hintText.setText("請說「下一步」以繼續導覽");
                                                recognizer.startListening(intent);
                                        }
                                };
                                timer.start();

                        }else if(sb.toString().equals("上一步")){
                                index-=1;
                                recognizer.cancel();
                                setGone();
                                Log.e("index","="+index);
                                Log.e("seekTo","="+stepList.get(index).getStartTime());
                                Log.e("current","="+currentTime);
                                if(index-1<0){
                                        recipeVideoView.seekTo(0);
                                }else{
                                        recipeVideoView.seekTo(stepList.get(index-1).getStartTime());
                                }
                                currentTime=stepList.get(index).getStartTime();
                                handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                                recipeVideoView.start();
                                        }
                                },500);
                        }else if(sb.toString().equals("下一步")){
                                index+=1;
                                recognizer.cancel();
                                setGone();
                                handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                                recipeVideoView.start();
                                        }
                                },500);
                        }else if(sb.toString().equals("重播")){
                                recognizer.cancel();
                                setGone();
                                Log.e("index","="+index);
                                Log.e("seekTo","="+stepList.get(index).getStartTime());
                                Log.e("current","="+currentTime);
                                recipeVideoView.seekTo(stepList.get(index-1).getStartTime());
                                handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                                recipeVideoView.start();
                                        }
                                },500);
                        }else{
                                recognizer.startListening(intent);
                        }

                }

                @Override
                public void onError(int error) {
                        /*error 7 -> no match
                          error 8 -> busy*/
//                        Log.d("RECOGNIZER","ERROR"+error);
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
//                        Log.e("RECOGNIZER","ready");
                }

                @Override
                public void onBeginningOfSpeech() {
                        Log.e("RECOGNIZER","beginning");
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
                        speakImage.clearAnimation();
                        recognizer.cancel();
                        recognizer.startListening(intent);
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
}




