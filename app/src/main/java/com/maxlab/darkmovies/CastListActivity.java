package com.maxlab.darkmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.maxlab.darkmovies.Adapter.MovieAdapter;
import com.maxlab.darkmovies.databinding.ActivityCastListBinding;
import com.maxlab.darkmovies.models.MovieModel;

import java.util.ArrayList;
import java.util.HashSet;

public class CastListActivity extends AppCompatActivity {
    ActivityCastListBinding binding;
    FirebaseFirestore database;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCastListBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        setContentView(binding.getRoot());
        recyclerView = binding.recyclerView;
        final String castid = getIntent().getStringExtra("castid");


        HashSet<String> movielist = new HashSet<>();

        database.collection("lang").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot snapshot : task.getResult()) {
                        if (snapshot.exists()) {
                            String lngName = snapshot.getString("lngName").toLowerCase();
                            movielist.add(lngName);
                        }
                    }

                    if (movielist.size() !=0){
                        ArrayList<MovieModel> movieModels = new ArrayList<>();
                        MovieAdapter adapter = new MovieAdapter(CastListActivity.this, movieModels);

                        for (String i : movielist){

                            database.collection("movies").document(i).collection("list").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    for (DocumentSnapshot mvdata : value.getDocuments()){
                                        database.collection("movies").document(i).collection("list").document(mvdata.getId()).collection("cast").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                for (DocumentSnapshot data : task.getResult()){
                                                    if (castid.equals(data.getString("castUid"))){

                                                        database.collection("movies").document(i).collection("list").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                        recyclerView.setLayoutManager(new GridLayoutManager(CastListActivity.this, 2));
                        recyclerView.setAdapter(adapter);
                    }
                }
            }
        });

        binding.sellAllBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

}