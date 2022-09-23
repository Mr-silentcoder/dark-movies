package com.maxlab.darkmovies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maxlab.darkmovies.R;
import com.maxlab.darkmovies.models.DetailsModel;

import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {

    Context context;
    ArrayList<DetailsModel> detailsModels;

    public DetailsAdapter(Context context, ArrayList<DetailsModel> detailsModels) {
        this.context = context;
        this.detailsModels = detailsModels;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detailsview, null);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
        DetailsModel detailsModel = detailsModels.get(position);
        String dname = detailsModel.getDetailsName();
        String vname = detailsModel.getDetailsValue();
        dname = dname.toUpperCase().charAt(0)+dname.substring(1,dname.length());
        vname = vname.toUpperCase().charAt(0)+vname.substring(1,vname.length());
        holder.detailsName.setText(dname);
        holder.detailsValue.setText(vname);

    }

    @Override
    public int getItemCount() {
        return detailsModels.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView detailsName, detailsValue;
        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            detailsName  = itemView.findViewById(R.id.detailsName);
            detailsValue = itemView.findViewById(R.id.detailsValue);
        }
    }
}
