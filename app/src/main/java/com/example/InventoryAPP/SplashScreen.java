package com.example.InventoryAPP;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread thread= new Thread(){
            public void run(){
                try {
                    sleep(7000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    Intent goToMainActivity = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(goToMainActivity);
                    finish();
                }
            }
        };
        thread.start();
    }
}