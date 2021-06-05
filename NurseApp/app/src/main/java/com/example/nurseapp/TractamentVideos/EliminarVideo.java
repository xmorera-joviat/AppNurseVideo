package com.example.nurseapp.TractamentVideos;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nurseapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Classe per a tractar l'activity d'eliminar els vídeos.
 */
public class EliminarVideo extends LlistatVideosPrincipal {

    // Inicialització de les variables :
    private TextView idTextTitol;
    private TextView idTextDesc;
    private Spinner idSpinner;
    private Button idButtonDelete;
    private Button idBtnVideosEliminar;
    DatabaseReference mDataBase;
    private int numFilaSnipper = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_video);

        Locale locale = new Locale(getIntent().getExtras().getString("llenguatge"));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;

        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        // Executem el mètode setUpToolBar
       setUpToolBar();

        // Executem el mètode customTitileToolBar amb el títol corresponent.
        customTitileToolBar(getResources().getString(R.string.tlbevTitol));

        // Vinculem les variables amb els corresponents objectes de l'apartat gràfic.
        idTextTitol = findViewById(R.id.idTextVideosEliminar);
        idTextDesc = findViewById(R.id.idDescElimnar);
        idSpinner = findViewById(R.id.spinnerVideos);
        idButtonDelete = findViewById(R.id.idButtonDelete);
        idBtnVideosEliminar = findViewById(R.id.idBtnVideosEliminar);

        // Obtenim la referència de la base de dades en Firebase.
        mDataBase = FirebaseDatabase.getInstance().getReference();

        // Executem el mètode showVideos.
        ShowVideos();
    }

    /**
     *
     * Mètode on carreguem en un Spinner tots els títols de cada vídeo que tenim a la base de dades de Firebase.
     *
     * Per cada item del spinner que hem seleccionat, carreguem el títol,
     * la descripció i vinculem l'url del vídeo corresponent al botó de play, per tal de poder reproduir el vídeo seleccionat.
     *
     * També tenim un OnClick de tal forma que quan estem segurs de l'element que volem esborrar, seleccionant
     * el botó d'esborrar eliminem el registre en qüestió.
     *
     *
     * Per últim, cada cop que fem una eliminació hem de recarregar l'activity,
     * ja que si no l'Spinner no s'actualitza. Per tal que la recàrrega de l'activity no tingui un salt amb la imatge
     * utilitzem finish, overridePendingTransition i startActivity.
     *
     */
    public void ShowVideos(){
        final List<LlistatVideos> llistatVideos = new ArrayList<>();

        mDataBase.child("LlistatVideos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        int id = Integer.parseInt(ds.child("numId").getValue().toString());
                        String titol = ds.child("texTitolVideos").getValue().toString();
                        String descVideo = ds.child("descVideo").getValue().toString();
                        String urlVideo = ds.child("urlVideo").getValue().toString();

                        llistatVideos.add(new LlistatVideos(id, titol, descVideo, urlVideo));

                        ArrayAdapter<LlistatVideos> arrayAdapter = new ArrayAdapter<>
                                (EliminarVideo.this, android.R.layout.simple_dropdown_item_1line,llistatVideos);

                        idSpinner.setAdapter(arrayAdapter);

                        idSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, final long id) {

                                numFilaSnipper = parent.getSelectedItemPosition();

                                idTextTitol.setText(llistatVideos.get(numFilaSnipper).getTexTitolVideos());
                                idTextDesc.setText(llistatVideos.get(numFilaSnipper).getDescVideo());

                                idBtnVideosEliminar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        Intent i = new Intent(v.getContext(), ApiYoutube.class);
                                        Bundle b = new Bundle();

                                        b.putString("url", llistatVideos.get(numFilaSnipper).getUrlVideo());
                                        i.putExtras(b);

                                        v.getContext().startActivity(i);

                                    }
                                });



                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        idButtonDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int i = llistatVideos.get(numFilaSnipper).getNumId();

                                DeleteVideo(i);

                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);

                                Toast.makeText(getApplicationContext(), "Vídeo eliminat", Toast.LENGTH_LONG).show();

                            }
                        });

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
