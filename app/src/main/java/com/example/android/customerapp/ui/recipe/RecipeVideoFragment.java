package com.example.android.customerapp.ui.recipe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.customerapp.MainActivity;
import com.example.android.customerapp.R;
import com.example.android.customerapp.VideoPlayerActivity;
import com.example.android.customerapp.adapters.OnStepListener;
import com.example.android.customerapp.adapters.StepAdapter;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.models.RecipeStep;
import com.example.android.customerapp.viewmodels.RecipeViewModel;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeVideoFragment extends Fragment implements OnStepListener {

    private RecipeViewModel mRecipeViewModel;
    private RecyclerView mStepRecyclerView;
    private StepAdapter mStepAdapter;
    private Button videoButton;
    private Recipe mRecipe;
    private List<RecipeStep> steps;
    private PlayerView recipeVideoView;
    private SimpleExoPlayer player;
    private int currentWindow = 0;
    private long playbackPosition = 0;


    public RecipeVideoFragment(Recipe recipe) {
        mRecipe = recipe;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recipe_video, container, false);
        recipeVideoView = root.findViewById(R.id.recipe_video);
        videoButton = root.findViewById(R.id.video_button);
        mStepRecyclerView = root.findViewById(R.id.recipe_steps_list);

        steps = new ArrayList<>();

        videoButton.setOnClickListener(v -> {
            goVideoPlayer();
        });
//        RecipeStep[] stepArray = mRecipe.getRecipeSteps();
//        List list = Arrays.asList(stepArray);
//        steps = new ArrayList(list);
////        for(RecipeStep step : mRecipe.getRecipeSteps()){
////            if(step.getTimer()!=0)steps.remove(step);
////        }
////        for(int i=0;i<steps.size()-1;i++){
////            steps.get(i).setNote(steps.get(i+1).getNote());
////        }
        //TODO step跑掉了
        mStepAdapter = new StepAdapter(getContext(), this, mRecipe.getRecipeSteps());
        mStepRecyclerView.setAdapter(mStepAdapter);
        mStepRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    public void goVideoPlayer(){
        Log.e("Button", "onClick");
        Intent intent = new Intent();
        intent.setClass(getContext(), VideoPlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", new Recipe(mRecipe.getId(),mRecipe.getLink(),mRecipe.getRecipeSteps()));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onStart() {
        super.onStart();
        Uri uri = Uri.parse(mRecipe.getLink());
        player = new SimpleExoPlayer.Builder(getContext()).build();
        recipeVideoView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(uri);
        player.setMediaItem(mediaItem);

    }

    public void onResume() {
        super.onResume();
        player.seekTo(currentWindow, playbackPosition);
        player.prepare();
    }

    @Override
    public void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        player.release();
    }
    @Override
    public void onStepClick(int position) {
        player.seekTo(currentWindow, steps.get(position).getStartTime());
        player.play();
    }
}
