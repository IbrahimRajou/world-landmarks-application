package com.example.tourismapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    ImageButton imgbBack, imgbStar, imgbPrevious, imgbNext;
    TextView tvItemName, tvItemLocation, tvDescription1, tvDescription2, tvDescription3;
    ImageView imgvDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);

        imgbBack = (ImageButton) findViewById(R.id.backButton);
        imgbStar= (ImageButton) findViewById(R.id.starButton);
        imgbPrevious = (ImageButton) findViewById(R.id.previousButton);
        imgbNext = (ImageButton) findViewById(R.id.nextButton);

        tvItemName = (TextView) findViewById(R.id.itemName);
        tvItemLocation = (TextView) findViewById(R.id.itemLocation);
        tvDescription1 = (TextView) findViewById(R.id.description1);
        tvDescription2 = (TextView) findViewById(R.id.description2);
        tvDescription3 = (TextView) findViewById(R.id.description3);

        imgvDetails = (ImageView) findViewById(R.id.detailsImage);

        boolean status [] = {false};

        AppDatabase dpHelper = new AppDatabase(this);
        SQLiteDatabase db = dpHelper.getWritableDatabase();

        Intent i = getIntent();
        String likedLandmark = i.getStringExtra("Landmark");
        Cursor cursor = db.rawQuery("SELECT * FROM Landmark WHERE Name=?", new String[] {likedLandmark});
        cursor.moveToFirst();
        String likedLandmarkId = cursor.getString(0);

        imgbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent();
                if (i.getStringExtra("From").equals("HomePage")) {
                    i2.setClass(getApplicationContext(), MainActivity.class);
                }
                else {
                    i2.setClass(getApplicationContext(), SearchActivity.class);
                }
                startActivity(i2);
            }
        });

        tvItemName.setText(cursor.getString(2));

        Cursor c = db.rawQuery("SELECT * FROM User_Like_Landmark WHERE User_id = ? AND Landmark_id = ?;", new String[] {Session.currentUser.getId() + "", likedLandmarkId});
        if (c.moveToFirst()) {
            imgbStar.setImageResource(R.drawable.true_star_image);
            status[0] = true;
        }
        else {
            imgbStar.setImageResource(R.drawable.star_image);
        }

        int currentStars [] = {Integer.parseInt(cursor.getString(11))};
        ContentValues values = new ContentValues();
        ContentValues newValues = new ContentValues();

        imgbStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status[0]) {
                    imgbStar.setImageResource(R.drawable.star_image);
                    currentStars[0] = currentStars[0] - 1;
                    status[0] = false;
                    db.delete("User_Like_Landmark", "User_id = ? AND Landmark_id = ?", new String[] {Session.currentUser.getId() + "", likedLandmarkId});
                }
                else {
                    imgbStar.setImageResource(R.drawable.true_star_image);
                    currentStars[0] = currentStars[0] + 1;
                    status[0] = true;
                    newValues.put("User_id", Session.currentUser.getId());
                    newValues.put("Landmark_id", likedLandmarkId);
                    db.insert("User_Like_Landmark",null, newValues);
                }
                values.put("NoStars", currentStars[0]);
                long result = db.update("Landmark", values , "Name=?", new String[] {likedLandmark});
            }
        });

        imgvDetails.setImageResource(getResources().getIdentifier(cursor.getString(8), "drawable", getPackageName()));
        String [] array = {cursor.getString(8),
                cursor.getString(9),
                cursor.getString(10)};
        int index [] = {0};
        imgbNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgvDetails.animate() .translationX(-300f) .alpha(0f) .setDuration(400)
                        .setInterpolator(new android.view.animation.AccelerateInterpolator())
                        .withEndAction(() -> {

                            index[0] = (index[0] + 1) % array.length;
                            imgvDetails.setImageResource(getResources().getIdentifier(array[index[0]], "drawable", getPackageName()));

                            imgvDetails.setTranslationX(300f);
                            imgvDetails.animate()
                            .translationX(0f)
                            .alpha(1f)
                            .setInterpolator(new android.view.animation.AccelerateInterpolator())
                            .setDuration(400);
                });
            }
        });
        imgbPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgvDetails.animate() .translationX(300f) .alpha(0f) .setDuration(400)
                        .setInterpolator(new android.view.animation.AccelerateInterpolator())
                        .withEndAction(() -> {

                            index[0] = (index[0] + 2) % array.length;
                            imgvDetails.setImageResource(getResources().getIdentifier(array[index[0]], "drawable", getPackageName()));

                            imgvDetails.setTranslationX(-300f);
                            imgvDetails.animate()
                            .translationX(0f)
                            .alpha(1f)
                            .setInterpolator(new android.view.animation.AccelerateInterpolator())
                            .setDuration(400);
                });
            }

        });

        tvItemLocation.setText(cursor.getString(3));
        tvDescription1.setText(cursor.getString(4));
        tvDescription2.setText(cursor.getString(5));
        tvDescription3.setText(cursor.getString(6));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detailsAct), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}