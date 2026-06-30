package com.example.tourismapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

    Context context;
    ArrayList<HomeItem> list;
    public ItemAdapter(Context context, ArrayList<HomeItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.home_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int index) {
        HomeItem item = list.get(index);
        holder.tvItemName.setText(item.name);
        holder.imgvLandmark.setImageResource(context.getResources().getIdentifier(item.image, "drawable", context.getPackageName()));
        final int pos = index;
        holder.bExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("From", "HomePage");
                i.putExtra("Landmark", list.get(pos).name);
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
