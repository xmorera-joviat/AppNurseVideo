package com.example.nurseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Classe que utilitzem per a tenir una pantalla d'inici, amb la seva imatge corresponent.
 */
public class PantallaInici extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inici);

        // Utilitzem l'interfície Runnable per tal d'aconseguir una espera de 3 segons amb el logo corresponent i
        // així crear un efecte de pantalla de càrrega.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }
}
