package com.example.nurseapp.TractamentGenericToolBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.nurseapp.MainActivity;
import com.example.nurseapp.R;


/**
 * Classe genèrica per al tractament de la toolbar en la resta de classes (menys Main Actinty)
 */
public class TractamentToolBar extends AppCompatActivity {

    // Inicialització de les variables :
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tractament_tool_bar);
    }

    /**
     * Mètode que utilitzem per a activar una toolbar personalitzada.
     */
    public void setUpToolBar() {
        toolbar = findViewById(R.id.idToolBar);
        setSupportActionBar(toolbar);
        showHomeUpIcon(R.drawable.ic_home);
    }

    /**
     * Mètode que utilitzem per mostrar el botó de home
     */
    public void showHomeUpIcon(int drawable) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            final Drawable icon = getResources().getDrawable(drawable);
            getSupportActionBar().setHomeAsUpIndicator(icon);
        }
    }

    /**
     * Mètode que controla quin botó del menú s'ha seleccionat per tal d'obrir la corresponent activity.
     *
     * @param item MenuItem
     * @return item
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Mètode que controla el text que hi ha en el centre de la toolbar
     *
     * @param nomTitol textView
     */
    public void customTitileToolBar(String nomTitol) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView textView = toolbar.findViewById(R.id.toolbar_title);
            textView.setText(nomTitol);
        }
    }
}
