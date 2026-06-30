package com.example.tourismapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

public class AppDatabase extends SQLiteOpenHelper {

    public Context context;
    public static final String Database_name = "TourismAppDB.db";
    public AppDatabase(@Nullable Context context) {
        super(context, Database_name, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String countryTable = "CREATE TABLE Country ( ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT NOT NULL);";
        String userTable = "CREATE TABLE User ( ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT NOT NULL, Email TEXT , Password TEXT NOT NULL);";
        String landmarkTable = "CREATE TABLE Landmark ( ID INTEGER PRIMARY KEY AUTOINCREMENT, CountryID INTEGER REFERENCES Country (ID) ON DELETE CASCADE, Name TEXT NOT NULL, Location TEXT NOT NULL, Description1 TEXT NOT NULL, Description2 TEXT NOT NULL, Description3 TEXT NOT NULL, MainImage TEXT, Image1 TEXT, Image2 TEXT, Image3 TEXT);";
        String userLikeLandmarkTable = "CREATE TABLE User_Like_Landmark (User_id INTEGER REFERENCES User (ID) ON DELETE CASCADE NOT NULL, Landmark_id INTEGER REFERENCES Landmark (ID) ON DELETE CASCADE NOT NULL, PRIMARY KEY (User_id, Landmark_id) ); ";
        db.execSQL(countryTable);
        db.execSQL(userTable);
        db.execSQL(landmarkTable);
        db.execSQL(userLikeLandmarkTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User_Like_Landmark");
        db.execSQL("DROP TABLE IF EXISTS Landmark");
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Country");
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

}
