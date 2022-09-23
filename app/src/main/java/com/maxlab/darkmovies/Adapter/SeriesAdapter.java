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
import com.maxlab.darkmovies.R;
import com.maxlab.darkmovies.SeriesActivity;
import com.maxlab.darkmovies.models.SeriesModel;

import java.util.ArrayList;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder> {

    Context context;
    ArrayList<SeriesModel> seriesModels;
    public SeriesAdapter(Context context, ArrayList<SeriesModel> seriesModels){
        this.context = context;
        this.seriesModels = seriesModels;
    }

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.seriesview, null);
        return new SeriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder holder, int position) {
        SeriesModel model = seriesModels.get(position);
        Glide.with(context).load(model.getSeriesImage()).into(holder.seriesImage);
        holder.seriesName.setText(model.getSeriesName());
        holder.seriesRate.setText(model.getSeriesRate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SeriesActivity.class);
                intent.putExtra("seriesName", model.getSeriesName());
                intent.putExtra("seriesBanner", model.getSeriesImage());
                intent.putExtra("seriesRate", model.getSeriesRate());
                intent.putExtra("seriesUid", model.getSeriesUid());
                intent.putExtra("seriesId", model.getSeriesId());
                intent.putExtra("seasons", model.getSeasons());
                intent.putExtra("seriesLng", model.getSeriesLng());
                intent.putExtra("seriesYear", model.getSeriesYear());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return seriesModels.size();
    }

    public class SeriesViewHolder extends RecyclerView.ViewHolder{
        ImageView seriesImage;
        TextView  seriesName , seriesRate;
        public SeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            seriesImage = itemView.findViewById(R.id.seriesImage);
            seriesName  = itemView.findViewById(R.id.seriesName);
            seriesRate  = itemView.findViewById(R.id.sRate);
        }
    }
}
