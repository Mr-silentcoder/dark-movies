package com.maxlab.darkmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.maxlab.darkmovies.Adapter.CastAdapter;
import com.maxlab.darkmovies.Adapter.DetailsAdapter;
import com.maxlab.darkmovies.Adapter.MovieGenreAdapter;
import com.maxlab.darkmovies.Adapter.SeasonsAdapter;
import com.maxlab.darkmovies.Adapter.SeriesAdapter;
import com.maxlab.darkmovies.Adapter.SummaryAdapter;
import com.maxlab.darkmovies.Adapter.TopPicksAdapter;
import com.maxlab.darkmovies.databinding.ActivitySeriesBinding;
import com.maxlab.darkmovies.databinding.RateusviewBinding;
import com.maxlab.darkmovies.models.CastModel;
import com.maxlab.darkmovies.models.DetailsModel;
import com.maxlab.darkmovies.models.MovieGenreModel;
import com.maxlab.darkmovies.models.MovieModel;
import com.maxlab.darkmovies.models.SeasonsModel;
import com.maxlab.darkmovies.models.SeriesModel;
import com.maxlab.darkmovies.models.SummaryModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SeriesActivity extends AppCompatActivity {
    ActivitySeriesBinding binding;
    RecyclerView seriesGenre , summary , details ,seasonView , toppickView;
    FirebaseFirestore database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeriesBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        setContentView(binding.getRoot());

        String seriesName = getIntent().getStringExtra("seriesName");
        final String seriesRate = getIntent().getStringExtra("seriesRate");
        final String seriesUid  = getIntent().getStringExtra("seriesUid");
        final String seriesId   = getIntent().getStringExtra("seriesId");
        final String seriesImg  = getIntent().getStringExtra("seriesBanner");
        final String seasons    = getIntent().getStringExtra("seasons");
        final String seriesYear = getIntent().getStringExtra("seriesYear");
        final String seriesLng  = getIntent().getStringExtra("seriesLng").toLowerCase();
        Glide.with(this).load(seriesImg).into(binding.sImage);
        seriesName = seriesName.toLowerCase().substring(0, 1).toUpperCase() + seriesName.substring(1);
        binding.sName.setText(seriesName);
        String seasons_count = seasons + " Seasons";
        binding.seasons.setText(seasons_count);
        seriesGenre = binding.seriesGenre;
        summary  = binding.summary;
        details = binding.details;
        toppickView = binding.toppickView;
        seasonView = binding.seasonView;
        binding.mRate.setText(seriesRate);
        binding.sYear.setText(seriesYear);

//===============> top movie list
        ArrayList<SeriesModel> seriesModels = new ArrayList<>();
        SeriesAdapter seriesAdapter = new SeriesAdapter(this, seriesModels);
        database.collection("series").document(seriesLng).collection("list").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                seriesModels.clear();
                assert value != null;
                for (DocumentSnapshot snapshot : value.getDocuments()){
                    SeriesModel model = snapshot.toObject(SeriesModel.class);
                    assert model != null;
                    double rate = Double.parseDouble(model.getSeriesRate());
                    if (rate >= 4.0){
                        model.setSeriesId(snapshot.getId());
                        seriesModels.add(model);
                    }
                }
                Collections.shuffle(seriesModels);
                seriesAdapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager topview = new LinearLayoutManager(this);
        topview.setOrientation(LinearLayoutManager.HORIZONTAL);
        toppickView.setLayoutManager(topview);
        toppickView.setAdapter(seriesAdapter);




        //===============> series genre list
        ArrayList<MovieGenreModel> movieGenreModels = new ArrayList<>();
        MovieGenreAdapter genreAdapter = new MovieGenreAdapter(this, movieGenreModels);
        database.collection("series").document(seriesLng).collection("list").document(seriesId).collection("genre").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                movieGenreModels.clear();
                assert value != null;
                for (DocumentSnapshot snapshot : value.getDocuments()){
                    MovieGenreModel movieGenreModel = snapshot.toObject(MovieGenreModel.class);
                    movieGenreModels.add(movieGenreModel);
                }
                genreAdapter.notifyDataSetChanged();

            }
        });
        seriesGenre.setLayoutManager(new GridLayoutManager(this,3));
        seriesGenre.setAdapter(genreAdapter);

        ArrayList<SeasonsModel> seasonsModels = new ArrayList<>();
        SeasonsAdapter seasonsAdapter = new SeasonsAdapter(this, seasonsModels);

        database.collection("series").document(seriesLng).collection("list").document(seriesId).collection("seasons").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                seasonsModels.clear();
                for (DocumentSnapshot seasonsdata : value.getDocuments()){
                    SeasonsModel model = seasonsdata.toObject(SeasonsModel.class);
                    model.setSeasonId(seasonsdata.getId());
                    seasonsModels.add(model);
                }
                seasonsAdapter.notifyDataSetChanged();
            }
        });

        seasonView.setLayoutManager(new GridLayoutManager(this, 1));
        seasonView.setAdapter(seasonsAdapter);

        //=============> series summary
        ArrayList<SummaryModel> summaryModels = new ArrayList<>();
        SummaryAdapter summaryAdapter = new SummaryAdapter(this, summaryModels);
        database.collection("series").document(seriesLng).collection("list").document(seriesId).collection("summary").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                summaryModels.clear();
                assert value != null;
                for (DocumentSnapshot smsnapshot : value.getDocuments()){
                    SummaryModel summaryModel = smsnapshot.toObject(SummaryModel.class);
                    summaryModels.add(summaryModel);
                }
                summaryAdapter.notifyDataSetChanged();

            }
        });
        summary.setLayoutManager(new GridLayoutManager(this, 1));
        summary.setAdapter(summaryAdapter);
        summary.setNestedScrollingEnabled(false);

        //=====================> seris Details

        ArrayList<DetailsModel> detailsModels = new ArrayList<>();
        DetailsAdapter detailsAdapter = new DetailsAdapter(this, detailsModels);
        database.collection("series").document(seriesLng).collection("list").document(seriesId).collection("details").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                detailsModels.clear();
                assert value != null;
                for (DocumentSnapshot desnapshot : value.getDocuments()){
                    DetailsModel detailsModel = desnapshot.toObject(DetailsModel.class);
                    detailsModels.add(detailsModel);
                }
                detailsAdapter.notifyDataSetChanged();

            }
        });
        details.setLayoutManager(new GridLayoutManager(this, 1));
        details.setAdapter(detailsAdapter);
        details.setNestedScrollingEnabled(false);

        //============> rate us button click
        binding.rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                RateusviewBinding rateusview = RateusviewBinding.inflate(getLayoutInflater());
                AlertDialog dialog = new AlertDialog.Builder(SeriesActivity.this).setView(rateusview.getRoot()).setCancelable(false).create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final int[] star = new int[1];
                ToggleButton star1, star2, star3,star4, star5;
                star1 = rateusview.star1;
                star2 = rateusview.star2;
                star3 = rateusview.star3;
                star4 = rateusview.star4;
                star5 = rateusview.star5;
                star1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (star1.isChecked()){
                            star[0] = 1;
                        }else{
                            star[0] = 0;
                            star2.setChecked(false);
                            star3.setChecked(false);
                            star4.setChecked(false);
                            star5.setChecked(false);
                        }
                    }
                });

                star2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (star2.isChecked()){
                            star[0] = 2;
                            star1.setChecked(true);
                        }else{
                            star[0] = 0;
                            star1.setChecked(false);
                            star3.setChecked(false);
                            star4.setChecked(false);
                            star5.setChecked(false);
                        }
                    }
                });

                star3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (star3.isChecked()){
                            star[0] = 3;
                            star1.setChecked(true);
                            star2.setChecked(true);
                        }else{
                            star[0] = 0;
                            star1.setChecked(false);
                            star2.setChecked(false);
                            star4.setChecked(false);
                            star5.setChecked(false);
                        }
                    }
                });
                star4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (star4.isChecked()){
                            star[0] = 4;
                            star1.setChecked(true);
                            star2.setChecked(true);
                            star3.setChecked(true);
                        }else{
                            star[0] = 0;
                            star1.setChecked(false);
                            star2.setChecked(false);
                            star3.setChecked(false);
                            star5.setChecked(false);
                        }
                    }
                });
                star5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (star5.isChecked()){
                            star[0] = 5;
                            star1.setChecked(true);
                            star2.setChecked(true);
                            star3.setChecked(true);
                            star4.setChecked(true);
                        }else{
                            star[0] = 0;
                            star1.setChecked(false);
                            star2.setChecked(false);
                            star3.setChecked(false);
                            star4.setChecked(false);
                        }
                    }
                });


                //===========> check alredy rated or not
                int[] starRate = { 1,2,3,4,5};
                for (int i:starRate) {
                    database.collection("series").document(seriesLng).collection("list").document(seriesId).collection("rate").document(String.valueOf(i)).collection(userid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {

                                    if (userid.equals(document.getString("user"))) {
                                        rateusview.rPanel.setVisibility(View.GONE);
                                        rateusview.lPanel.setVisibility(View.VISIBLE);
                                        rateusview.rateSubmit.setVisibility(View.GONE);
                                        Toast.makeText(SeriesActivity.this, "Already Rated", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        }
                    });
                }


                //===========> rate submit
                rateusview.rateSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String rate = String.valueOf(star[0]);

                        Map<String, Object> data = new HashMap<>();
                        data.put("rate",rate);
                        data.put("user", userid);

                        if (star[0] == 0){
                            Toast.makeText(SeriesActivity.this, "Please Select Stars", Toast.LENGTH_SHORT).show();
                        }else {

                            database.collection("series").document(seriesLng).collection("list").document(seriesId).collection("rate").document(rate).collection(userid)
                                    .add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(SeriesActivity.this, "Rated", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    database.collection("series").document(seriesLng).collection("list").document(seriesId).collection("rate").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()){
                                                for (DocumentSnapshot snapshot : task.getResult()){

                                                    if (rate.equals(snapshot.getString("position"))){
                                                        int starcount = Integer.parseInt(Objects.requireNonNull(snapshot.getString("starCount")));
                                                        int total = Integer.parseInt(Objects.requireNonNull(snapshot.getString("total")));
                                                        int ratecount = Integer.parseInt(rate);
                                                        database.collection("series").document(seriesLng).collection("list").document(seriesId).collection("rate").document(rate)
                                                                .update("starCount", String.valueOf(starcount+1), "total", String.valueOf(total+ratecount));
                                                    }

                                                }
                                                // rate update

                                                database.collection("series").document(seriesLng).collection("list").document(seriesId).collection("rate").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()){
                                                            int totalRate;
                                                            int totalcount;
                                                            int[] total = new int[5];
                                                            int[] count = new int[5];
                                                            for (DocumentSnapshot snapshot : task.getResult()){
                                                                int position = Integer.parseInt(Objects.requireNonNull(snapshot.getString("position")));
                                                                if (position == 1){
                                                                    total[0] = Integer.parseInt(Objects.requireNonNull(snapshot.getString("total")));
                                                                    count[0] = Integer.parseInt(Objects.requireNonNull(snapshot.getString("starCount")));
                                                                }else if (position == 2){
                                                                    total[1] = Integer.parseInt(Objects.requireNonNull(snapshot.getString("total")));
                                                                    count[1] = Integer.parseInt(Objects.requireNonNull(snapshot.getString("starCount")));
                                                                }else if (position ==3){
                                                                    total[2] = Integer.parseInt(Objects.requireNonNull(snapshot.getString("total")));
                                                                    count[2] = Integer.parseInt(Objects.requireNonNull(snapshot.getString("starCount")));
                                                                }else if (position == 4){
                                                                    total[3] = Integer.parseInt(Objects.requireNonNull(snapshot.getString("total")));
                                                                    count[3] = Integer.parseInt(Objects.requireNonNull(snapshot.getString("starCount")));
                                                                }else if (position ==5){
                                                                    total[4] = Integer.parseInt(Objects.requireNonNull(snapshot.getString("total")));
                                                                    count[4] = Integer.parseInt(Objects.requireNonNull(snapshot.getString("starCount")));
                                                                }
                                                                // min sdk 19 setup :)
                                                                totalRate = total[0]+total[1]+total[2]+total[3]+total[4];
                                                                totalcount = count[0]+count[1]+count[2]+count[3]+count[4];
                                                                final double  userRate = totalRate / totalcount;
                                                                BigDecimal bd = new BigDecimal(userRate);
                                                                BigDecimal rate = bd.setScale(1, RoundingMode.FLOOR);

                                                                if (!rate.equals(0) ){
                                                                    database.collection("series").document(seriesLng).collection("list").document(seriesId).update("seriesRate",String.valueOf(rate));
                                                                }

                                                            }

                                                        }

                                                    }
                                                });


                                            }
                                        }

                                    });


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SeriesActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                //============> cancel
                rateusview.cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Toast.makeText(SeriesActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
                rateusview.lPanel.setVisibility(View.GONE);
            }
        });


        String userid = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        database.collection("favorite").document(userid).collection("series").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot fdata : task.getResult()){
                        if (seriesId.equals(fdata.getString("seriesUid"))) {
                            binding.tBtn.setChecked(true);
                        }
                    }
                }
            }
        });

        String finalSeriesName = seriesName;
        binding.tBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> fdata = new HashMap<>();
                fdata.put("seriesName", finalSeriesName);
                fdata.put("seriesUid", seriesId);
                fdata.put("userId", userid);
                fdata.put("seriesLng", seriesLng);

                if (binding.tBtn.isChecked()){
                    database.collection("favorite").document(userid).collection("series").add(fdata).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SeriesActivity.this, "Added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    database.collection("favorite").document(userid).collection("series").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (DocumentSnapshot fdata : task.getResult()){
                                    if (seriesId.equals(fdata.getString("seriesUid"))) {
                                        database.collection("favorite").document(userid).collection("series").document(fdata.getId()).delete();
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });


    }
}

