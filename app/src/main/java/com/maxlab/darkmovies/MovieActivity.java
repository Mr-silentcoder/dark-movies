package com.maxlab.darkmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.maxlab.darkmovies.Adapter.SummaryAdapter;
import com.maxlab.darkmovies.Adapter.TopPicksAdapter;
import com.maxlab.darkmovies.databinding.ActivityMovieBinding;
import com.maxlab.darkmovies.databinding.RateusviewBinding;
import com.maxlab.darkmovies.models.CastModel;
import com.maxlab.darkmovies.models.DetailsModel;
import com.maxlab.darkmovies.models.MovieGenreModel;
import com.maxlab.darkmovies.models.MovieModel;
import com.maxlab.darkmovies.models.SummaryModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MovieActivity extends AppCompatActivity {
    ActivityMovieBinding binding;
    RecyclerView recyclerView, genreView, topmovieView , summary, details;
    FirebaseFirestore database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        final String movieid     = getIntent().getStringExtra("movieid");
        final String movieuid     = getIntent().getStringExtra("movieuid");
        final String movielan    = getIntent().getStringExtra("movielan");
        final String moviename   = getIntent().getStringExtra("moviename");
        final String movierate   = getIntent().getStringExtra("movierate");
        final String movietime   = getIntent().getStringExtra("movietime");
        final String movieyear   = getIntent().getStringExtra("movieyear");
        final String moviebanner = getIntent().getStringExtra("moviebanner");
        final String movieurl    = getIntent().getStringExtra("movieurl");
        final String trailer     = getIntent().getStringExtra("trailerurl");

        binding.movienName.setText(moviename);
        binding.mRate.setText(movierate);
        binding.mTime.setText(movietime);
        binding.mYear.setText(movieyear);
        Glide.with(this).load(moviebanner).into(binding.mImage);
        recyclerView  = binding.castView;
        genreView     = binding.movieGenre;
        topmovieView  = binding.moreView;
        summary       = binding.summary;
        details       = binding.details;
        setContentView(binding.getRoot());

        //===============> movie genre list
        ArrayList<MovieGenreModel> movieGenreModels = new ArrayList<>();
        MovieGenreAdapter genreAdapter = new MovieGenreAdapter(this, movieGenreModels);
        database.collection("movies").document(movielan).collection("list").document(movieid).collection("genre").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
        genreView.setLayoutManager(new GridLayoutManager(this,3));
        genreView.setAdapter(genreAdapter);

        //=============> movie summary
        ArrayList<SummaryModel> summaryModels = new ArrayList<>();
        SummaryAdapter summaryAdapter = new SummaryAdapter(this, summaryModels);
        database.collection("movies").document(movielan).collection("list").document(movieid).collection("summary").addSnapshotListener(new EventListener<QuerySnapshot>() {
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

        //=====================> movie Details

        ArrayList<DetailsModel> detailsModels = new ArrayList<>();
        DetailsAdapter detailsAdapter = new DetailsAdapter(this, detailsModels);
        database.collection("movies").document(movielan).collection("list").document(movieid).collection("details").addSnapshotListener(new EventListener<QuerySnapshot>() {
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

        //=====================> movie cast list
        ArrayList<CastModel> castModels = new ArrayList<>();
        CastAdapter castadapter = new CastAdapter(this, castModels);
        database.collection("movies").document(movielan).collection("list").document(movieid).collection("cast").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                castModels.clear();
                assert value != null;
                for (DocumentSnapshot snapshot : value.getDocuments()){
                    CastModel castModel = snapshot.toObject(CastModel.class);
                    assert castModel != null;
                    castModel.setCastId(snapshot.getId());
                    castModels.add(castModel);
                }
                castadapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager castLayout = new LinearLayoutManager(this);
        castLayout.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(castLayout);
        recyclerView.setAdapter(castadapter);

        //===============> top movie list
        ArrayList<MovieModel> movieModels = new ArrayList<>();
        TopPicksAdapter topPicksAdapter = new TopPicksAdapter(this, movieModels);
        database.collection("movies").document(movielan).collection("list").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                movieModels.clear();
                assert value != null;
                for (DocumentSnapshot snapshot : value.getDocuments()){
                    MovieModel model = snapshot.toObject(MovieModel.class);
                    assert model != null;
                    double rate = Double.parseDouble(model.getMovieRate());
                    if (rate >= 4.0){
                        model.setMovieId(snapshot.getId());
                        movieModels.add(model);
                    }
                }
                Collections.shuffle(movieModels);
                topPicksAdapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager topview = new LinearLayoutManager(this);
        topview.setOrientation(LinearLayoutManager.HORIZONTAL);
        topmovieView.setLayoutManager(topview);
        topmovieView.setAdapter(topPicksAdapter);

        //============> movie play button
        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieActivity.this, PlayerActivity.class);
                intent.putExtra("videoUrl", movieurl );
                startActivity(intent);
            }
        });

        binding.tPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieActivity.this, TrailerActivity.class);
                intent.putExtra("trailer", trailer);
                startActivity(intent);
            }
        });



        //============> rate us button click
        binding.rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                RateusviewBinding rateusview = RateusviewBinding.inflate(getLayoutInflater());
                AlertDialog dialog = new AlertDialog.Builder(MovieActivity.this).setView(rateusview.getRoot()).setCancelable(false).create();
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
                    database.collection("movies").document(movielan).collection("list").document(movieid).collection("rate").document(String.valueOf(i)).collection(userid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {

                                    if (userid.equals(document.getString("user"))) {
                                        rateusview.rPanel.setVisibility(View.GONE);
                                        rateusview.lPanel.setVisibility(View.VISIBLE);
                                        rateusview.rateSubmit.setVisibility(View.GONE);
                                        Toast.makeText(MovieActivity.this, "Already Rated", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(MovieActivity.this, "Please Select Stars", Toast.LENGTH_SHORT).show();
                        }else {

                            database.collection("movies").document(movielan).collection("list").document(movieid).collection("rate").document(rate).collection(userid)
                                    .add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(MovieActivity.this, "Rated", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    database.collection("movies").document(movielan).collection("list").document(movieid).collection("rate").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()){
                                                for (DocumentSnapshot snapshot : task.getResult()){

                                                    if (rate.equals(snapshot.getString("position"))){
                                                        int starcount = Integer.parseInt(Objects.requireNonNull(snapshot.getString("starCount")));
                                                        int total = Integer.parseInt(Objects.requireNonNull(snapshot.getString("total")));
                                                        int ratecount = Integer.parseInt(rate);
                                                        database.collection("movies").document(movielan).collection("list").document(movieid).collection("rate").document(rate)
                                                                .update("starCount", String.valueOf(starcount+1), "total", String.valueOf(total+ratecount));
                                                    }
                                                }
                                                 // rate update

                                                database.collection("movies").document(movielan).collection("list").document(movieid).collection("rate").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                                                                    database.collection("movies").document(movielan).collection("list").document(movieid).update("movieRate",String.valueOf(rate));
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
                                    Toast.makeText(MovieActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MovieActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });

               dialog.show();
                rateusview.lPanel.setVisibility(View.GONE);
            }
        });

        String userid = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        database.collection("favorite").document(userid).collection("movies").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot fdata : task.getResult()){
                        if (movieid.equals(fdata.getString("movieUid"))) {
                           binding.tBtn.setChecked(true);
                        }
                    }
                }
            }
        });

      binding.tBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Map<String, Object> fdata = new HashMap<>();
              fdata.put("movieName",moviename);
              fdata.put("movieUid", movieid);
              fdata.put("userId", userid);
              fdata.put("movieLng", movielan);

              if (binding.tBtn.isChecked()){
                  database.collection("favorite").document(userid).collection("movies").add(fdata).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                      @Override
                      public void onComplete(@NonNull Task<DocumentReference> task) {
                          if (task.isSuccessful()){
                              Toast.makeText(MovieActivity.this, "Added", Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
              }else{
                  database.collection("favorite").document(userid).collection("movies").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                      @Override
                      public void onComplete(@NonNull Task<QuerySnapshot> task) {
                          if (task.isSuccessful()){
                              for (DocumentSnapshot fdata : task.getResult()){
                                  if (movieid.equals(fdata.getString("movieUid"))) {
                                      database.collection("favorite").document(userid).collection("movies").document(fdata.getId()).delete();
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