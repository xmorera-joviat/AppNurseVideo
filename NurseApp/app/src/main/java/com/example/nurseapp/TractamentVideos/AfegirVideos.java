package com.example.nurseapp.TractamentVideos;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nurseapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

/**
 * Classe utilitzada per afegir títol del vídeo, descripció, url i ID, l'ID és autonumèric. A la base de dades Firebase.
 */
public class AfegirVideos extends LlistatVideos {

    // Inicialització de les variables :
    private EditText idNom;
    private EditText idDesc;
    private EditText idUrl;
    private Button btnSave;
    FirebaseDatabase database;
    DatabaseReference ref;
    private boolean isInsert = false;
    long count = 0;
    int numLlista = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_videos);

        Locale locale = new Locale(getIntent().getExtras().getString("llenguatge"));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;

        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Vinculem les variables amb els corresponents objectes de l'apartat gràfic.
        idNom = findViewById(R.id.idNom);
        idDesc = findViewById(R.id.idDesc);
        idUrl = findViewById(R.id.idUrl);
        btnSave = findViewById(R.id.idButtonSave);

        // Executem el mètode setUpToolBar
        setUpToolBar();

        // Executem el mètode customTitileToolBar amb el seu títol corresponent.
        customTitileToolBar(getResources().getString(R.string.tlbafTitol));

        // Obtenim la referència del fill que tenim a la base de dades.
        ref = database.getInstance().getReference().child("Video");

        // Botó onClick per ta d'afegir les dades a la base de dades.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomVideo = idNom.getText().toString();
                String descVideo = idDesc.getText().toString();
                String url = idUrl.getText().toString();

                data();

                // Número del següent ID.
                numLlista = getMidaLlista();

                numLlista++;

                // Fem una petita validació per saber si algun dels caps està buit.
                switch (v.getId()) {
                    case R.id.idButtonSave: {
                        if (nomVideo.equals("") || descVideo.equals("") || url.equals("")) {
                            validar();
                        } else {
                            // En el cas que tot sigui correcte, hem de validar si l'url conte "be/" o "?v",
                            // ja que si no és així, no es tracta d'una url acceptada i per tal el vídeo no es podrà reproduir.
                            int inici = 0;
                            if (url.contains("be/")) {

                                inici = url.indexOf("be/");
                            } else if (url.contains("?v")) {
                                inici = url.indexOf("?v");
                            }

                            Video lv = new Video();

                            if (url.contains("be/") || url.contains("?v")) {
                                lv.setNumId(numLlista);
                                lv.setTitol(nomVideo);
                                lv.setDescVideo(descVideo);

                                lv.setUrlVideo(url.substring(inici + 3, inici + 14));

                                ref.child(String.valueOf(numLlista)).setValue(lv);

                                // Si tot es correcte afegim el vídeo a la base de dades Firebase.
                                Toast.makeText(getApplicationContext(), "Vídeo afegit", Toast.LENGTH_LONG).show();
                                netejar();
                            }
                            else {
                                // Si l'url no és correcta, no s'enregistrarà el vídeo i es mostrarà un avís d'error.
                                Toast.makeText(getApplicationContext(), "Revisa la URL", Toast.LENGTH_LONG).show();
                            }

                            // Cada cop que fem un registre hem de recarregar l'activity, ja que si no el comptador del ID no
                            // s'incrementa y això ens donarà problemes a l'hora d'intentar realitzar el
                            // següent registre, per tal que la recàrrega de l'activity no tingui un salt amb la imatge
                            // utilitzem finish, overridePendingTransition i startActivity.
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);

                        }
                        break;
                    }
                }
            }
        });
    }

    /**
     * Mètode que comprova que els camps no estiguis buits.
     */
    private void validar() {
        String nomVideo = idNom.getText().toString();
        String descVideo = idDesc.getText().toString();
        String url = idUrl.getText().toString();

        if (nomVideo.equals("")) {
            Toast.makeText(getApplicationContext(), "Tots els camps han de tenir text", Toast.LENGTH_LONG).show();
        }
        else if (descVideo.equals("")) {
            Toast.makeText(getApplicationContext(), "Tots els camps han de tenir text", Toast.LENGTH_LONG).show();
        }
        else if (url.equals("")) {
            Toast.makeText(getApplicationContext(), "Tots els camps han de tenir text", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Mètode per deixar els camps de text buits cada cop que es realitza un registre o hi hagi un error.
     */
    private void netejar() {
        idNom.setText("");
        idDesc.setText("");
        idUrl.setText("");
    }
}
