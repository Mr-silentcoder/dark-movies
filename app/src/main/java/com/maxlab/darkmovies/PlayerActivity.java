package com.maxlab.darkmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.maxlab.darkmovies.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity {
    ActivityPlayerBinding binding;
    ExoPlayer player;
    StyledPlayerView playerView;

    private   String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        playerView = binding.exoPlayer;
        Bundle epurl = getIntent().getExtras();
        if (epurl != null){
            videoUrl = epurl.getString("videoUrl");;
        }

    }


    public void intilizeplayer(){
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }




    @Override
    protected void onStart() {
        super.onStart();
        intilizeplayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player == null){
            intilizeplayer();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (player.isPlaying()){
            player.stop();
            player.release();
        }
    }
}