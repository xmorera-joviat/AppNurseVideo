package com.example.nurseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.nurseapp.TractamentGenericToolBar.TractamentToolBar;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class EditarRols extends TractamentToolBar {

    private FirebaseFirestore db;
    private AdapterEditarRols adapter;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_rols);

        // Executem el mètode setUpToolBar
        setUpToolBar();

        // Executem el mètode customTitileTool, passant com a paràmetre el nom que volem indicar.
        customTitileToolBar(getResources().getString(R.string.btnEditarRols));

        // Obtenim les referències necessàries als components de la interfície.
        recycler = (RecyclerView) findViewById(R.id.idRecyclerRols);

        // Assignem al recycler que volem mostrar l'informació una columna lineal
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // Obtenim una instància d'accés a la base de dades Firestore.
        db = FirebaseFirestore.getInstance();

        // Cridem el mètode encarregat de mostrar la llista d'artistes.
        MostrarUsuaris();
    }

    private void MostrarUsuaris() {
        // Preparem la consulta que obtindrà les dades a visualitzar en el RecyclerView.
        Query consulta = db.collection("Users");

        // Preparem l'objecte "Options" que ens ha de permetre crear l'adapter. Aquest objecte
        // defineix, entre altres aspectes, la consulta amb el tipus d'objecte que retornarà
        // aquesta consulta (en el nostre cas, Alumne).
        FirestoreRecyclerOptions<UserInfo> opcions =
                new FirestoreRecyclerOptions
                        .Builder<UserInfo>()
                        .setQuery(consulta, UserInfo.class)
                        .build();

        // Creem l'objecte Adapter passant-li l'objecte Options al constructor.
        adapter = new AdapterEditarRols(opcions);

        // Associem l'adapter creat amb el RecyclerView que tenim a la vista.
        recycler.setAdapter(adapter);
    }

}