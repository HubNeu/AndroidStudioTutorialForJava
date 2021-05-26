package com.example.zgadywanka;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        final int SPLASH_DISPLAY_LENGTH = 3000;

        //opóźniony handler aby po ustalonym czasie przejść do nowego ekranu
        new Handler().postDelayed(new Runnable() {
            @Override
            //obowiązkowa implementacja metody klasy Runnable
            public void run() {
                //ładujemy nasz nowy widok
                Intent i = new Intent(EntryActivity.this,
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}