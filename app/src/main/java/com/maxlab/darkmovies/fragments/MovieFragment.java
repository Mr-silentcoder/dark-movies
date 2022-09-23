package com.maxlab.darkmovies.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.maxlab.darkmovies.Adapter.MoviesListAdapter;
import com.maxlab.darkmovies.databinding.FragmentMovieBinding;
import com.maxlab.darkmovies.models.MovieModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class MovieFragment extends Fragment {

    FragmentMovieBinding binding;
    FirebaseFirestore database;
    RecyclerView recyclerView;
    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        recyclerView = binding.movieView;

        ArrayList<MovieModel> movieModels = new ArrayList<>();
        MoviesListAdapter moviesListAdapter = new MoviesListAdapter(getContext(), movieModels);

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

                                    for (DocumentSnapshot data : value.getDocuments()) {
                                        if (data.exists()) {
                                            if (lngName.equals(data.getString("movieLng"))) {
                                                MovieModel movieModel = data.toObject(MovieModel.class);
                                                movieModel.setMovieId(data.getId());
                                                movieModels.add(movieModel);

                                            }

                                        }
                                    }
                                    Collections.shuffle(movieModels);
                                    moviesListAdapter.notifyDataSetChanged();
                                }
                            });

                        }
                    }

                }
            }
        });


        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(moviesListAdapter);


        return binding.getRoot();
    }
}