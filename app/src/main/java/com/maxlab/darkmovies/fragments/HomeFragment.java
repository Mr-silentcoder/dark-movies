package com.maxlab.darkmovies.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.maxlab.darkmovies.Adapter.GenresAdapter;
import com.maxlab.darkmovies.Adapter.MovieAdapter;
import com.maxlab.darkmovies.Adapter.MovieListAdapter;
import com.maxlab.darkmovies.ListActivity;
import com.maxlab.darkmovies.databinding.FragmentHomeBinding;
import com.maxlab.darkmovies.models.GenresModel;
import com.maxlab.darkmovies.models.MovieListModal;
import com.maxlab.darkmovies.models.MovieModel;
import com.maxlab.darkmovies.models.SliderModels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    FirebaseFirestore database;
    RecyclerView recyclerView , listView;
    ProgressDialog dialog;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        recyclerView = binding.genres;
        listView = binding.listView;
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading..");

        //======================> slider block
        ImageSlider imageSlider = binding.slider;
        List<SlideModel> slideModels = new ArrayList<>();
        database.collection("slider").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for (DocumentSnapshot snapshot : value.getDocuments()){
                    SliderModels models = snapshot.toObject(SliderModels.class);
                    slideModels.add(new SlideModel(models.getSliderImage(),models.getSliderName(),ScaleTypes.FIT));
                    imageSlider.setImageList(slideModels, ScaleTypes.FIT);
                }
            }
        });

       //========================> Genres List Block
        ArrayList<GenresModel> genresModel = new ArrayList<>();
        GenresAdapter adapter = new GenresAdapter(getContext(), genresModel);
        database.collection("genres").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
                for (DocumentSnapshot gendata : value.getDocuments()){
                    GenresModel model = gendata.toObject(GenresModel.class);
                    assert model != null;
                    model.setGenreId(gendata.getId());
                    genresModel.add(model);
                }
                adapter.notifyDataSetChanged();

            }
        });

        LinearLayoutManager glinearLayoutManager = new LinearLayoutManager(getContext());
        glinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(glinearLayoutManager);
        recyclerView.setAdapter(adapter);

        listView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        listView.setRecycledViewPool(viewPool);
        listView.setNestedScrollingEnabled(false);

        //===================> Movie List View
        ArrayList<MovieListModal> movieListModals = new ArrayList<>();
        ArrayList<String > movieslist = new ArrayList<>();
        dialog.show();
        database.collection("lang").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot snapshot : task.getResult()) {
                    String name = Objects.requireNonNull(snapshot.getString("lngName")).toLowerCase();
                    movieslist.add(name);
                }
                if (movieslist.size() != 0) {
                    ArrayList[] movieModels = new ArrayList[movieslist.size()];
                    movieListModals.clear();
                    for (int x = 0; x < movieModels.length; x++) {
                        movieModels[x] = new ArrayList<>();
                        int finalX = x;
                        database.collection("movies").document(movieslist.get(x)).collection("list").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                MovieListAdapter listAdapter = new MovieListAdapter(getContext(), movieListModals);
                                listView.setAdapter(listAdapter);
                                movieModels[finalX].clear();
                                assert value != null;
                                for (DocumentSnapshot mdata : value.getDocuments()) {
                                    MovieModel model = mdata.toObject(MovieModel.class);
                                    assert model != null;
                                    model.setMovieId(mdata.getId());
                                    movieModels[finalX].add(model);
                                    dialog.dismiss();
                                }
                                Collections.shuffle(movieModels[finalX]);
                                listAdapter.notifyDataSetChanged();
                            }
                        });
                        String movielistName = movieslist.get(x).substring(0, 1).toUpperCase() + movieslist.get(x).substring(1);
                        movieListModals.add(new MovieListModal(movielistName, movieModels[finalX]));

                    }

                }
            }
        });


        return binding.getRoot();
    }


}