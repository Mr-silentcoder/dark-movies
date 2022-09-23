package com.maxlab.darkmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.maxlab.darkmovies.Adapter.MovieAdapter;
import com.maxlab.darkmovies.databinding.ActivityListBinding;
import com.maxlab.darkmovies.models.MovieModel;

import java.util.ArrayList;
import java.util.Collections;

public class ListActivity extends AppCompatActivity {
    ActivityListBinding binding;
    RecyclerView recyclerView;
    FirebaseFirestore database;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        recyclerView = binding.ListView;
        searchView = binding.searchView;
        setContentView(binding.getRoot());
        final String listname = getIntent().getStringExtra("list").toLowerCase();
        ArrayList<MovieModel> movieModels = new ArrayList<>();
        MovieAdapter movieAdapter = new MovieAdapter(this, movieModels);

        //================> list movies
        database.collection("movies").document(listname).collection("list").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                movieModels.clear();
                assert value != null;
                for (DocumentSnapshot snapshot : value.getDocuments()){
                    MovieModel model = snapshot.toObject(MovieModel.class);
                    assert model != null;
                    model.setMovieId(snapshot.getId());
                    binding.catName.setText(model.getMovieLng() + " Movies");
                    movieModels.add(model);
                }
                Collections.shuffle(movieModels);
                movieAdapter.notifyDataSetChanged();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(movieAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                database.collection("movies").document(listname).collection("list").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        movieModels.clear();
                        assert value != null;
                        for (DocumentSnapshot snapshot : value.getDocuments()){
                            MovieModel model = snapshot.toObject(MovieModel.class);
                            assert model != null;
                            model.setMovieId(snapshot.getId());
                            binding.catName.setText(model.getMovieLng() + " Movies");

                            if (model.getMovieName().toLowerCase().contains(s.toLowerCase())){
                                movieModels.add(model);
                            }
                        }
                        movieAdapter.notifyDataSetChanged();
                    }
                });
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.catName.setVisibility(View.INVISIBLE);
                binding.backBtn.setVisibility(View.INVISIBLE);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                binding.catName.setVisibility(View.VISIBLE);
                binding.backBtn.setVisibility(View.VISIBLE);
                return false;
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

}