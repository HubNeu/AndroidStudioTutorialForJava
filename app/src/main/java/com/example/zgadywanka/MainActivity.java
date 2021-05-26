package com.example.zgadywanka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button przyciskGraj;
    EditText editHasloDoZgadniecia;
    EditText editNick;
    TextView errorTekst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //odniesienie sie do pola wejsciowego
        editHasloDoZgadniecia = (EditText) findViewById(R.id.editHasloDoZgadniecia);
        editNick = (EditText) findViewById(R.id.editNick);
        //odniesienie do pola tekstowego
        errorTekst = (TextView) findViewById(R.id.errorTekst);
        //odniesienie do przycisku
        przyciskGraj = (Button) findViewById(R.id.przyciskGraj);

        //dodajemy funkcjonalność do przycisku za pomoca event listener'a
        przyciskGraj.setOnClickListener(v -> {
            //kod ktory zostanie wykonany po nacisnieciu
            String haslo = String.valueOf(editHasloDoZgadniecia.getText());
            String nick = String.valueOf(editNick.getText());
            if (nick.length() == 0) {
                nick = "Brak";
            }
            if (haslo.length() < 3) {
                //haslo jest zbyt krotkie
                errorTekst.setText("Haslo zbyt krotkie!");

            } else if(sprawdzCzyCyfry(haslo)) {
                //haslo zawiera cyfry
                errorTekst.setText("Haslo nie może mieć cyfr!");

            } else {
                //haslo jest ok, mozemy przejsc do nastepnego ekranu
                errorTekst.setText("");
                //Przygotowanie przejścia do nowego ekranu za pomocą klasy Intent
                Intent i = new Intent(MainActivity.this,
                        GameActivity.class);
                //Dodaj do następnego ekranu nasze hasło jako zmienną
                i.putExtra("haslo", haslo);
                i.putExtra("nick", nick);
                //zainicjalizuj następny ekran
                startActivity(i);
                //uprzątnij obecny ekran aby nie zajmował pamięci
                finish();
            }
        });
    }

    private boolean sprawdzCzyCyfry(String haslo) {
        // Uzyjemy wrappera Character
        // aby w prosty sposob przeiterowac po kazdym znaku
        // oraz sprawdzic czy nie jest cyfra
        // jesli jest, mozemy przestac iterowac i zwrocic true
        char[] chars = haslo.toCharArray();
        for(char c : chars){
            if(Character.isDigit(c)){
                return true;
            }
        }
        return false;
    }
}