package com.example.nurseapp.Formularis_Calendari;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.nurseapp.R;
import com.example.nurseapp.TractamentGenericToolBar.TractamentToolBar;

/**
 * Classe que obri un WebView del calendari de google.
 */
public class ActivitatCalendari extends TractamentToolBar {

    // Inicialització de les variables :
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitat_calendari);

        // Vinculem les variables amb els corresponents objectes de l'apartat gràfic.
        web = findViewById(R.id.idWebViewCalendari);

        // Executem el mètode setUpToolBar
        setUpToolBar();

        // Executem el mètode customTitileToolBar amb el títol corresponent.
        customTitileToolBar("Calendari");

        // Llancem en el WebView el calendari de google.
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl("https://calendar.google.com/calendar/r");
    }
}
