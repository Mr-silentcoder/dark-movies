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
import com.maxlab.darkmovies.CastActivity;
import com.maxlab.darkmovies.R;
import com.maxlab.darkmovies.models.CastModel;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder>{
    Context context;
    ArrayList<CastModel> castModels;
    public  CastAdapter(Context context, ArrayList<CastModel> castModels){
        this.context    = context;
        this.castModels = castModels;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.castview, null);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
     CastModel castModel = castModels.get(position);
        Glide.with(context).load(castModel.getCastImage()).into(holder.castImage);
        String  castname = castModel.getCastName();
                castname = castname.toUpperCase().charAt(0) + castname.substring(1 , castname.length());
        String castpname = castModel.getCastMname();
               castpname = castpname.toUpperCase().charAt(0) + castpname.substring(1, castpname.length());
        holder.castName.setText(castname);
        holder.castPosition.setText(castpname);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CastActivity.class);
                intent.putExtra("castid", castModel.getCastUid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return castModels.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder{
        ImageView castImage;
        TextView castName, castPosition;
        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            castImage    = itemView.findViewById(R.id.actorImage);
            castName     = itemView.findViewById(R.id.castName);
            castPosition = itemView.findViewById(R.id.castPosition);
        }
    }


}
