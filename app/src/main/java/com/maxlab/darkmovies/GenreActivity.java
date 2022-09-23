package com.maxlab.darkmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.maxlab.darkmovies.databinding.ActivityGenreBinding;
import com.maxlab.darkmovies.models.MovieModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class GenreActivity extends AppCompatActivity {
    ActivityGenreBinding binding;
    FirebaseFirestore database;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGenreBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        setContentView(binding.getRoot());
        recyclerView = binding.recyclerView;
        final String genid = getIntent().getStringExtra("genid");
        final String genname = getIntent().getStringExtra("genname");
        String genreName = genname + " Movies";
        binding.genName.setText(genreName);

        ArrayList<MovieModel> movieModels = new ArrayList<>();
        MovieAdapter adapter = new MovieAdapter(GenreActivity.this, movieModels);

        database.collection("lang").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot snapshot : task.getResult()) {
                        if (snapshot.exists()) {
                            String lngName = Objects.requireNonNull(snapshot.getString("lngName")).toLowerCase();

                            database.collection("movies").document(lngName).collection("list").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    assert value != null;
                                    for (DocumentSnapshot mvdata : value.getDocuments()){
                                        database.collection("movies").document(lngName).collection("list").document(mvdata.getId()).collection("genre").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                for (DocumentSnapshot data : task.getResult()){
                                                    if (genid.equals(data.getString("genreUid"))) {

                                                        database.collection("movies").document(lngName).collection("list").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                                assert value != null;
                                                                for (DocumentSnapshot datas : value.getDocuments()) {
                                                                    if (Objects.equals(datas.getString("movieId"), data.getString("movieId"))) {
                                                                        MovieModel model = datas.toObject(MovieModel.class);
                                                                        assert model != null;
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
        recyclerView.setLayoutManager(new GridLayoutManager(GenreActivity.this, 2));
        recyclerView.setAdapter(adapter);
        //===========>
        binding.genBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}