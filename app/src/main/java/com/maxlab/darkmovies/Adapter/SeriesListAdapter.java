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
import com.maxlab.darkmovies.SeriesListActivity;
import com.maxlab.darkmovies.models.SeriesListModel;
import com.maxlab.darkmovies.models.SeriesModel;

import java.util.ArrayList;
import java.util.List;

public class SeriesListAdapter extends RecyclerView.Adapter<SeriesListAdapter.SeriesListViewHolder> {
    Context context;
    List<SeriesListModel> seriesListModels = new ArrayList<>();

    public SeriesListAdapter(Context context, List<SeriesListModel> seriesListModels) {
        this.context = context;
        this.seriesListModels = seriesListModels;
    }

    @NonNull
    @Override
    public SeriesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.seriessliderview, null);
        return new SeriesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesListViewHolder holder, int position) {
        holder.listSName.setText(seriesListModels.get(position).getSeriesListName());
        SeriesAdapter adapter = new SeriesAdapter(context, (ArrayList<SeriesModel>) seriesListModels.get(position).getSeriesModels());
        holder.listSeriesView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL , false));
        holder.listSeriesView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        holder.seeAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SeriesListActivity.class);
                intent.putExtra("list", seriesListModels.get(position).getSeriesListName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seriesListModels.size();
    }

    public class SeriesListViewHolder extends RecyclerView.ViewHolder {
        RecyclerView listSeriesView;
        TextView listSName;
        TextView seeAllBtn;
        public SeriesListViewHolder(@NonNull View itemView) {
            super(itemView);
            listSeriesView = itemView.findViewById(R.id.listSeriesView);
            listSName = itemView.findViewById(R.id.listSName);
            seeAllBtn = itemView.findViewById(R.id.seeall);
        }
    }
}
