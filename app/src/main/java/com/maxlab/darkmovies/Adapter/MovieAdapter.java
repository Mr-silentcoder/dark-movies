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
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    Context context;
    List<MovieModel> movieModels = new ArrayList<>();

    public MovieAdapter(Context context, List<MovieModel> movieModels) {
        this.context = context;
        this.movieModels = movieModels;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movieview, null);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
     MovieModel movieModel = movieModels.get(position);
        Glide.with(context).load(movieModel.getMovieImage()).into(holder.movieImage);
        String moviename = movieModel.getMovieName();
        moviename = moviename.toLowerCase().substring(0, 1).toUpperCase() + moviename.substring(1);
        holder.movieName.setText(moviename);
        holder.movieRate.setText(movieModel.getMovieRate());

        double rate = Double.parseDouble(movieModel.getMovieRate());

        if (rate <= 2){

            if (rate == 2){
                holder.star1.setColorFilter(context.getResources().getColor(R.color.star));
                holder.star2.setColorFilter(context.getResources().getColor(R.color.star));

            }else if (rate == 1.5){
                holder.star1.setColorFilter(context.getResources().getColor(R.color.star));
                holder.star2.setImageResource(R.drawable.ic_star_half);
                holder.star2.setColorFilter(context.getResources().getColor(R.color.star));

            }else if (rate == 1){
                holder.star1.setColorFilter(context.getResources().getColor(R.color.star));

            }else if (rate == 0.5){
                holder.star1.setImageResource(R.drawable.ic_star_half);
                holder.star1.setColorFilter(context.getResources().getColor(R.color.star));
            }else{
                holder.star1.setColorFilter(context.getResources().getColor(R.color.white));
            }
        }else if (rate <= 4){

            if (rate == 4){
                holder.star1.setColorFilter(context.getResources().getColor(R.color.star));
                holder.star2.setColorFilter(context.getResources().getColor(R.color.star));
                holder.star3.setColorFilter(context.getResources().getColor(R.color.star));
                holder.star4.setColorFilter(context.getResources().getColor(R.color.star));
            }else if (rate == 3.5){
                holder.star1.setColorFilter(context.getResources().getColor(R.color.star));
                holder.star2.setColorFilter(context.getResources().getColor(R.color.star));
                holder.star3.setColorFilter(context.getResources().getColor(R.color.star));
                holder.star4.setImageResource(R.drawable.ic_star_half);
                holder.star4.setColorFilter(context.getResources().getColor(R.color.star));
            }else if (rate == 3){
                holder.star1.setColorFilter(context.getResources().getColor(R.color.star));
                holder.star2.setColorFilter(context.getResources().getColor(R.color.star));
                holder.star3.setColorFilter(context.getResources().getColor(R.color.star));

            }else{
                //2.5
                holder.star1.setColorFilter(context.getResources().getColor(R.color.star));
                holder.star2.setColorFilter(context.getResources().getColor(R.color.star));
                holder.star3.setImageResource(R.drawable.ic_star_half);
                holder.star3.setColorFilter(context.getResources().getColor(R.color.star));
            }
        }else {
            //4.5
            holder.star1.setColorFilter(context.getResources().getColor(R.color.star));
            holder.star2.setColorFilter(context.getResources().getColor(R.color.star));
            holder.star3.setColorFilter(context.getResources().getColor(R.color.star));
            holder.star4.setColorFilter(context.getResources().getColor(R.color.star));
            holder.star5.setImageResource(R.drawable.ic_star_half);
            holder.star5.setColorFilter(context.getResources().getColor(R.color.star));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieActivity.class);
                intent.putExtra("movieid", movieModel.getMovieId());
                intent.putExtra("movieuid", movieModel.getMovieUid());
                intent.putExtra("movielan",movieModel.getMovieLng());
                intent.putExtra("moviename",movieModel.getMovieName());
                intent.putExtra("movieimage", movieModel.getMovieImage());
                intent.putExtra("movierate", movieModel.getMovieRate());
                intent.putExtra("movieyear", movieModel.getMovieYear());
                intent.putExtra("movietime",movieModel.getMovieTime());
                intent.putExtra("moviebanner", movieModel.getMovieBanner());
                intent.putExtra("movieurl", movieModel.getMovieUrl());
                intent.putExtra("trailerurl", movieModel.getTrailerUrl());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage , star1, star2,star3,star4,star5;
        TextView movieName, movieRate;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movieImage);
            movieName = itemView.findViewById(R.id.movieName);
            movieRate = itemView.findViewById(R.id.movieRate);
            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            star4 = itemView.findViewById(R.id.star4);
            star5 = itemView.findViewById(R.id.star5);
        }
    }
}
