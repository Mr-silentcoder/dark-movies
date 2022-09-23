package com.maxlab.darkmovies.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.maxlab.darkmovies.Adapter.MovieListAdapter;
import com.maxlab.darkmovies.Adapter.SeriesAdapter;
import com.maxlab.darkmovies.Adapter.SeriesListAdapter;
import com.maxlab.darkmovies.databinding.FragmentSeriesBinding;
import com.maxlab.darkmovies.models.MovieListModal;
import com.maxlab.darkmovies.models.MovieModel;
import com.maxlab.darkmovies.models.SeriesListModel;
import com.maxlab.darkmovies.models.SeriesModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class SeriesFragment extends Fragment {

    FragmentSeriesBinding binding;
    RecyclerView recyclerView;
    FirebaseFirestore database;

    public SeriesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSeriesBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        recyclerView = binding.seriesView;


        ArrayList<SeriesListModel> seriesListModels = new ArrayList<>();
        ArrayList<String> seriesList = new ArrayList<>();

        database.collection("lang").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot snapshot : task.getResult()){
                    if (snapshot.exists()) {
                        String name = Objects.requireNonNull(snapshot.getString("lngName")).toLowerCase();
                        seriesList.add(name);
                    }
                }

                if (seriesList.size() != 0) {
                    ArrayList[] seriesModels = new ArrayList[seriesList.size()];
                    seriesListModels.clear();
                    for (int x = 0; x < seriesModels.length; x++) {
                        seriesModels[x] = new ArrayList<>();
                        int finalX = x;
                        database.collection("series").document(seriesList.get(x)).collection("list").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                SeriesListAdapter listAdapter = new SeriesListAdapter(getContext(), seriesListModels);
                                recyclerView.setAdapter(listAdapter);
                                seriesModels[finalX].clear();
                                assert value != null;
                                for (DocumentSnapshot mdata : value.getDocuments()) {
                                    SeriesModel model = mdata.toObject(SeriesModel.class);
                                    assert model != null;
                                    model.setSeriesId(mdata.getId());
                                    seriesModels[finalX].add(model);

                                }
                                Collections.shuffle(seriesModels[finalX]);
                                listAdapter.notifyDataSetChanged();
                            }
                        });
                        String serieslistName = seriesList.get(x).substring(0, 1).toUpperCase() + seriesList.get(x).substring(1);
                        seriesListModels.add(new SeriesListModel(serieslistName, seriesModels[finalX]));

                    }

                }



            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        recyclerView.setNestedScrollingEnabled(false);

        return binding.getRoot();
    }
}