package com.example.tourismapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recycleLandmarks;
    BottomNavigationView bnvBar;
    AutoCompleteTextView autoTVLandmark;
    Spinner spCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        AppDatabase dbHelper = new AppDatabase(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        recycleLandmarks = (RecyclerView) findViewById(R.id.displayLandmarks);
        bnvBar = (BottomNavigationView) findViewById(R.id.bottomBarSearch);
        autoTVLandmark = (AutoCompleteTextView) findViewById(R.id.searchALandmark);
        spCountry = (Spinner) findViewById(R.id.searchByCountrySpinner);

        // To get all the landmarks in a random order to be displayed in the search screen
        Cursor cursor = db.rawQuery("SELECT Name, Location, ShortDescription, SearchImage FROM Landmark ORDER BY Random()", null);
        showAllLandmarks(cursor);

        autoTVLandmark.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            // While the user is typing we retrieve the landmarks that contains the entered text from the user
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchValue = s.toString().toLowerCase();
                ArrayList<LandmarkModel> newList = new ArrayList<>();
                if (cursor.moveToFirst()) {
                    do {
                        if (cursor.getString(0).toLowerCase().contains(searchValue)) {
                            newList.add(new LandmarkModel(cursor.getString(0), cursor.getString(1),
                                    cursor.getString(2), cursor.getString(3)));
                        }
                    } while (cursor.moveToNext());
                }
                LandmarkAdapter newAdapter = new LandmarkAdapter(SearchActivity.this, newList);
                recycleLandmarks.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                recycleLandmarks.setAdapter(newAdapter);
            }
        });

        // To add all the countries in the DB to the spinner
        ArrayList<String> spList = new ArrayList<>();
        spList.add("Select a country");
        Cursor cursor2 = db.rawQuery("SELECT ID, Name FROM Country", null);
        if (cursor2.moveToFirst()) {
            do {
                spList.add(cursor2.getString(1));
            } while (cursor2.moveToNext());
        }
        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spList);
        spCountry.setAdapter(spAdapter);

        // To display the landmarks based on the user selection from the spinner
        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                autoTVLandmark.setText("");
                if (index != 0){
                    String country = spList.get(index);
                    ArrayList<LandmarkModel> newList = new ArrayList<>();
                    Cursor cursor3 = db.rawQuery("SELECT Name, Location, ShortDescription, SearchImage, CountryId FROM Landmark WHERE CountryID = (SELECT ID FROM Country WHERE Name = ?) " +
                            "ORDER BY Random() LIMIT 10", new String [] {country});
                    if (cursor3.moveToFirst()) {
                        do {
                            newList.add(new LandmarkModel(cursor3.getString(0), cursor3.getString(1),
                                    cursor3.getString(2), cursor3.getString(3)));
                        } while (cursor3.moveToNext());
                    }
                    LandmarkAdapter newAdapter = new LandmarkAdapter(SearchActivity.this, newList);
                    recycleLandmarks.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                    recycleLandmarks.setAdapter(newAdapter);
                }
                else {
                    showAllLandmarks(cursor);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // To set the hover effect on the search icon when the user is in the search screen
        bnvBar.setSelectedItemId(R.id.searchIcon);
        bnvBar.setOnItemSelectedListener(item -> {
            Intent a = null;
            if (item.getItemId() == R.id.homeIcon) {
                a = new Intent(this, MainActivity.class);
            }
            else if (item.getItemId() == R.id.profileIcon) {
                a = new Intent(this, ProfileActivity.class);
            }
            if (a != null) {
                startActivity(a);
            }
            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.searchAct), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void showAllLandmarks(Cursor cursor){
        ArrayList<LandmarkModel> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                list.add(new LandmarkModel(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        LandmarkAdapter adapter = new LandmarkAdapter(this, list);
        recycleLandmarks.setLayoutManager(new LinearLayoutManager(this));
        recycleLandmarks.setAdapter(adapter);
    }
}