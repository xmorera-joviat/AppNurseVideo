package com.example.nurseapp.GestioVideos;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nurseapp.R;
import com.example.nurseapp.TractamentToolBar;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Classe en la qual tractem tot el relacionat en mostrar el text, descripció, enllaç als vídeos i mètodes que són utilitzats
 * des d'altes classes com per exemple eliminar vídeos.
 */
public class LlistatVideos extends TractamentToolBar {

    //Inicialització de les variables
    public static RecyclerView recycler;
    public static LlistatVideosAdapter llistatVideosAdapter;
    public static MostrarVideosAdapter mostrarVideosAdapter;
    private FirebaseUser user;
    private FirebaseFirestore fStore;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public SearchView searchView;
    private String LlistaVideosLlengua;
    private Spinner categoriaSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistat_videos);

        fStore = FirebaseFirestore.getInstance();

        Locale locale = new Locale(getIntent().getExtras().getString("llenguatge"));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        //Vinculem les variables amb els corresponents objectes de l'apartat gràfic.
        recycler = (RecyclerView) findViewById(R.id.idRecyvler);
        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(lim);
        recycler.setHasFixedSize(true);
        searchView = findViewById(R.id.search);
        data();

        //Mètode inicialitzaAdapter.
        SeleccionaCategoria();

        //Mètodes setUpToolBar i customTitileToolBar heretats de la classe TractamentToolBar.
        setUpToolBar();
        customTitileToolBar(getResources().getString(R.string.tlbvTitol));
    }


    /**
     * Amb l'estat onResume solucionem el problema que cada cop que canviem d'activity o canviem a l'estat onPuase
     * es dupliquin els items del llistat de vídeos.
     */
    @Override
    protected void onResume() {
        super.onResume();
        data();
    }

    /**
     * Mètode per a carregar els item corresponents de cada vídeo.
     */
    public void data() {
        //Instanciem de les variables firebaseDatabase i databaseReference.
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        switch(getIntent().getExtras().getString("llenguatge")){
            case "ca":
                LlistaVideosLlengua = "LlistatVideosCa";
                break;

            case  "es":
                LlistaVideosLlengua = "LlistatVideosEs";
                break;

            case  "en":
                LlistaVideosLlengua = "LlistatVideosEn";
                break;
        }

        //searchView.setOnQueryTextListener que utilitzem per poder fer cerques al llistat.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //Mètode Cerca.
                Cerca(s);

                return true;
            }
        });
    }

     private void SeleccionaCategoria() {
         ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(this,R.array.categorias, android.R.layout.simple_spinner_item);
         categoriaSpinner =findViewById(R.id.categoriaSpinner);
         categoriaSpinner.setAdapter(adp);
         categoriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 if (position == 0) {
                     inicialitzaAdapter();
                 }
                 else {
                     String[] arrayCategories = getResources().getStringArray(R.array.categorias);
                     Query consulta = databaseReference.child(LlistaVideosLlengua).orderByChild("categoria").equalTo(arrayCategories[position]);

                     // Preparem l'objecte "Options" que ens ha de permetre crear l'adapter. Aquest objecte
                     // defineix, entre altres aspectes, la consulta amb el tipus d'objecte que retornarà
                     // aquesta consulta (en el nostre cas, Videos).
                     FirebaseRecyclerOptions<Video> opcions =
                             new FirebaseRecyclerOptions
                                     .Builder<Video>()
                                     .setQuery(consulta, Video.class)
                                     .build();


                     // Creem l'objecte Adapter passant-li l'objecte Options al constructor.
                     mostrarVideosAdapter = new MostrarVideosAdapter(opcions);

                     // Associem l'adapter creat amb el RecyclerView que tenim a la vista.
                     recycler.setAdapter(mostrarVideosAdapter);
                     mostrarVideosAdapter.startListening();
                 }
             }
             @Override
             public void onNothingSelected(AdapterView<?> parent) { }
         });
     }

    /**
     * Mètode que utilitzem per a filtrar el llistat de vídeos.
     * @param s string
     */
    private void Cerca(String s) {
        // Preparem la consulta a realitzar a la base de dades.
        Query consulta = databaseReference.child(LlistaVideosLlengua)
                .orderByChild("titol")
                .startAt(s.toUpperCase())
                .endAt(s.toLowerCase()+"\uf8ff");

        // Preparem l'objecte "Options" que ens ha de permetre crear l'adapter. Aquest objecte
        // defineix, entre altres aspectes, la consulta amb el tipus d'objecte que retornarà
        // aquesta consulta (en el nostre cas, Videos).
        FirebaseRecyclerOptions<Video> opcions =
                new FirebaseRecyclerOptions
                        .Builder<Video>()
                        .setQuery(consulta, Video.class)
                        .build();


        // Creem l'objecte Adapter passant-li l'objecte Options al constructor.
        mostrarVideosAdapter = new MostrarVideosAdapter(opcions);

        // Associem l'adapter creat amb el RecyclerView que tenim a la vista.
        recycler.setAdapter(mostrarVideosAdapter);
        mostrarVideosAdapter.startListening();
    }

    /**
     * Mètode utilitzat per a inicialitzar l'adabter que hi té el List llsitatVideos.
     */
    public void inicialitzaAdapter(){
        Query consulta = databaseReference.child(LlistaVideosLlengua);

        // Preparem l'objecte "Options" que ens ha de permetre crear l'adapter. Aquest objecte
        // defineix, entre altres aspectes, la consulta amb el tipus d'objecte que retornarà
        // aquesta consulta (en el nostre cas, Alumne).
        FirebaseRecyclerOptions<Video> opcions =
                new FirebaseRecyclerOptions
                        .Builder<Video>()
                        .setQuery(consulta, Video.class)
                        .build();

        SetAdapter(opcions);
    }

    private void SetAdapter(FirebaseRecyclerOptions opcions) {
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Comprovem que no sigui null, i per tant, algú tingui la sessió iniciada.
        if (user != null) {
            DocumentReference df = fStore.collection("Users").document(user.getUid());

            // Extraiem les dades del document
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("rol") != null) {
                        if (documentSnapshot.getString("rol").equals("editor")) {
                            // Creem l'objecte Adapter passant-li l'objecte Options al constructor.
                            mostrarVideosAdapter = new MostrarVideosAdapter(opcions);

                            // Associem l'adapter creat amb el RecyclerView que tenim a la vista.
                            recycler.setAdapter(mostrarVideosAdapter);
                            mostrarVideosAdapter.startListening();
                        }
                        else if (documentSnapshot.getString("rol").equals("professional")) {
                            // Creem l'objecte Adapter passant-li l'objecte Options al constructor.
                            mostrarVideosAdapter = new MostrarVideosAdapter(opcions);

                            // Associem l'adapter creat amb el RecyclerView que tenim a la vista.
                            recycler.setAdapter(mostrarVideosAdapter);
                            mostrarVideosAdapter.startListening();
                        }
                        else {
                            // Creem l'objecte Adapter passant-li l'objecte Options al constructor.
                            llistatVideosAdapter = new LlistatVideosAdapter(opcions);

                            // Associem l'adapter creat amb el RecyclerView que tenim a la vista.
                            recycler.setAdapter(llistatVideosAdapter);
                            llistatVideosAdapter.startListening();
                        }
                    }
                    else {
                        // Creem l'objecte Adapter passant-li l'objecte Options al constructor.
                        llistatVideosAdapter = new LlistatVideosAdapter(opcions);

                        // Associem l'adapter creat amb el RecyclerView que tenim a la vista.
                        recycler.setAdapter(llistatVideosAdapter);
                        llistatVideosAdapter.startListening();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Creem l'objecte Adapter passant-li l'objecte Options al constructor.
                    llistatVideosAdapter = new LlistatVideosAdapter(opcions);

                    // Associem l'adapter creat amb el RecyclerView que tenim a la vista.
                    recycler.setAdapter(llistatVideosAdapter);
                    llistatVideosAdapter.startListening();
                }
            });
        }
        // Si es null, ficarem la llista dels pacients.
        else {
            // Creem l'objecte Adapter passant-li l'objecte Options al constructor.
            llistatVideosAdapter = new LlistatVideosAdapter(opcions);

            // Associem l'adapter creat amb el RecyclerView que tenim a la vista.
            recycler.setAdapter(llistatVideosAdapter);
            llistatVideosAdapter.startListening();
        }
    }

    /**
     * Mètode que utilitzem per a carregar la connexió a Firebase i eliminar l'ID indicat.
     * @param i Int.
     */
    public void DeleteVideo(int i){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Eliminem el vídeo de la llista de vídeos en català.
        DatabaseReference ref = database.getReference("LlistatVideosCa").child(String.valueOf(i));
        ref.removeValue();

        // Eliminem el vídeo de la llista de vídeos en anglès.
        ref = database.getReference("LlistatVideosEn").child(String.valueOf(i));
        ref.removeValue();

        // Eliminem el vídeo de la llista de vídeos en castellà.
        ref = database.getReference("LlistatVideosEs").child(String.valueOf(i));
        ref.removeValue();
    }

}
