package com.example.tourismapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistrationActivity extends AppCompatActivity {

    LinearLayout lay;
    TextView tvRegType, tvPasswordConstraint, tvSignUpText;
    EditText etName, etEmail, etPassword;
    Button bRegTypeButton;
    CheckBox chOkayPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        lay = (LinearLayout) findViewById(R.id.registrationAct);

        tvRegType = (TextView) findViewById(R.id.regType);
        tvPasswordConstraint = (TextView) findViewById(R.id.passwordConstraint);
        tvSignUpText = (TextView) findViewById(R.id.signUpText);

        etName = (EditText) findViewById(R.id.nameInput);
        etEmail = (EditText) findViewById(R.id.emailInput);
        etPassword = (EditText) findViewById(R.id.passwordInput);

        bRegTypeButton = (Button) findViewById(R.id.regTypeButton);

        chOkayPolicy = (CheckBox) findViewById(R.id.okayToPolicy);

        int [] x = {2};
        boolean [] status = {true};

        // To change the color to a part of the text and add an underline
        SpannableString span = new SpannableString(tvSignUpText.getText());
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#0083F6")), 23, 30, span.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new UnderlineSpan(), 23, 30, span.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvSignUpText.setText(span);

        // The animation will be applied to the sign in and sign up
        bRegTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase dbHelper = new AppDatabase(RegistrationActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                Session.currentUser = new User();

                Session.currentUser.setName(etName.getText().toString().toLowerCase());
                Session.currentUser.setEmail(etEmail.getText().toString().toLowerCase());
                Session.currentUser.setPassword(etPassword.getText().toString());

                Cursor cursor = db.rawQuery("SELECT Id, Name, Email, Password FROM User;", null);
                // if the registration button text was sign in
                if (bRegTypeButton.getText().toString().equals("Sign in")) {
                    if (cursor.moveToFirst()) {
                        do {
                            String tableEmail = cursor.getString(2);
                            String tablePassword = cursor.getString(3);
                            // To check if the entered data exists in DB
                            if (tableEmail.equals(Session.currentUser.getEmail()) && tablePassword.equals(Session.currentUser.getPassword())) {
                                Session.currentUser.setId(Integer.parseInt(cursor.getString(0)));
                                Session.currentUser.setName(cursor.getString(1));
                                Intent i = new Intent();
                                i.setClass(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                status[0] = false;
                                break;
                            }
                        } while (cursor.moveToNext());
                        // Display a toast message if the entered data does not exist in DB
                        if (status[0]){
                            Toast.makeText(getApplicationContext(), "Invalid Email or Password, Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                // else means the registration button is sign up
                else {
                    // Some validation on the entered data before creating a new record in DB
                    if (Session.currentUser.getName().isBlank() || Session.currentUser.getEmail().isBlank() || Session.currentUser.getPassword().isBlank()) {
                        Toast.makeText(getApplicationContext(), "Please fill all the data correctly", Toast.LENGTH_SHORT).show();
                    }
                    else if (Session.currentUser.getName().length() > 20) {
                        Toast.makeText(getApplicationContext(), "Name is too long", Toast.LENGTH_SHORT).show();
                    }
                    else if (!Session.currentUser.getEmail().contains("@")) {
                        Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    }
                    else if (Session.currentUser.getPassword().length() < 8) {
                        Toast.makeText(getApplicationContext(), "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                    }
                    else if (!chOkayPolicy.isChecked()) {
                        Toast.makeText(getApplicationContext(), "Please accept our terms and policy", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // If the email was already in DB, it will not allow to create a new account
                        Cursor cursor2 = db.rawQuery("SELECT Email FROM USER WHERE Email = ?", new String[] {Session.currentUser.getEmail()});
                        if (cursor2.moveToFirst()){
                            Toast.makeText(getApplicationContext(), "Email already exists, try another one", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ContentValues values = new ContentValues();
                        values.put("Name", Session.currentUser.getName().trim());
                        values.put("Email", Session.currentUser.getEmail().trim());
                        values.put("Password", Session.currentUser.getPassword().trim());
                        long result = db.insert("User", null , values);
                        if (result == -1) {
                            Toast.makeText(getApplicationContext(), "Account was not created", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent();
                            i.setClass(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }
                    }
                }
            }
        });

        // Animation to transfer between sign in and sign up screens
        tvSignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x[0] == 2){
                    lay.animate() .translationX(-100f) .alpha(0f) .setDuration(200) .withEndAction(() -> {

                        tvRegType.setText(R.string.signUpNow);
                        etName.setVisibility(View.VISIBLE);
                        tvPasswordConstraint.setVisibility(View.VISIBLE);
                        chOkayPolicy.setVisibility(View.VISIBLE);
                        bRegTypeButton.setText(R.string.signUp);
                        tvSignUpText.setText(R.string.alreadyHaveAccount);

                        SpannableString span1 = new SpannableString(tvSignUpText.getText());
                        span1.setSpan(new ForegroundColorSpan(Color.parseColor("#0083F6")), 25, 32, span1.SPAN_EXCLUSIVE_EXCLUSIVE);
                        span1.setSpan(new UnderlineSpan(), 25, 32, span1.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvSignUpText.setText(span1);

                        lay.setTranslationX(100f);
                        lay.animate()
                                .translationX(0f)
                                .alpha(1f)
                                .setDuration(200);
                    });
                    x[0] = 1;
                }
                else if (x[0] == 1){
                    lay.animate() .translationX(100f) .alpha(0f) .setDuration(200) .withEndAction(() -> {

                        tvRegType.setText(R.string.signInNow);
                        etName.setVisibility(View.GONE);
                        tvPasswordConstraint.setVisibility(View.GONE);
                        chOkayPolicy.setVisibility(View.GONE);
                        bRegTypeButton.setText(R.string.signIn);
                        tvSignUpText.setText(R.string.doNotHaveAccount);

                        SpannableString span2 = new SpannableString(tvSignUpText.getText());
                        span2.setSpan(new ForegroundColorSpan(Color.parseColor("#0083F6")), 23, 30, span2.SPAN_EXCLUSIVE_EXCLUSIVE);
                        span2.setSpan(new UnderlineSpan(), 23, 30, span2.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvSignUpText.setText(span2);

                        lay.setTranslationX(-100f);
                        lay.animate()
                                .translationX(0f)
                                .alpha(1f)
                                .setDuration(200);
                    });
                    x[0] = 2;
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registrationAct), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}