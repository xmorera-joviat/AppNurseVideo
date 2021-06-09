package com.example.nurseapp.GestioVideos;



import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nurseapp.R;
import com.example.nurseapp.TractamentToolBar;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Classe en la qual tractem tot el relacionat en mostrar el text, descripció, enllaç als vídeos i mètodes que són utilitzats
 * des d'altes classes com per exemple eliminar vídeos.
 */
public class LlistatVideos extends TractamentToolBar {

    //Inicialització de les variables
    public static List<Video> videos = new ArrayList<>();
    public static RecyclerView recycler;
    public static LlistatVideosAdapter adapter;
    public SearchView searchView;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    private String LlistaVideosLlengua;
    int numLastArrayList = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistat_videos);

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
        inicialitzaAdapter();

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

        videos.clear();
        data();
    }

    /**
     * Mètode per a carregar els item corresponents de cada vídeo.
     */
    public void data(){
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

        /*
        //Amb el databaseReference.child aconseguim tenir un ValueEventListener escoltant el que tenim a la base de dades de FiireBase
        // i així poder afegir-ho a la nostra List videos.
        databaseReference.child(LlistaVideosLlengua).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    /*
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        int id = Integer.parseInt(ds.child("numId").getValue().toString());
                        String titol = ds.child("titol").getValue().toString();
                        String descVideo = ds.child("descVideo").getValue().toString();
                        String urlVideo = ds.child("urlVideo").getValue().toString();
                        String categoria = ds.child("categoria").getValue().toString();

                        videos.add(new Video(id, titol, descVideo, urlVideo, categoria, true));
                    }

                    //Poder saber quin és l'ID corresponent per tenir un autoincrement dels IDs a la base de dades Firebase.
                    long count = (dataSnapshot.getChildrenCount());

                    int i = (int) count;

                    numLastArrayList = videos.get(i-1).getNumId();

                    adapter = new LlistatVideosAdapter(videos);
                    recycler.setAdapter(adapter);

                    inicialitzaAdapter();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */
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


    /**
     * Mètode que utilitzem per a filtrar el llistat de vídeos.
     * @param s string
     */
    private void Cerca(String s) {
        // Preparem la consulta a realitzar a la base de dades.
        Query consulta = databaseReference.child(LlistaVideosLlengua)
                .orderByChild("titol")
                .startAt(s)
                .endAt(s + "\uf8ff");

        // Preparem l'objecte "Options" que ens ha de permetre crear l'adapter. Aquest objecte
        // defineix, entre altres aspectes, la consulta amb el tipus d'objecte que retornarà
        // aquesta consulta (en el nostre cas, Videos).
        FirebaseRecyclerOptions<Video> opcions =
                new FirebaseRecyclerOptions
                        .Builder<Video>()
                        .setQuery(consulta, Video.class)
                        .build();

        // Creem l'objecte Adapter passant-li l'objecte Options al constructor.
        adapter = new LlistatVideosAdapter(opcions);

        // Associem l'adapter creat amb el RecyclerView que tenim a la vista.
        recycler.setAdapter(adapter);
    }

    /**
     * Mètode utilitzat per a inicialitzar l'adabter que hi té el List llsitatVideos.
     */
    public void inicialitzaAdapter(){
        Query consulta = databaseReference.child("LlistatVideosCa");

        // Preparem l'objecte "Options" que ens ha de permetre crear l'adapter. Aquest objecte
        // defineix, entre altres aspectes, la consulta amb el tipus d'objecte que retornarà
        // aquesta consulta (en el nostre cas, Alumne).
        FirebaseRecyclerOptions<Video> opcions =
                new FirebaseRecyclerOptions
                        .Builder<Video>()
                        .setQuery(consulta, Video.class)
                        .build();
        Toast.makeText(LlistatVideos.this, "Valor: "+ opcions.getClass().getCanonicalName(), Toast.LENGTH_LONG).show();
        // Creem l'objecte Adapter passant-li l'objecte Options al constructor.
        adapter = new LlistatVideosAdapter(opcions);

        // Associem l'adapter creat amb el RecyclerView que tenim a la vista.
        recycler.setAdapter(adapter);
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

        videos.clear();

        adapter.notifyDataSetChanged();
    }

    /**
     * Mètode per a retornar el últim nombre de la llista que conte els vídeos,
     * és utilitzat per a controlar l'autoincrement a la base de dades Firebase.
     * @return int numLastArrayList
     */
    public int getMidaLlista(){
        return numLastArrayList;
    }

}
