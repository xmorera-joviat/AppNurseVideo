package com.example.nurseapp.GestioVideos;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.nurseapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

/**
 * Classe utilitzada per afegir títol del vídeo, descripció, url i ID, l'ID és autonumèric. A la base de dades Firebase.
 */
public class AfegirVideos extends LlistatVideos {

    // Inicialització de les variables :
    DatabaseReference ref;
    LlistatVideosAdapter llista;
    int ultimID = 0;
    Video video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_videos);

        Locale locale = new Locale(getIntent().getExtras().getString("llenguatge"));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;

        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction t = manager.beginTransaction();

        FragmentAfegirVideos f = new FragmentAfegirVideos();

        ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("LlistatVideosCa").orderByChild("numId").limitToLast(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    ultimID = ds.child("numId").getValue(Integer.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Executem el mètode setUpToolBar
        setUpToolBar();

        // Executem el mètode customTitileToolBar amb el seu títol corresponent.
        customTitileToolBar(getResources().getString(R.string.tlbafTitol));
    }

    /**
     * Mètode encarregat de comprovar les dades introduïdes per l'usuari,
     * i en cas de ser correctes, afegir-les a la base de dades.
     */
    public void Afegir(String titolCa, String descCa, String urlCa, String categoriaCa,
                       String tituloEs, String descEs, String urlEs, String categoriaEs,
                       String titleEn, String descEn, String urlEn, String categoryEn )
    {
        // Fem una petita validació per saber si algun dels caps està buit.
        if (titolCa.equals("") || descCa.equals("") || urlCa.equals("") ||
                tituloEs.equals("") || descEs.equals("") || urlEs.equals("") ||
                titleEn.equals("") || descEn.equals("") || urlEn.equals("") )
        {
            Toast.makeText(getApplicationContext(), "Omple els camps obligatoris, siusplau!", Toast.LENGTH_LONG).show();
        }
        // En el cas que tot sigui correcte, hem de validar si l'url conte "be/" o "?v",
        // ja que si no és així, no es tracta d'una url acceptada i per tal el vídeo no es podrà reproduir.
        else {
            int inici = 0;

            if (urlCa.contains("be/")) {
                inici = urlCa.indexOf("be/");
            } else if (urlCa.contains("?v")) {
                inici = urlCa.indexOf("?v");
            }

            if ((urlCa.contains("be/") || urlCa.contains("?v")) &&
                    (urlEs.contains("be/") || urlEs.contains("?v")) &&
                    (urlEn.contains("be/") || urlEn.contains("?v"))) {

                ultimID +=1;

                Video Cat = new Video();

                Cat.setNumId(ultimID);
                Cat.setTitol(titolCa);
                Cat.setDescVideo(descCa);
                Cat.setUrlVideo(urlCa.substring(inici + 3, inici + 14));
                Cat.setCategoria(categoriaCa);
                Cat.setMostrar(1);

                // Obtenim la referència del fill que tenim a la base de dades.
                ref.child("LlistatVideosCa").child(String.valueOf(ultimID)).setValue(Cat);

                Video Esp = new Video();

                Esp.setNumId(ultimID);
                Esp.setTitol(tituloEs);
                Esp.setDescVideo(descEs);
                Esp.setUrlVideo(urlEs.substring(inici + 3, inici + 14));
                Esp.setCategoria(categoriaEs);
                Esp.setMostrar(1);

                // Obtenim la referència del fill que tenim a la base de dades.
                ref.child("LlistatVideosEs").child(String.valueOf(ultimID)).setValue(Esp);

                Video Eng = new Video();

                Eng.setNumId(ultimID);
                Eng.setTitol(titleEn);
                Eng.setDescVideo(descEn);
                Eng.setUrlVideo(urlEn.substring(inici + 3, inici + 14));
                Eng.setCategoria(categoryEn);
                Eng.setMostrar(1);

                // Obtenim la referència del fill que tenim a la base de dades.
                ref.child("LlistatVideosEn").child(String.valueOf(ultimID)).setValue(Eng);

                // Si tot es correcte afegim el vídeo a la base de dades Firebase.
                Toast.makeText(getApplicationContext(), "Vídeo afegit", Toast.LENGTH_LONG).show();
            }
            else {
                // Si l'url no és correcta, no s'enregistrarà el vídeo i es mostrarà un avís d'error.
                Toast.makeText(getApplicationContext(), "URL no acceptada, revisa-la!", Toast.LENGTH_LONG).show();
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
    }
}
