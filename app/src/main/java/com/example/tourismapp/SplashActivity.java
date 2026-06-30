package com.example.tourismapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        Thread t = new Thread(){
            public void run(){
                try{
                    Thread.sleep(1000);
                }
                catch (Exception e){

                }
                finally {
                    Intent i = new Intent();
                    i.setClass(getApplicationContext(), OnboardingActivity.class);
                    startActivity(i);
                }
            }
        };
        t.start();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splashAct), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}