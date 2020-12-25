package com.example.githubdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();

        Runnable run = new Runnable() {

            public void run() {
                // TODO Auto-generated method stub
                startActivity(new Intent(SplashActivity.this, SearchUserActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        };

        handler.postDelayed(run, 3000);
    }
}