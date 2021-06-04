package com.example.nurseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nurseapp.Formularis_Calendari.ActivitatCalendari;
import com.example.nurseapp.Formularis_Calendari.ActivitatFormularis;
import com.example.nurseapp.Registres_Acces.AccesUsuaris;
import com.example.nurseapp.TractamentVideos.LlistatVideosPrincipal;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Activitat que controla la pantalla principal.
 */
public class MainActivity extends AppCompatActivity {

    // Inicialització de les variables :
    private Button IdBtnVideos;
    private Button idBtnCalendari;
    private Button idBtnFormularis;
    private Toolbar toolbar;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Vinculem les variables amb els corresponents objectes de l'apartat gràfic.
        IdBtnVideos = findViewById(R.id.idBtnVideos);
        idBtnCalendari = findViewById(R.id.idBtnCalendari);
        idBtnFormularis = findViewById(R.id.idBtnFormulari);

        // Executem el mètode setUpToolBar
        setUpToolBar();

        // Executem el mètode customTitileToolBar
        customTitileToolBar();

        // Botó per tal d'obrir l'activitat corresponent al calendari.
        idBtnCalendari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentetCalen = new Intent(getApplicationContext(), ActivitatCalendari.class);
                intentetCalen.putExtra("llenguatge", getResources().getString(R.string.llenguatge));
                startActivity(intentetCalen);
            }
        });

        // Botó per tal d'obrir l'activitat corresponent als formularis.
        idBtnFormularis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentetForms = new Intent(getApplicationContext(), ActivitatFormularis.class);
                intentetForms.putExtra("llenguatge",getResources().getString(R.string.llenguatge));
                startActivity(intentetForms);
            }
        });



    }

    private void CheckUserRole(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);

        // Extraiem les dades del document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.getString("rol") != null) {
                    if (documentSnapshot.getString("rol").equals("editor")) {
                        setContentView(R.layout.activity_main_editor);
                    }
                    else if (documentSnapshot.getString("rol").equals("professional")) {
                        setContentView(R.layout.activity_main_professional);
                    }
                    else {
                        setContentView(R.layout.activity_main_pacient);
                    }
                }
                else {
                    setContentView(R.layout.activity_main_pacient);
                }
            }
        });

    }

    /**
     * Mètode que utilitzem per a obrir l'activitat corresponent als vídeos.
     *
     * @param v View
     */
    public void onClickBtnVideos(View v) {
        Intent video = new Intent(this, LlistatVideosPrincipal.class );
        video.putExtra("llenguatge",getResources().getString(R.string.llenguatge));
        startActivity(video);
    }

    /**
     * Mètode que utilitzem per a activar una toolbar personalitzada.
     */
    public void setUpToolBar() {
        toolbar = findViewById(R.id.idToolBar);
        setSupportActionBar(toolbar);
        showHomeUpIcon();
        setUpHomeUpIconColor(R.drawable.ic_home, R.color.white);
    }

    /**
     * Mètode que utilitzem per mostrar el botó de home
     */
    public void showHomeUpIcon() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Mètode per tal d'aplicar el color que necessitem a les nostres icones.
     *
     * @param drawable icona
     * @param color    color per a l'icona
     */
    public void setUpHomeUpIconColor(int drawable, int color) {
        if (getSupportActionBar() != null) {
            final Drawable icon = getResources().getDrawable(drawable);
            icon.setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(icon);
        }
    }

    /**
     * Mètode que utilitzem per a fer l'inflater del menú.
     *
     * @param menu menu
     * @return menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Mètode que controla quin botó del menú s'ha seleccionat per tal d'obrir la corresponent activity.
     * <p>
     * També controla el text que hi ha en el centre de la toolbar en aquesta activity.
     *
     * @param item MenuItem
     * @return ItemSelected
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent i = new Intent(this, MainActivity.class );
                startActivity(i);
                break;
            case R.id.idAcces:
                Intent acces = new Intent(this, AccesUsuaris.class );
                acces.putExtra("llenguatge", getResources().getString(R.string.llenguatge));
                startActivity(acces);
                break;
            case  R.id.idCatala:
                CanviarLlenguatge("ca");
                break;
            case  R.id.idCastella:
                CanviarLlenguatge("es");
                break;
            case  R.id.idAngles:
                CanviarLlenguatge("en");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void CanviarLlenguatge(String len) {
        // Configurar lenguaje actual de local.
        Locale locale = new Locale(len);
        Locale.setDefault(locale);

        // Solicita al sistema que actualice el local del sistema.
        Configuration config = new Configuration();
        config.locale = locale;

        // Actualizar recursos lenguaje de app.
        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        Intent refrescar = new Intent(this, MainActivity.class);
        startActivity(refrescar);
        finish();
    }

    private void customTitileToolBar() {
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            TextView textView = toolbar.findViewById(R.id.toolbar_title);

            textView.setText(getResources().getString(R.string.tlbiTitul));
        }
    }
}
