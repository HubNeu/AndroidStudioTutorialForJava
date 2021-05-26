package com.example.zgadywanka;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    TextView iloscZgadniec;
    TextView errorTekstGra;
    Button zgadnij;
    TextView poleHasla;
    EditText poleDoWpisywania;

    String haslo;
    String nick;
    String zakodowaneHaslo;
    int licznikZgadniec = 0;
    String licznikConst = "Ilość zgadnięć: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //połaczenie elementów z ekranu z instancjami w kodzie
        iloscZgadniec = (TextView) findViewById(R.id.tekstIloscZgadniec);
        zgadnij = (Button) findViewById(R.id.przyciskZgadnij);
        poleHasla = (TextView) findViewById(R.id.hasloDisplay);
        errorTekstGra = (TextView) findViewById(R.id.errorTekstGra);
        poleDoWpisywania = (EditText) findViewById(R.id.editZgadnij);

        //pobieramy haslo
        haslo = getIntent().getStringExtra("haslo").toUpperCase();
        nick = getIntent().getStringExtra("nick");

        //zakodowane haslo w postaci "___ ____ _ ___"
        zakodowaneHaslo = getZakodowaneHaslo(haslo);

        //ustaw odpowiednie wartości na ekranie
        iloscZgadniec.setText(licznikConst + String.valueOf(licznikZgadniec));
        poleHasla.setText(zakodowaneHaslo);
        //mały trik zwiększający odstępy między literami
        poleHasla.setLetterSpacing(0.3f);

        //zaimplementowane funkcjonalności przycisku zgadnij
        zgadnij.setOnClickListener(v -> {
            //ukryj tekst błędu jeśli taki był wcześniej ustawiony
            errorTekstGra.setText("");

            //pobierz wpisane dane z pola tekstowego
            String inp = poleDoWpisywania.getText().toString().toUpperCase();

            //walidacja tekstu
            //jeśli tekst zawiera cyfrę to jest nieprawidłowy
            if (sprawdzCzyCyfry(inp)){
                //jesli zawiera cyfrę to jest nieprawidłowe
            } else if (inp.length() == 1) {
                //jeśli jego długość jest równa 1 to odkrywamy literę
                zakodowaneHaslo = odkryjLitere(zakodowaneHaslo, haslo, inp);
                //inkrementujemy licznik zgadnięć
                licznikZgadniec++;
                poleHasla.setText(zakodowaneHaslo);
            } else if (inp.length() == zakodowaneHaslo.length()) {
                //jesli input ma równą liczbę danych, to sprobuj zgadnąć hasło
                if (haslo.equals(inp)) {
                    //pokaż dialog o końcu gry
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Wygrana!");
                    builder.setMessage("Gratulacje! Zgadnięto hasło:\n" + haslo);
                    builder.setPositiveButton("Super!", (dialog, which) -> {
                        //przenieś do ekranu początkowego
                        Intent i = new Intent(GameActivity.this,
                                ScoreActivity.class);
                        i.putExtra("nick", nick);
                        String score = String.valueOf((int) (100*((float)haslo.length())/((float)licznikZgadniec)));
                        i.putExtra("wynik", score);
                        //zainicjalizuj następny ekran
                        startActivity(i);
                        //uprzątnij obecny ekran aby nie zajmował pamięci
                        finish();
                    });
                    //pokaż komunikat
                    builder.show();
                } else {
                    //wpisane haslo nie jest zgodne
                    licznikZgadniec++;
                    errorTekstGra.setText("Hasło nie jest zgodne!");
                }
            }
            //Na koniec uaktualnimy wszystkie napisy
            //ustaw odpowiednie wartości na ekranie
            iloscZgadniec.setText(licznikConst + String.valueOf(licznikZgadniec));
            poleHasla.setText(zakodowaneHaslo);
            //wyczyścimy pole do wpisywania
            poleDoWpisywania.getText().clear();
            // alternatywnie można użyć: poleDoWpisywania.setText("");
        });
    }

    private String odkryjLitere(String docelowe, String odkodowane, String litera) {
        char[] chrTrg = docelowe.toCharArray();
        char[] chrSrc = odkodowane.toCharArray();
        char chr = litera.charAt(0);
        for (int i = 0; i<chrTrg.length; i++){
            if (chrSrc[i] == chr) {
                chrTrg[i] = chr;
            }
        }
        return new String(chrTrg);
    }

    private String getZakodowaneHaslo(String arg){
        //stworzymy obraz hasla ktory zamiast liter bedzie mial podkreslniki
        char[] arr = new char[arg.length()];

        //aby nie pokazywac uzytkownikowi hasla
        for (int i = 0; i< arg.length(); i++){
            //sprawdzamy czy znak jest literą czy spacją
            if (arg.charAt(i) == ' '){
                //znak jest spacja
                arr[i] = arg.charAt(i);
            } else {
                arr[i] = '_';
            }
        }
        return new String(arr);
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