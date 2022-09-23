package com.maxlab.darkmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maxlab.darkmovies.ListActivity;
import com.maxlab.darkmovies.R;
import com.maxlab.darkmovies.models.MovieListModal;
import com.maxlab.darkmovies.models.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    Context context;
    List<MovieListModal> movieListModals = new ArrayList<>();

    public MovieListAdapter(Context context, List<MovieListModal> movieListModals) {
        this.context = context;
        this.movieListModals = movieListModals;

    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.moviesliderview, null);
        return new MovieListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {
           holder.listTName.setText(movieListModals.get(position).getMovieListName());
           MovieAdapter adapter;
           adapter = new MovieAdapter(context, (ArrayList<MovieModel>) movieListModals.get(position).getMovieModels());
           holder.listMovieView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
           holder.listMovieView.setAdapter(adapter);
           adapter.notifyDataSetChanged();
           holder.seeAllBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(context, ListActivity.class);
                   intent.putExtra("list", movieListModals.get(position).getMovieListName());
                   context.startActivity(intent);
               }
           });
    }

    @Override
    public int getItemCount() {
        return movieListModals.size();
    }

    public class MovieListViewHolder extends RecyclerView.ViewHolder {
        RecyclerView listMovieView;
        TextView listTName;
        ImageView seeAllBtn;
        public MovieListViewHolder(@NonNull View itemView) {
            super(itemView);
            listMovieView = itemView.findViewById(R.id.listSeriesView);
            listTName = itemView.findViewById(R.id.listSName);
            seeAllBtn = itemView.findViewById(R.id.sellAllBtn);
        }
    }
}
