package com.example.nurseapp.TractamentVideos;



import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nurseapp.MainActivity;
import com.example.nurseapp.R;
import com.example.nurseapp.Registres_Acces.AccesUsuaris;
import com.example.nurseapp.TractamentGenericToolBar.TractamentToolBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Classe en la qual tractem tot el relacionat en mostrar el text, descripció, enllaç als vídeos i mètodes que són utilitzats
 * des d'altes classes com per exemple eliminar vídeos.
 */
public class LlistatVideosPrincipal extends TractamentToolBar {


    //Inicialització de les variables


    public static List<LlistatVideos> llistatVideos = new ArrayList<>();
    public static RecyclerView llistat;
    public static LlistatVideosAdabter adapter;
    public SearchView searchView;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    private String ListaVideosLen;
    int numLastArrayList = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistat_videos_principal);

        Locale locale = new Locale(getIntent().getExtras().getString("llenguatge"));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        //Vinculem les variables amb els corresponents objectes de l'apartat gràfic.

        llistat = (RecyclerView) findViewById(R.id.idRecyvler);
        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        llistat.setLayoutManager(lim);
        searchView = findViewById(R.id.search);

        //Mètode inicialitzaAdabter.

        inicialitzaAdabter();

        //Mètodes setUpToolBar i customTitileToolBar heretats de la classe TractamentToolBar.

        setUpToolBar();
        customTitileToolBar("Video");





    }


    /**
     * Amb l'estat onResume solucionem el problema que cada cop que canviem d'activity o canviem a l'estat onPuase
     * es dupliquin els items del llistat de vídeos.
     */
    @Override
    protected void onResume() {
        super.onResume();

        llistatVideos.clear();
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
                ListaVideosLen = "LlistatVideosCa";
                break;

            case  "es":
                ListaVideosLen = "LlistatVideosEs";
                break;

            case  "en":
                ListaVideosLen = "LlistatVideosEn";
                break;
        }
        //Amb el databaseReference.child aconseguim tenir un ValueEventListener escoltant el que tenim a la base de dades de FiireBase
        // i així poder afegir-ho a la nostra List llistatVideos.

        databaseReference.child(ListaVideosLen).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    for(DataSnapshot ds : dataSnapshot.getChildren()){


                        int id = Integer.parseInt(ds.child("numId").getValue().toString());
                        String titol = ds.child("texTitolVideos").getValue().toString();
                        String descVideo = ds.child("descVideo").getValue().toString();
                        String urlVideo = ds.child("urlVideo").getValue().toString();




                        llistatVideos.add(new LlistatVideos(id, titol, descVideo, urlVideo));




                    }


                    //Poder saber quin és l'ID corresponent per tenir un autoincrement dels IDs a la base de dades Firebase.

                    long count=(dataSnapshot.getChildrenCount());

                    int i = (int) count;

                    numLastArrayList = llistatVideos.get(i-1).getNumId();


                    adapter = new LlistatVideosAdabter(llistatVideos);
                    llistat.setAdapter(adapter);


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



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
     *
     * Mètode que utilitzem per a filtrar el llistat de vídeos.
     *
     * @param s string
     */
    private void Cerca(String s) {

        ArrayList<LlistatVideos> myList = new ArrayList<>();

        for(LlistatVideos videos : llistatVideos){


            if(videos.getTexTitolVideos().toLowerCase().contains(s.toLowerCase()) || videos.getDescVideo().toLowerCase().contains(s.toLowerCase())){

                myList.add(videos);

                adapter = new LlistatVideosAdabter(myList);
                llistat.setAdapter(adapter);
            }


        }




    }

    /**
     *
     * Mètode utilitzat per a inicialitzar l'adabter que hi té el List llsitatVideos.
     *
     */
    public void inicialitzaAdabter(){

        adapter = new LlistatVideosAdabter(llistatVideos);
        llistat.setAdapter(adapter);




    }


    /**
     *
     * Mètode que utilitzem per a carregar la connexió a Firebase i eliminar l'ID indicat.
     *
     * @param i Int.
     */
    public void DeleteVideo(int i){


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("LlistatVideos").child(String.valueOf(i));
        ref.removeValue();

        llistatVideos.clear();

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
