package com.example.android.customerapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.models.RecipeStep;
import com.example.android.customerapp.models.RecipeVideoView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class VideoPlayerActivity extends AppCompatActivity{

        private TextView stepText;
        private ImageView speakImage;
        private ScaleAnimation animation;
        private RecipeVideoView recipeVideoView;
        private MediaController mediaController;
        private ArrayList<RecipeStep> stepList;
        private int index=0,countdown=0;
        private SpeechRecognizer recognizer;
        private Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        final Handler handler = new Handler();
        private boolean isSpeechRecognizerAlive = false;
        private boolean isVisible=false;
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
                stepText=findViewById(R.id.step_text);
                //取得傳入的 Intent 物件
                Intent recipeIntent = getIntent();
                Recipe recipe = (Recipe) recipeIntent.getSerializableExtra("recipe");
                stepList=new ArrayList<>();
                for(RecipeStep steps : recipe.getRecipeSteps()){
                        stepList.add(steps);
                }
//                stepList.add(0,new RecipeStep(30,5,"第5秒(請說開始計時)",10));
//                stepList.add(1,new RecipeStep(30,10,"第10秒"));
//                stepList.add(2,new RecipeStep(30,15,"第15秒"));
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
                mediaController = new MediaController(VideoPlayerActivity.this);
                recipeVideoView.setMediaController(mediaController);
                mediaController.setMediaPlayer(recipeVideoView);     //MediaPlayer與MediaController互相連接
                recipeVideoView.setPlayPauseListener(new RecipeVideoView.PlayPauseListener() {
                        public void onPlay() {
                                while(recipeVideoView.isPlaying()){
                                        int currentTime=recipeVideoView.getCurrentPosition();
                                        if(currentTime/1000==stepList.get(index).getStartTime()){
                                                recipeVideoView.pause();
                                        }
                                }
                        }

                        public void onPause() {
                                stepText.setText(stepList.get(index).getNote());
                                speakImage.setAnimation(animation);
                                changeVisibility();
                                recognizer.startListening(intent);
                        }
                });

                loadView(recipe.getLink());
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
                        if(stepList.get(index).getTimer()!=0 && sb.toString().equals("開始計時")) {
                                recognizer.cancel();
                                CountDownTimer timer = new CountDownTimer(stepList.get(index).getTimer()*1000, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                                stepText.setText((millisUntilFinished / 1000)+"second");
                                        }
                                        @Override
                                        public void onFinish() {
                                                Log.e("VIDEO","onFinish");
                                                sendNotification();

                                                stepText.setText("請說下一步以繼續導覽");
                                                recognizer.startListening(intent);
                                        }
                                };
                                timer.start();

                        }else if(sb.toString().equals("下一步")){
                                index+=1;
                                recognizer.cancel();
                                changeVisibility();
                                handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                                recipeVideoView.start();
                                        }
                                },500);
                        }else if(sb.toString().equals("上一步")){
                                recognizer.cancel();
                                changeVisibility();
                                recipeVideoView.seekTo(stepList.get(index).getStartTime());
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
                        Log.e("RECOGNIZER","ready");
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
//
//                        handler.postDelayed(() -> {
//                                }, 500);
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
        public void changeVisibility(){
                if(isVisible==true){
                        isVisible=false;
                        speakImage.setVisibility(View.GONE);
                        stepText.setVisibility(View.INVISIBLE);
                }else{
                        isVisible=true;
                        speakImage.setVisibility(View.VISIBLE);
                        stepText.setVisibility(View.VISIBLE);
                }
        }
        public void sendNotification(){
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "cook")
//                        .setSmallIcon(R.drawable.icon)
//                        .setContentTitle("My notification")
//                        .setContentText("Much longer text that cannot fit one line...")
//                        .setStyle(new NotificationCompat.BigTextStyle()
//                                .bigText("Much longer text that cannot fit one line..."))
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        CharSequence name = getString(R.string.channel_name);
                        int importance = NotificationManager.IMPORTANCE_DEFAULT;
                        NotificationChannel channel = new NotificationChannel("cook", name, importance);
                        channel.setDescription("description");
                        // Register the channel with the system; you can't change the importance
                        // or other notification behaviors after this
                        NotificationManager notificationManager = getSystemService(NotificationManager.class);
                        notificationManager.createNotificationChannel(channel);
                }
                Intent intent = new Intent(this, VideoPlayerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "cook")
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
                notificationManager.notify(1, builder.build());
        }
}




