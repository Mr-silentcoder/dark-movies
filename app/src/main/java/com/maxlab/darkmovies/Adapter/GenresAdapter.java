package com.maxlab.darkmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maxlab.darkmovies.GenreActivity;
import com.maxlab.darkmovies.MovieActivity;
import com.maxlab.darkmovies.R;
import com.maxlab.darkmovies.models.GenresModel;

import java.util.ArrayList;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenresViewHolder> {
    Context context;
    ArrayList<GenresModel>genresModels;
    public GenresAdapter(Context context, ArrayList<GenresModel> genresModels){
        this.context = context;
        this.genresModels = genresModels;
    }

    @NonNull
    @Override
    public GenresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.moviegenre, null);
        return new GenresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresViewHolder holder, int position) {
      GenresModel genresModel = genresModels.get(position);
      String genre = genresModel.getGenreName();
      genre = genre.toLowerCase().substring(0, 1).toUpperCase() + genre.substring(1);
      holder.genre.setText(genre);

      holder.genre.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              String genrename = genresModel.getGenreName();
              Intent intent = new Intent(context, GenreActivity.class);
              intent.putExtra("genid", genresModel.getGenreUid());
              intent.putExtra("genname", genrename.toLowerCase().substring(0, 1).toUpperCase() + genrename.substring(1));
              context.startActivity(intent);
          }
      });
    }

    @Override
    public int getItemCount() {
        return genresModels.size();
    }

    public class GenresViewHolder extends RecyclerView.ViewHolder {
        Button genre;
        public GenresViewHolder(@NonNull View itemView) {
            super(itemView);
            genre = itemView.findViewById(R.id.genre);
        }
    }
}
