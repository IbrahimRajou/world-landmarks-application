package com.example.tourismapp;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView userName, tvHomeQuot;
    RecyclerView rvItems;
    BottomNavigationView bnvBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        AppDatabase dbHelper = new AppDatabase(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        userName = (TextView) findViewById(R.id.userName);
        tvHomeQuot = (TextView) findViewById(R.id.home_quot);

        rvItems = (RecyclerView) findViewById(R.id.recycleView);

        bnvBar = (BottomNavigationView) findViewById(R.id.bottomBarMain);

        userName.setText(Session.currentUser.getName().toUpperCase());

        SpannableString span = new SpannableString(tvHomeQuot.getText().toString());
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#FF7029")), 22, 29, span.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvHomeQuot.setText(span);

        // To get all the landmarks that is descending order and limit the retrieve to 5 records only
        ArrayList<HomeItem> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT Name, MainImage FROM Landmark ORDER BY NoStars DESC LIMIT 5", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new HomeItem(cursor.getString(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Adding the arraylist to the item adapter
        ItemAdapter adapter = new ItemAdapter(this, list);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        rvItems.setAdapter(adapter);

        // To set the hover to the home icon when the user is in the home icon
        bnvBar.setSelectedItemId(R.id.homeIcon);
        bnvBar.setOnItemSelectedListener(item -> {
            Intent a = null;
            if (item.getItemId() == R.id.searchIcon) {
                a = new Intent(this, SearchActivity.class);
            }
            else if (item.getItemId() == R.id.profileIcon) {
                a = new Intent(this, ProfileActivity.class);
            }
            if (a != null) {
                startActivity(a);
            }
            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}