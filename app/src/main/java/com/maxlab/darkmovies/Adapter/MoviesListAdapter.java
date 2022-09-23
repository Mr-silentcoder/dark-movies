package com.maxlab.darkmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.maxlab.darkmovies.MovieActivity;
import com.maxlab.darkmovies.R;
import com.maxlab.darkmovies.models.MovieModel;

import java.util.ArrayList;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder> {

    Context context;
    ArrayList<MovieModel> movieModels;
    public MoviesListAdapter(Context context, ArrayList<MovieModel> movieModels){
        this.context = context;
        this.movieModels = movieModels;
    }

    @NonNull
    @Override
    public MoviesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movieslistview, null);
        return new MoviesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesListViewHolder holder, int position) {
        MovieModel movieModel = movieModels.get(position);
        Glide.with(context).load(movieModel.getMovieImage()).into(holder.listMovieImage);
        holder.listMovieName.setText(movieModel.getMovieName());
        holder.listMovieRate.setText(movieModel.getMovieRate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieActivity.class);
                intent.putExtra("movieid", movieModel.getMovieId());
                intent.putExtra("movielan",movieModel.getMovieLng());
                intent.putExtra("moviename",movieModel.getMovieName());
                intent.putExtra("movieimage", movieModel.getMovieImage());
                intent.putExtra("movierate", movieModel.getMovieRate());
                intent.putExtra("movieyear", movieModel.getMovieYear());
                intent.putExtra("movietime",movieModel.getMovieTime());
                intent.putExtra("moviebanner", movieModel.getMovieBanner());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    public class MoviesListViewHolder extends RecyclerView.ViewHolder{
        ImageView listMovieImage;
        TextView listMovieName, listMovieRate;
        public MoviesListViewHolder(@NonNull View itemView) {
            super(itemView);
            listMovieImage = itemView.findViewById(R.id.listMovieImage);
            listMovieName  = itemView.findViewById(R.id.listMovieName);
            listMovieRate  = itemView.findViewById(R.id.listMovieRate);
        }
    }
}
