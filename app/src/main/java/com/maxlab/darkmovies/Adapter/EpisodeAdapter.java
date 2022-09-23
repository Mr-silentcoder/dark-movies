package com.maxlab.darkmovies.Adapter;

import android.annotation.SuppressLint;
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
import com.maxlab.darkmovies.PlayerActivity;
import com.maxlab.darkmovies.R;
import com.maxlab.darkmovies.models.EpisodeModel;

import java.util.ArrayList;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {

    Context context;
    ArrayList<EpisodeModel> episodeModels;

    public EpisodeAdapter(Context context, ArrayList<EpisodeModel> episodeModels) {
        this.context = context;
        this.episodeModels = episodeModels;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.episodeview , null);
        return new EpisodeViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        EpisodeModel model = episodeModels.get(position);
        String epsodeName = model.getEpisodeName();
        holder.epName.setText(epsodeName.toLowerCase().substring(0, 1).toUpperCase() + epsodeName.substring(1));
        Glide.with(context).load(model.getEpisodeBanner()).into(holder.epImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("videoUrl", model.getEpisodeUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return episodeModels.size();
    }

    public class EpisodeViewHolder extends RecyclerView.ViewHolder {
        ImageView epImage;
        TextView epName;
        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            epImage = itemView.findViewById(R.id.epImage);
            epName  = itemView.findViewById(R.id.epName);
        }
    }
}
