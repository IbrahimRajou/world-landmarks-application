package com.example.tourismapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LandmarkAdapter extends RecyclerView.Adapter<LandmarkHolder> {

    Context context;
    ArrayList<LandmarkModel> list;

    public LandmarkAdapter(Context context, ArrayList<LandmarkModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LandmarkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LandmarkHolder(LayoutInflater.from(context).inflate(R.layout.landmark_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LandmarkHolder holder, int index) {
        LandmarkModel item = list.get(index);
        holder.tvLandmarkName.setText(item.landmarkName);
        holder.tvLandmarkLocation.setText(item.landmarkLocation);
        holder.tvLandmarkDescription.setText(item.landmarkDescription);
        holder.imgvLandmark.setImageResource(context.getResources().getIdentifier(item.landmarkImage, "drawable", context.getPackageName()));
        final int pos = index;
        holder.bOpenLandmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("From", "SearchPage");
                i.putExtra("Landmark", list.get(pos).landmarkName);
                i.setClass(context, DetailsActivity.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
