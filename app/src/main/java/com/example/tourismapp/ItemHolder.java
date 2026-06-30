package com.example.tourismapp;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class ItemHolder extends RecyclerView.ViewHolder {

    ImageView imgvLandmark;
    TextView tvItemName;
    Button bExplore;

    public ItemHolder(@NonNull View itemView) {
        super(itemView);
        imgvLandmark = itemView.findViewById(R.id.itemImage);
        tvItemName = itemView.findViewById(R.id.itemName);
        bExplore = itemView.findViewById(R.id.explore);
    }
}
