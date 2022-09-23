package com.maxlab.darkmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.maxlab.darkmovies.Adapter.MovieAdapter;
import com.maxlab.darkmovies.Adapter.SeriesAdapter;
import com.maxlab.darkmovies.databinding.ActivitySeriesListBinding;
import com.maxlab.darkmovies.models.MovieModel;
import com.maxlab.darkmovies.models.SeriesModel;

import java.util.ArrayList;
import java.util.Collections;

public class SeriesListActivity extends AppCompatActivity {
    ActivitySeriesListBinding binding;
    FirebaseFirestore database;
    RecyclerView recyclerView;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeriesListBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        recyclerView = binding.sListView;
        searchView = binding.searchView;
        setContentView(binding.getRoot());

        final String listname = getIntent().getStringExtra("list").toLowerCase();
        ArrayList<SeriesModel> seriesModels = new ArrayList<>();
        SeriesAdapter seriesAdapter = new SeriesAdapter(this, seriesModels);

        //================> list movies
        database.collection("series").document(listname).collection("list").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                seriesModels.clear();
                assert value != null;
                for (DocumentSnapshot snapshot : value.getDocuments()){
                    SeriesModel model = snapshot.toObject(SeriesModel.class);
                    assert model != null;
                    model.setSeriesId(snapshot.getId());
                    binding.srName.setText(model.getSeriesLng() + " Series");
                    seriesModels.add(model);
                }
                Collections.shuffle(seriesModels);
                seriesAdapter.notifyDataSetChanged();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(seriesAdapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                database.collection("series").document(listname).collection("list").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        seriesModels.clear();
                        assert value != null;
                        for (DocumentSnapshot snapshot : value.getDocuments()){
                            SeriesModel model = snapshot.toObject(SeriesModel.class);
                            assert model != null;
                            model.setSeriesId(snapshot.getId());
                            binding.srName.setText(model.getSeriesLng() + " Series");

                            if (model.getSeriesName().toLowerCase().contains(s.toLowerCase())){
                                seriesModels.add(model);
                            }
                        }
                        seriesAdapter.notifyDataSetChanged();
                    }
                });
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.srName.setVisibility(View.INVISIBLE);
                binding.sbackBtn.setVisibility(View.INVISIBLE);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                binding.srName.setVisibility(View.VISIBLE);
                binding.sbackBtn.setVisibility(View.VISIBLE);
                return false;
            }
        });

        binding.sbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });












    }
}