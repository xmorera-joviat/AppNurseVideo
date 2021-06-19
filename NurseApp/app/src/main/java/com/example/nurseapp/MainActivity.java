package com.example.nurseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nurseapp.Autentificacio.Registrarse;
import com.example.nurseapp.TractamentRols.EditarRols;
import com.example.nurseapp.GestioVideos.EliminarVideo;
import com.example.nurseapp.Formularis_Calendari.ActivitatCalendari;
import com.example.nurseapp.Formularis_Calendari.ActivitatFormularis;
import com.example.nurseapp.Autentificacio.IniciarSessio;
import com.example.nurseapp.GestioVideos.AfegirVideos;
import com.example.nurseapp.GestioVideos.LlistatVideos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

/**
 * Activitat que controla la pantalla principal.
 */
public class MainActivity extends AppCompatActivity {

    // Inicialització de les variables :
    private Button idBtnCalendari;
    private Button idBtnFormularis;
    private Toolbar toolbar;
    private FirebaseFirestore fStore;
    private boolean loggedIn;
    private MenuItem logIn, register, logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creem una instància de la base de dades de firebase cloudstore.
        fStore = FirebaseFirestore.getInstance();

        // Agafem les dades de l'usuari.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Comprovem que no sigui null, i per tant, algú tingui la sessió iniciada.
        if (user != null) {
            loggedIn = true;
            CheckUserRole(user.getUid());
        }
        // Si es null, ficarem la vista dels pacients.
        else {
            loggedIn = false;
            setContentView(R.layout.activity_main_pacient);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Executem el mètode setUpToolBar
                setUpToolBar();

                // Executem el mètode customTitileToolBar
                customTitileToolBar();
            }
        }, 1000);

    }

    /**
     * Mètode per comprovar quin tipus de rol té l'usuari i per tant mostrar les
     * opcions per pantalla corresponents al seu privilegi.
     * @param uid Variable de tipus String, és l'identificador de l'usuari.
     */
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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                setContentView(R.layout.activity_main_pacient);
            }
        });
    }

    /**
     * Mètode que utilitzem per a obrir l'activitat del calendari.
     * @param v View
     */
    public void onClickBtnCalendari(View v) {
        Intent calendari = new Intent(getApplicationContext(), ActivitatCalendari.class);
        calendari.putExtra("llenguatge", getResources().getString(R.string.llenguatge));
        startActivity(calendari);
    }

    /**
     * Mètode que utilitzem per a obrir l'activitat per a editar rols.
     * @param v View
     */
    public void onClickBtnEditarRols(View v) {
        Intent rols = new Intent(getApplicationContext(), EditarRols.class);
        rols.putExtra("llenguatge", getResources().getString(R.string.llenguatge));
        startActivity(rols);
    }

    /**
     * Mètode que utilitzem per a obrir l'activitat del formulari.
     * @param v View
     */
    public void onClickBtnFormulari(View v) {
        Intent forms = new Intent(getApplicationContext(), ActivitatFormularis.class);
        forms.putExtra("llenguatge",getResources().getString(R.string.llenguatge));
        startActivity(forms);
    }

    /**
     * Mètode que utilitzem per a obrir l'activitat corresponent als vídeos.
     * @param v View
     */
    public void onClickBtnVideos(View v) {
        Intent videos = new Intent(this, LlistatVideos.class );
        videos.putExtra("llenguatge",getResources().getString(R.string.llenguatge));
        startActivity(videos);
    }

    /**
     * Mètode que utilitzem per a obrir l'activitat per a poder afegir un vídeo.
     * @param v View
     */
    public void onClickBtnAfegirVideo(View v) {
        Intent afegir = new Intent(this, AfegirVideos.class );
        afegir.putExtra("llenguatge",getResources().getString(R.string.llenguatge));
        startActivity(afegir);
    }

    /**
     * Mètode que utilitzem per a obrir l'activitat per a poder eliminar un vídeo.
     * @param v View
     */
    public void onClickBtnEliminarVideo(View v) {
        Intent eliminar = new Intent(this, EliminarVideo.class );
        eliminar.putExtra("llenguatge",getResources().getString(R.string.llenguatge));
        startActivity(eliminar);
    }

    /**
     * Mètode que utilitzem per a activar una toolbar personalitzada.
     */
    public void setUpToolBar() {
        toolbar = findViewById(R.id.idToolBar);
        setSupportActionBar(toolbar);
    }

    /**
     * Mètode que utilitzem per a fer l'inflater del menú.
     * @param menu menu
     * @return menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // Incialitzem la instància dels diferents items.
        logIn = menu.findItem(R.id.idLogin);
        //register = menu.findItem(R.id.idRegister);
        logOut = menu.findItem(R.id.idLogout);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // Comprovem si l'usuari té la sessió iniciada i en qüestió d'aixó mostrem
        // les opcions de iniciar sessió i registrar-te(acció desactivada), o l'opció de tancar sessió.
        logIn.setVisible(!loggedIn);
        //register.setVisible(!loggedIn);
        logOut.setVisible(loggedIn);

        return true;
    }

    /**
     * Mètode que controla quin botó del menú s'ha seleccionat per tal d'obrir la corresponent activity.
     * També controla el text que hi ha en el centre de la toolbar en aquesta activity.
     * -------------------------------------------------------------------------------------------------
     * (xm)s'ha desactiviat (veure també menu.xml) el menu de registre, ja que de moment no es
     * registraran usuaris, només perfils d'usuari creats des de admin. S'ha d'estudiar la conveniència
     * d'utiltzar usuaris registrats i el seu tractament (LODP).
     * -------------------------------------------------------------------------------------------------
     * @param item MenuItem
     * @return ItemSelected
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.idLogin:
                Intent login = new Intent(this, IniciarSessio.class );
                login.putExtra("llenguatge", getResources().getString(R.string.llenguatge));
                startActivity(login);
                finish();
                break;
            /*case R.id.idRegister:
                Intent register = new Intent(this, Registrarse.class );
                register.putExtra("llenguatge", getResources().getString(R.string.llenguatge));
                startActivity(register);
                finish();
                break;*/
            case R.id.idLogout:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(this, RecrearPantalla.class);
                startActivity(i);
                recreate();
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

        Intent i = new Intent(this, RecrearPantalla.class);
        startActivity(i);

        // Actualizar recursos lenguaje de app.
        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Recarreguem l'activitat.
        recreate();
    }

    private void customTitileToolBar() {
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            TextView textView = toolbar.findViewById(R.id.toolbar_title);

            textView.setText(getResources().getString(R.string.tlbiTitol));
        }
    }
}
