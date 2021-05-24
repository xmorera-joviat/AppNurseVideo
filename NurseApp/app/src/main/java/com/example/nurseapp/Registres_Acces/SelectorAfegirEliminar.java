package com.example.nurseapp.Registres_Acces;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.nurseapp.EliminarVideos.EliminarVideo;
import com.example.nurseapp.R;
import com.example.nurseapp.TractamentGenericToolBar.TractamentToolBar;

/**
 * Classe en la qual disposem dos botons per decidir si volem afegir o eliminar vídeos.
 */
public class SelectorAfegirEliminar extends TractamentToolBar {

    // Inicialització de les variables :

    private Button buttonAfegirVideos;
    private Button buttonEliminarVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_afegir_eliminar);

        // Executem el mètode setUpToolBar
        setUpToolBar();

        // Executem el mètode customTitileToolBar amb el corresponent títol.
        customTitileToolBar("Afegir/Eliminar");

        // Vinculem les variables amb els corresponents objectes de l'apartat gràfic.
        buttonAfegirVideos = findViewById(R.id.idButtonAfegirVideos);
        buttonEliminarVideos = findViewById(R.id.idButtonEliminarVideos);

        // Botó onClick que ens redirigeix a l'activity per poder afegir vídeos.
        buttonAfegirVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AfegirVideos.class );
                startActivity(i);
            }
        });

        // Botó onClick que ens redirigeix a l'activity per poder eliminar vídeos.
        buttonEliminarVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent e = new Intent(getApplicationContext(), EliminarVideo.class );
                startActivity(e);
            }
        });
    }
}
