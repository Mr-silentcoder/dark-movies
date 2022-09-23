package com.maxlab.darkmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maxlab.darkmovies.EpisodeActivity;
import com.maxlab.darkmovies.R;
import com.maxlab.darkmovies.models.SeasonsModel;

import java.util.ArrayList;

public class SeasonsAdapter extends RecyclerView.Adapter<SeasonsAdapter.SeasonsViewHolder> {

    Context context;
    ArrayList<SeasonsModel> seasonsModels;

    public SeasonsAdapter(Context context, ArrayList<SeasonsModel> seasonsModels) {
        this.context = context;
        this.seasonsModels = seasonsModels;
    }

    @NonNull
    @Override
    public SeasonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.seasonsview , null);
        return new SeasonsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonsViewHolder holder, int position) {
        SeasonsModel model = seasonsModels.get(position);
        holder.sButton.setText(model.getSeasonName());
        holder.sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EpisodeActivity.class);
                intent.putExtra("seasonsid", model.getSeasonId());
                intent.putExtra("serieslng", model.getSeriesLng());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seasonsModels.size();
    }

    public class SeasonsViewHolder extends RecyclerView.ViewHolder {
         Button sButton;
        public SeasonsViewHolder(@NonNull View itemView) {
            super(itemView);
            sButton = itemView.findViewById(R.id.sButton);
        }
    }
}
