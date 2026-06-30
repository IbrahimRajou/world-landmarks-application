package com.example.tourismapp;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LandmarkHolder extends RecyclerView.ViewHolder {

    ImageView imgvLandmark;
    TextView tvLandmarkName;
    TextView tvLandmarkLocation;
    TextView tvLandmarkDescription;
    ImageButton bOpenLandmark;
    public LandmarkHolder(@NonNull View itemView) {
        super(itemView);
        this.imgvLandmark = itemView.findViewById(R.id.landmarkCardImage);
        this.tvLandmarkName = itemView.findViewById(R.id.landmarkCardName);
        this.tvLandmarkLocation = itemView.findViewById(R.id.landmarkCardLocation);
        this.tvLandmarkDescription = itemView.findViewById(R.id.landmarkCardDescription);
        this.bOpenLandmark = itemView.findViewById(R.id.landmarkCardOpen);
    }
}
