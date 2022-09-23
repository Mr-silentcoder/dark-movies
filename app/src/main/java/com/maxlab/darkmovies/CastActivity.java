package com.maxlab.darkmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.maxlab.darkmovies.Adapter.MovieAdapter;
import com.maxlab.darkmovies.databinding.ActivityCastBinding;
import com.maxlab.darkmovies.models.MovieModel;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CastActivity extends AppCompatActivity {
    ActivityCastBinding binding;
    FirebaseFirestore database;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCastBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        recyclerView = binding.recyclerView;
        setContentView(binding.getRoot());
        final String castid = getIntent().getStringExtra("castid");


        if (!castid.isEmpty()){

            database.collection("cast").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()){

                        for (DocumentSnapshot snapshot : task.getResult()){
                            if (snapshot.exists()){
                                String castUid     = snapshot.getString("castUid");
                                String castName    = snapshot.getString("castName");
                                String castImage   = snapshot.getString("castImage");
                                String castDetails = snapshot.getString("castDetails");
                                if (castid.equals(castUid)){
                                    binding.actorName.setText(castName);
                                    Glide.with(CastActivity.this).load(castImage).into(binding.actorImage);
                                    binding.actorInfo.setText(castDetails);
                                }
                            }
                        }
                    }
                }
            });
        }else {
            Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show();
        }


        ArrayList<MovieModel> movieModels = new ArrayList<>();
        MovieAdapter adapter = new MovieAdapter(CastActivity.this, movieModels);

        database.collection("lang").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot snapshot : task.getResult()) {
                        if (snapshot.exists()) {
                            String lngName = snapshot.getString("lngName").toLowerCase();
                             database.collection("movies").document(lngName).collection("list").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                 @Override
                                 public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                     for (DocumentSnapshot mvdata : value.getDocuments()){
                                         database.collection("movies").document(lngName).collection("list").document(mvdata.getId()).collection("cast").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                             @Override
                                             public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                 for (DocumentSnapshot data : task.getResult()){
                                                     if (castid.equals(data.getString("castUid"))){

                                                         database.collection("movies").document(lngName).collection("list").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                             @Override
                                                             public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                                 for (DocumentSnapshot datas : value.getDocuments()){
                                                                     if (datas.getString("movieId").equals(data.getString("movieId"))) {
                                                                         MovieModel model = datas.toObject(MovieModel.class);
                                                                         model.setMovieId(datas.getId());
                                                                         movieModels.add(model);
                                                                     }
                                                                 }
                                                                 adapter.notifyDataSetChanged();
                                                             }
                                                         });

                                                     }
                                                 }

                                             }
                                         });

                                     }
                                 }
                             });

                        }

                    }

                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(CastActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);


        binding.seeAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CastActivity.this, CastListActivity.class);
                intent.putExtra("castid", castid);
                startActivity(intent);
            }
        });


        binding.actorBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}