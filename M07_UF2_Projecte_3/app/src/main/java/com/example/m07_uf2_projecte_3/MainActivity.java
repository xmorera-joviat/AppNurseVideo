package com.example.m07_uf2_projecte_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //Declaració de les variables necessàries.
    TextView tv_prova;
    Button btn_aplica, btn_add;
    FirebaseDatabase database;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Realitzem la conexió al Firebase i busquem i assignem les variables de l'entorn gràfic.
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("jugadors");

        tv_prova = findViewById(R.id.tv_prova);
        btn_aplica = findViewById(R.id.btn_ap);
        btn_add = findViewById(R.id.btn_add);

    }

    public void btnConsulta(View v) {

        // Associem un listener per gestionar la lectura de les ratafies.
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // El paràmetre DataSnapshot ens dóna accés al node consultat. En
                // aquest cas consultem els nodes fill que són els objectes Ratafia.
                Iterable<DataSnapshot> lr = dataSnapshot.getChildren();
                for (DataSnapshot d : lr) {
                    //Log.e("VALORS: ", d.getKey() + " -> " + d.getValue());
                    Jugadors j = d.getValue(Jugadors.class);
                    //Log.e("NOM: ", j.getNomJug());
                    tv_prova.setText(tv_prova.getText() + "\n" + j.getNomJug());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tv_prova.setText("Error llegint BD: " + databaseError.getCode());
            }
        });

    }

    //Mètode per anar a l'activity d'afegir un jugador nou.
    public void AfegirJugador (View v) {

        Intent intent = new Intent(this, AddJugador.class);
        startActivity(intent);
    }
}
