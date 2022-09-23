package com.maxlab.darkmovies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maxlab.darkmovies.R;
import com.maxlab.darkmovies.models.SummaryModel;

import java.util.ArrayList;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder> {

    Context context;
    ArrayList<SummaryModel>summaryModels;

    public SummaryAdapter(Context context, ArrayList<SummaryModel> summaryModels) {
        this.context = context;
        this.summaryModels = summaryModels;
    }

    @NonNull
    @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.summaryview, null);
        return new SummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryViewHolder holder, int position) {
        SummaryModel summaryModel = summaryModels.get(position);
        holder.summaryinfo.setText(summaryModel.getSummary());

    }

    @Override
    public int getItemCount() {
        return summaryModels.size();
    }

    public class SummaryViewHolder extends RecyclerView.ViewHolder {
        TextView summaryinfo;
        public SummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            summaryinfo = itemView.findViewById(R.id.summaryinfo);
        }
    }
}
