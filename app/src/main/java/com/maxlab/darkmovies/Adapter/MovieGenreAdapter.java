package com.maxlab.darkmovies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maxlab.darkmovies.R;
import com.maxlab.darkmovies.models.MovieGenreModel;

import java.util.ArrayList;

public class MovieGenreAdapter extends RecyclerView.Adapter<MovieGenreAdapter.MovieGenreViewHolder> {
    Context context;
    ArrayList<MovieGenreModel> movieGenreModels;
    public MovieGenreAdapter(Context context, ArrayList<MovieGenreModel> movieGenreModels){
       this.context = context;
       this.movieGenreModels = movieGenreModels;
    }

    @NonNull
    @Override
    public MovieGenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.genreview,null);
        return new MovieGenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieGenreViewHolder holder, int position) {
        MovieGenreModel movieGenreModel = movieGenreModels.get(position);
        String genrename = movieGenreModel.getGenreName();
        genrename =  genrename.toLowerCase().substring(0,1).toUpperCase() + genrename.substring(1);
        holder.genreName.setText(genrename);
    }

    @Override
    public int getItemCount() {
        return movieGenreModels.size();
    }

    public class MovieGenreViewHolder extends RecyclerView.ViewHolder {
        TextView genreName;
        public MovieGenreViewHolder(@NonNull View itemView) {
            super(itemView);
            genreName = itemView.findViewById(R.id.genreName);
        }
    }
}
