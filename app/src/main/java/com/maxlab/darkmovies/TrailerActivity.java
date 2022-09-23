package com.maxlab.darkmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.ViewStub;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.maxlab.darkmovies.databinding.ActivityTrailerBinding;


public class TrailerActivity extends YouTubeBaseActivity {
    ActivityTrailerBinding binding;
    FirebaseFirestore database;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrailerBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        setContentView(binding.getRoot());

        youTubePlayerView = binding.youtubePlayerView;

        YouTubePlayer.OnInitializedListener listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Bundle trailerUrl = getIntent().getExtras();
                if (trailerUrl != null){
                    String videoUrl = trailerUrl.getString("trailer");;
                    youTubePlayer.loadVideo(videoUrl);
                    youTubePlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(TrailerActivity.this, "Youtube Player Error", Toast.LENGTH_SHORT).show();
            }
        };

        
         database.collection("api").document("youtube").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                 DocumentSnapshot snapshot = task.getResult();
                 youTubePlayerView.initialize(snapshot.getString("keyData"), listener);
             }
         });


    }

}