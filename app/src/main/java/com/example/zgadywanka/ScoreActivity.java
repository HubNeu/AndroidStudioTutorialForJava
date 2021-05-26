package com.example.zgadywanka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {

    TextView top1;
    TextView top2;
    TextView top3;
    TextView top4;
    TextView top5;
    Button graj;
    DatabaseHelper databaseHelper;
    Button doMenu;
    String nick;
    String score;
    int scoreVal = 0;

    //deklaracja obiektów
    List<Entry> wyniki = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //łączenie widoku i kodu
        top1 = (TextView) findViewById(R.id.wynik1);
        top1.setText("");
        top2 = (TextView) findViewById(R.id.wynik2);
        top2.setText("");
        top3 = (TextView) findViewById(R.id.wynik3);
        top3.setText("");
        top4 = (TextView) findViewById(R.id.wynik4);
        top4.setText("");
        top5 = (TextView) findViewById(R.id.wynik5);
        top5.setText("");
        graj = (Button) findViewById(R.id.przyciskGraj);
        graj.setOnClickListener(v -> {
            //Przejdź do menu głównego
            Intent i = new Intent(ScoreActivity.this,
                    MainActivity.class);
            startActivity(i);
            finish();
        });

        //operacje na bazie danych
        databaseHelper = new DatabaseHelper(this);

        if (nick == null){
            nick = this.getIntent().getStringExtra("nick");
        }
        if (score == null) {
            score = this.getIntent().getStringExtra("wynik");
            scoreVal = Integer.parseInt(score);
        }

        odczytajPozycje();
        dodajNowyRekord();
        posortuj();
        zapiszNajlepsze();
        uzupelnijListe();
    }

    private void uzupelnijListe() {
        try {
            top1.setText(wyniki.get(0).getName() + " (wynik:" + wyniki.get(0).getScore() + ")");
            top2.setText(wyniki.get(1).getName() + " (wynik:" + wyniki.get(1).getScore() + ")");
            top3.setText(wyniki.get(2).getName() + " (wynik:" + wyniki.get(2).getScore() + ")");
            top4.setText(wyniki.get(3).getName() + " (wynik:" + wyniki.get(3).getScore() + ")");
            top5.setText(wyniki.get(4).getName() + " (wynik:" + wyniki.get(4).getScore() + ")");
        } catch (Exception e) {
            //nie rób nic
        }
    }

    private void odczytajPozycje() {
        //SELECT * FROM NAMES
        Cursor places = databaseHelper.pullData();
        while (places.moveToNext()) {
            //pobierz nastepny wynik i dodaj do wynikow
            wyniki.add(new Entry(places.getString(1), places.getInt(2)));
        }
    }

    private void dodajNowyRekord() {
        //dodaj do wyników nowy rekord
        wyniki.add(new Entry(nick,scoreVal));
    }

    private void posortuj() {
        Collections.sort(wyniki, new Comparator<Entry>() {
            @Override
            public int compare(Entry o1, Entry o2) {
                return o2.getScore() - o1.getScore();
            }
        });
    }

    private void zapiszNajlepsze() {
        //usuń wszystkie dane z tabeli
        databaseHelper.deleteAll();
        //zapisz do tabeli jeszcze raz, ale top 5 posortowanych wyników
        try {
            for (int i=0;i<5;i++){
                databaseHelper.addData(wyniki.get(i).getName(),wyniki.get(i).getScore());
            }
        } catch (Exception e) {
            //nie rob nic
        }
    }
}