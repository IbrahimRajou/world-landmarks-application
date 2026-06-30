package com.example.tourismapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    EditText etName, etEmail;
    Button bEdit, bSave, bDeleteAccount;
    ImageButton imgbLogOut;
    BottomNavigationView bnvBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        etName = (EditText) findViewById(R.id.nameValue);
        etEmail = (EditText) findViewById(R.id.emailValue);

        bEdit = (Button) findViewById(R.id.editButton);
        bSave = (Button) findViewById(R.id.saveButton);
        bDeleteAccount = (Button) findViewById(R.id.deleteAccountButton);

        imgbLogOut = (ImageButton) findViewById(R.id.logoutButton);

        bnvBar = (BottomNavigationView) findViewById(R.id.bottomBarProfile);

        AppDatabase dbHelper = new AppDatabase(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        etName.setText(Session.currentUser.getName());
        etEmail.setText(Session.currentUser.getEmail());

        Intent i = new Intent();
        i.setClass(getApplicationContext(), RegistrationActivity.class);
        // to return to the registration screen if the user click on the logout button
        imgbLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.currentUser = null;
                startActivity(i);
            }
        });

        // To enable the edit text of the name value
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableDisableInfo();
            }
        });

        // To save the new username to the DB
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().isBlank() || etName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid name", Toast.LENGTH_SHORT).show();
                }
                else if (etName.getText().toString().length() > 20) {
                    Toast.makeText(getApplicationContext(), "Name is too long", Toast.LENGTH_SHORT).show();
                }
                else {
                    ContentValues cv = new ContentValues();
                    cv.put("Name", etName.getText().toString().toLowerCase().trim());
                    int result = db.update("User", cv, "id=?", new String[] {Session.currentUser.getId() + ""});
                    if (result == 1) {
                        Toast.makeText(getApplicationContext(), "Your name has been updated", Toast.LENGTH_SHORT).show();
                        Session.currentUser.setName(etName.getText().toString());
                        etName.setEnabled(false);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Please sign in again to use this feature", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // To delete the user account from the DB
        bDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete("User", "id=?", new String[] {Session.currentUser.getId() + ""});
                Session.currentUser = null;
                startActivity(i);
            }
        });

        // To set the hover effect on the profile icon when the user is in the profile screen
        bnvBar.setSelectedItemId(R.id.profileIcon);
        bnvBar.setOnItemSelectedListener(item -> {
            Intent a = null;
            if (item.getItemId() == R.id.homeIcon) {
                a = new Intent(this, MainActivity.class);
            }
            else if (item.getItemId() == R.id.searchIcon) {
                a = new Intent(this, SearchActivity.class);
            }
            if (a != null) {
                startActivity(a);
            }
            return true;
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profileAct), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void enableDisableInfo(){
        if (etName.isEnabled()){
            etName.setEnabled(false);
        }
        else {
            etName.setEnabled(true);
        }
    }
}