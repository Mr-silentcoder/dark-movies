package com.maxlab.darkmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.maxlab.darkmovies.Adapter.EpisodeAdapter;
import com.maxlab.darkmovies.databinding.ActivityEpisodeBinding;
import com.maxlab.darkmovies.models.EpisodeModel;
import com.maxlab.darkmovies.models.SeasonsModel;

import java.util.ArrayList;

public class EpisodeActivity extends AppCompatActivity {
   ActivityEpisodeBinding binding;
   FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEpisodeBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        setContentView(binding.getRoot());
        RecyclerView episodeView;
        episodeView = binding.episodeView;

        final String seriesLng = getIntent().getStringExtra("serieslng");
        final String seasonid  = getIntent().getStringExtra("seasonsid");
        ArrayList<EpisodeModel> episodeModels = new ArrayList<>();
        EpisodeAdapter episodeAdapter = new EpisodeAdapter(this, episodeModels);

        database.collection("series").document(seriesLng).collection("list").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot snapshot : task.getResult()){
                    if (snapshot.exists()){
                        database.collection("series").document(seriesLng).collection("list").document(snapshot.getId()).collection("seasons").document(seasonid).collection("episode").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                episodeModels.clear();
                                for (DocumentSnapshot data : value.getDocuments()){
                                    if (data.exists()){
                                        EpisodeModel model = data.toObject(EpisodeModel.class);
                                        model.setEpisodeId(data.getId());
                                        episodeModels.add(model);
                                    }

                                }
                                episodeAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });

        episodeView.setLayoutManager( new GridLayoutManager(this, 1));
        episodeView.setAdapter(episodeAdapter);
    }
}