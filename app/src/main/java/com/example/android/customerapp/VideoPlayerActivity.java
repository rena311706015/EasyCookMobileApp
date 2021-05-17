package com.example.android.customerapp;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.android.customerapp.models.ExtendedTimeBar;
import com.example.android.customerapp.models.LodingDialog;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.models.RecipeStep;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.PendingIntent.FLAG_NO_CREATE;

public class VideoPlayerActivity extends AppCompatActivity {

    private TextView stepText, hintText;
    private ImageView speakImage;
    private ScaleAnimation animation;
    /* exoplayer */
    private PlayerView recipeVideoView;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private Recipe mRecipe;
    private int index;  //目前在第幾步
    private long currentTime = 0;
    private ArrayList<RecipeStep> stepList;

    private SpeechRecognizer recognizer;
    private TextToSpeech textToSpeech;
    private LodingDialog lodingDialog;
    private Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    final Handler handler = new Handler();
    private boolean isSpeechRecognizerAlive = false;

    @SuppressLint("SourceLockedOrientationActivity")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態列
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隱藏標題列
        getSupportActionBar().hide(); //隱藏標題列
        setContentView(R.layout.activity_videoplayer);
        recipeVideoView = findViewById(R.id.video_view);
        speakImage = findViewById(R.id.speak_image);
        stepText = findViewById(R.id.step_text);
        hintText = findViewById(R.id.hint_text);

        index = 0;

        //取得傳入的 Intent 物件
        Intent recipeIntent = getIntent();
        mRecipe = (Recipe) recipeIntent.getSerializableExtra("recipe");

        stepList = new ArrayList<>();
        for (RecipeStep steps : mRecipe.getRecipeSteps()) {
            stepList.add(steps);
        }

        setTextToSpeech();
        setAnime();

        lodingDialog = new LodingDialog(this);
        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(new MyRecognizerListener());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//強制橫屏
        lodingDialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        Uri uri = Uri.parse(mRecipe.getLink());
        player = new SimpleExoPlayer.Builder(this).build();
        recipeVideoView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(uri);
        ExtendedTimeBar timeBar = new ExtendedTimeBar(this);
        timeBar.setEnabled(false);
        player.setMediaItem(mediaItem);
    }

    @Override
    public void onResume() {
        super.onResume();

        player.addListener(new Player.EventListener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (isPlaying) {
                    speakImage.clearAnimation();
                    recipeVideoView.postDelayed(this::getCurrentPlayerPosition, 500);
                } else {
                    Log.e("VIDEO", "OnPause");
                    if (stepList.get(index).getTimer() != 0) {
                        hintText.setText("請說「開始計時」");
                    } else {
                        hintText.setText("請說「重播」/「上/下一步」");
                    }
                    stepText.setText(stepList.get(index).getNote());
                    textToSpeech.speak(stepList.get(index).getNote(), TextToSpeech.QUEUE_ADD, null);
                    speakImage.setAnimation(animation);
                    setVisible();
                    recognizer.startListening(intent);
                }
            }

            private void getCurrentPlayerPosition() {
                currentTime = player.getCurrentPosition() / 1000 * 1000;
                if (currentTime >= stepList.get(index).getStartTime())
                    player.pause();
                if (player.isPlaying()) {
                    recipeVideoView.postDelayed(this::getCurrentPlayerPosition, 500);
                }
            }
        });
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare();
        player.play();
        lodingDialog.dismiss();
    }


    @Override
    public void onPause() {
        super.onPause();
        recognizer.stopListening();
        recognizer.cancel();
//                setGone();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
//            player = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (recognizer != null) {
            recognizer.destroy();
        }
    }

    public void setTextToSpeech() {
        if (textToSpeech == null) {
            textToSpeech = new TextToSpeech(this, arg0 -> {
                if (arg0 == TextToSpeech.SUCCESS) {
                    if (textToSpeech.isLanguageAvailable(Locale.CHINESE) == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                        textToSpeech.setLanguage(Locale.CHINESE);
                    }
                }
            });
        }
    }

    public void setAnime() {
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

    public void setGone() {
        speakImage.clearAnimation();
        speakImage.setVisibility(View.GONE);
        stepText.setVisibility(View.GONE);
        hintText.setVisibility(View.GONE);
    }

    public void setVisible() {
        speakImage.startAnimation(animation);
        speakImage.setVisibility(View.VISIBLE);
        stepText.setVisibility(View.VISIBLE);
        hintText.setVisibility(View.VISIBLE);
    }

    public void sendNotification() {
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
            for (String res : resList) {
                sb.append(res);
                break;
            }
            Log.e("RECOGNIZER", sb.toString());
            //TODO 計時要可以在背景計，然後繼續下一步
            if (stepList.get(index).getTimer() != 0 && sb.toString().contains("開始計時")) {
                recognizer.cancel();
                CountDownTimer timer = new CountDownTimer(stepList.get(index).getTimer(), 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        stepText.setText("還剩" + (millisUntilFinished / 1000) + "秒");
                    }

                    @Override
                    public void onFinish() {
                        Log.e("VIDEO", "onFinish");
                        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 500);
                        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 5000);
                        sendNotification();
                        stepText.setText("時間到");
                        hintText.setText("請說「下一步」以繼續導覽");
                        recognizer.startListening(intent);
                    }
                };
                timer.start();

            } else if (sb.toString().contains("上一步")) {
                index -= 1;
                recognizer.cancel();
                setGone();
                if (index - 1 < 0) {
                    player.seekTo(0);
                } else {
                    player.seekTo(stepList.get(index - 1).getStartTime());
                }
                currentTime = stepList.get(index).getStartTime();
                handler.postDelayed(() -> player.play(), 500);
            } else if (sb.toString().contains("下一步")) {
                index += 1;
                recognizer.cancel();
                setGone();
                handler.postDelayed(() -> player.play(), 500);
            } else if (sb.toString().contains("重播")) {
                recognizer.cancel();
                setGone();
                player.seekTo(stepList.get(index - 1).getStartTime());
                handler.postDelayed(() -> player.play(), 500);
            } else {
                recognizer.startListening(intent);
            }

        }

        @Override
        public void onError(int error) {
                        /*error 7 -> no match
                          error 8 -> busy*/
//                        Log.d("RECOGNIZER","ERROR"+error);
            if (error == 8) {  //不可以startListening，否則會產生更多錯誤，因此直接return
                if (!isSpeechRecognizerAlive) {
                    recognizer.startListening(intent);
                } else {
                    recognizer.cancel();
                    return;
                }
            } else {    /*no match 使recognizer停留在stoplistening的狀態，因此需重新startListening
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
        }

        @Override
        public void onRmsChanged(float rmsdB) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {
            recognizer.cancel();
            recognizer.startListening(intent);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }

    }
}




