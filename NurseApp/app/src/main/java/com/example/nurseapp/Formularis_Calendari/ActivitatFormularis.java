package com.example.nurseapp.Formularis_Calendari;



import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.nurseapp.R;
import com.example.nurseapp.TractamentGenericToolBar.TractamentToolBar;

/**
 * Classe que obri un WebView del forms de google.
 */
public class ActivitatFormularis extends TractamentToolBar {

    //Inicialització de les variables

    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitat_formularis);

        //Executem el mètode setUpToolBar

        setUpToolBar();

        //Executem el mètode customTitileToolBar amb el títol corresponent.

        customTitileToolBar("Formularis");

        //Vinculem les variables amb els corresponents objectes de l'apartat gràfic.

        web = findViewById(R.id.idWebViewFormularis);


        //Llancem en el WebView el forms de google.

        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl("https://docs.google.com/forms/d/1GeYzHTgyJiBLaAyo7JyVeaZ1uxK1CBFZpArCLTO1fHw/edit");
    }
}
