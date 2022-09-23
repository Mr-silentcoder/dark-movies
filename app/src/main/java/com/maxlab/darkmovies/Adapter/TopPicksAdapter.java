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

public class TopPicksAdapter extends RecyclerView.Adapter<TopPicksAdapter.TopPicksViewHolder> {

    Context context;
    ArrayList<MovieModel> movieModels;
    public TopPicksAdapter(Context context, ArrayList<MovieModel> movieModels){
        this.context = context;
        this.movieModels = movieModels;
    }

    @NonNull
    @Override
    public TopPicksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.toppicksview, null);
        return new TopPicksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPicksViewHolder holder, int position) {
        MovieModel movieModel = movieModels.get(position);
        Glide.with(context).load(movieModel.getMovieImage()).into(holder.topMovieImage);
        holder.topMovieRate.setText(movieModel.getMovieRate());
        holder.topMovieName.setText(movieModel.getMovieName());

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

    public class TopPicksViewHolder extends RecyclerView.ViewHolder{
        ImageView topMovieImage;
        TextView topMovieName,topMovieRate;
        public TopPicksViewHolder(@NonNull View itemView) {
            super(itemView);
            topMovieImage = itemView.findViewById(R.id.topMovieImage);
            topMovieRate = itemView.findViewById(R.id.topMovieRate);
            topMovieName = itemView.findViewById(R.id.topMovieName);
        }
    }
}
