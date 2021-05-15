package com.example.android.customerapp.ui.recipe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.android.customerapp.R;
import com.example.android.customerapp.VideoPlayerActivity;
import com.example.android.customerapp.adapters.StepAdapter;
import com.example.android.customerapp.models.Recipe;
import com.example.android.customerapp.viewmodels.RecipeViewModel;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class RecipeVideoFragment extends Fragment {

    private RecipeViewModel mRecipeViewModel;
    private RecyclerView mStepRecyclerView;
    private StepAdapter mStepAdapter;
    private Button videoButton;
    private Recipe mRecipe;

    private PlayerView recipeVideoView;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
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

        videoButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), VideoPlayerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("recipe", mRecipe);
            startActivity(intent, bundle);
        });


        mStepAdapter = new StepAdapter(getContext(), mRecipe.getRecipeSteps());
        mStepRecyclerView.setAdapter(mStepAdapter);
        mStepRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
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
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare();
//        player.play();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
        }
    }

}
