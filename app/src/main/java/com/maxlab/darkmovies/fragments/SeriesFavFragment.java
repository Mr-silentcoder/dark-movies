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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.maxlab.darkmovies.Adapter.MovieAdapter;
import com.maxlab.darkmovies.Adapter.SeriesAdapter;
import com.maxlab.darkmovies.R;
import com.maxlab.darkmovies.databinding.FragmentFavoriteBinding;
import com.maxlab.darkmovies.databinding.FragmentSeriesFavBinding;
import com.maxlab.darkmovies.models.MovieModel;
import com.maxlab.darkmovies.models.SeriesModel;

import java.util.ArrayList;
import java.util.Objects;


public class SeriesFavFragment extends Fragment {

    FragmentSeriesFavBinding binding;
    FirebaseFirestore database;
    FirebaseAuth auth;
    RecyclerView recyclerView;

    public SeriesFavFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSeriesFavBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        String userid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        recyclerView = binding.recyclerView;

        database.collection("favorite").document(userid).collection("series").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<SeriesModel> seriesModels = new ArrayList<>();
                SeriesAdapter adapter = new SeriesAdapter(getContext(), seriesModels);
                for (DocumentSnapshot snapshot : task.getResult()){
                    if (snapshot.exists()){
                        String serieslang = snapshot.getString("seriesLng");
                        String seriesid = snapshot.getString("seriesUid");
                        assert serieslang != null;
                        database.collection("series").document(serieslang.toLowerCase()).collection("list").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                assert value != null;

                                for (DocumentSnapshot data : value.getDocuments()){
                                    if (data.getId().equals(seriesid)){
                                        SeriesModel model = data.toObject(SeriesModel.class);
                                        assert model != null;
                                        model.setSeriesId(data.getId());
                                        seriesModels.add(model);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recyclerView.setAdapter(adapter);
            }
        });









        return binding.getRoot();
    }
}