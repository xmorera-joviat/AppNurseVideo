package com.example.nurseapp.TractamentVideos;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.nurseapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

/**
 * Classe utilitzada per afegir títol del vídeo, descripció, url i ID, l'ID és autonumèric. A la base de dades Firebase.
 */
public class AfegirVideos extends LlistatVideos {

    // Inicialització de les variables :
    private EditText idTitolCa, idDescCa, idUrlCa, idCategoriaCa, idTituloEs, idDescEs, idUrlEs, idCategoriaEs, idTitleEn, idDescEn, idUrlEn, idCategoryEn;
    private Button btnAfegir;
    DatabaseReference ref;
    int numLlista = 0;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_videos);

        Locale locale = new Locale(getIntent().getExtras().getString("llenguatge"));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;

        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Vinculem el fragment.
        fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentAfegirVideos);

        // Vinculem les variables amb els corresponents objectes de l'apartat  del fragment.
        idTitolCa = fragment.getView().findViewById(R.id.idTitolCa);
        idDescCa = fragment.getView().findViewById(R.id.idDescripcioCa);
        idUrlCa = fragment.getView().findViewById(R.id.idUrlCa);
        idCategoriaCa = fragment.getView().findViewById(R.id.idCategoriaCa);
        idTituloEs = fragment.getView().findViewById(R.id.idTituloEs);
        idDescEs = fragment.getView().findViewById(R.id.idDescripcionEs);
        idUrlEs = fragment.getView().findViewById(R.id.idUrlEs);
        idCategoriaEs = fragment.getView().findViewById(R.id.idCategoriaEs);
        idTitleEn = fragment.getView().findViewById(R.id.idTitleEn);
        idDescEn = fragment.getView().findViewById(R.id.idDescriptionEn);
        idUrlEn = fragment.getView().findViewById(R.id.idUrlEn);
        idCategoryEn = fragment.getView().findViewById(R.id.idCategoryEn);
        btnAfegir = fragment.getView().findViewById(R.id.idButtonAfegir);

        // Executem el mètode setUpToolBar
        setUpToolBar();

        // Executem el mètode customTitileToolBar amb el seu títol corresponent.
        customTitileToolBar(getResources().getString(R.string.tlbafTitol));

        // Obtenim la referència del fill que tenim a la base de dades.
        ref = FirebaseDatabase.getInstance().getReference().child("Video");

        // Botó onClick per ta d'afegir les dades a la base de dades.
        btnAfegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titolCa = idTitolCa.getText().toString();
                String descCa = idDescCa.getText().toString();
                String urlCa = idUrlCa.getText().toString();
                String categoriaCa = idCategoriaCa.getText().toString();

                String tituloEs = idTituloEs.getText().toString();
                String descEs = idDescEs.getText().toString();
                String urlEs = idUrlEs.getText().toString();
                String categoriaEs = idCategoriaEs.getText().toString();

                String titleEn = idTitleEn.getText().toString();
                String descEn = idDescEn.getText().toString();
                String urlEn = idUrlEn.getText().toString();
                String categoryEn = idCategoryEn.getText().toString();

                data();

                // Número del següent ID.
                numLlista = getMidaLlista();
                numLlista++;

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

                    Video Cat = new Video();
                    Video Esp = new Video();
                    Video Eng = new Video();

                    if ((urlCa.contains("be/") || urlCa.contains("?v")) &&
                            (urlEs.contains("be/") || urlEs.contains("?v")) &&
                                (urlEn.contains("be/") || urlEn.contains("?v")))
                    {
                        Cat.setNumId(numLlista);
                        Cat.setTitol(titolCa);
                        Cat.setDescVideo(descCa);
                        Cat.setUrlVideo(urlCa.substring(inici + 3, inici + 14));
                        Cat.setCategoria(categoriaCa);

                        ref.child(String.valueOf(numLlista)).setValue(Cat);

                        Esp.setNumId(numLlista);
                        Esp.setTitol(tituloEs);
                        Esp.setDescVideo(descEs);
                        Esp.setUrlVideo(urlEs.substring(inici + 3, inici + 14));
                        Esp.setCategoria(categoriaEs);

                        ref.child(String.valueOf(numLlista)).setValue(Esp);

                        Eng.setNumId(numLlista);
                        Eng.setTitol(titleEn);
                        Eng.setDescVideo(descEn);
                        Eng.setUrlVideo(urlEn.substring(inici + 3, inici + 14));
                        Eng.setCategoria(categoryEn);

                        ref.child(String.valueOf(numLlista)).setValue(Eng);

                        // Si tot es correcte afegim el vídeo a la base de dades Firebase.
                        Toast.makeText(getApplicationContext(), "Vídeo afegit", Toast.LENGTH_LONG).show();
                        netejar();
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
        });
    }

    /**
     * Mètode per deixar els camps de text buits cada cop que es realitza un registre.
     */
    private void netejar() {
        idTitolCa.setText("");
        idDescCa.setText("");
        idUrlCa.setText("");
        idCategoriaCa.setText("");

        idTituloEs.setText("");
        idDescEs.setText("");
        idUrlEs.setText("");
        idCategoriaEs.setText("");

        idTitleEn.setText("");
        idDescEn.setText("");
        idUrlEn.setText("");
        idCategoryEn.setText("");
    }
}
