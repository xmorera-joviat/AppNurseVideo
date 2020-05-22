package com.example.provaapiyoutube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Variables per al control de la classe Llistat vídeos relacionada amb elRecyclerView.

    private List<LlistatVideos> llistatVideos;
    private RecyclerView llistat;
    private LlistatVideosAdabter adabter;


    //Dues variables de tipus botó per a reproduir el vídeo seleccionat i per passar l'altra classe per afegir vídeos.

    private ImageButton btnPlay;
    private ImageButton btnAdd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Relacionem els botons amb la vista gràfica.

        btnPlay = findViewById(R.id.btnPlay);
        btnAdd = findViewById(R.id.imgBtnAdd);

       //Relacionem la llista i el LinearLayout amb la part gràfica i definim l'orientació.

        llistat = (RecyclerView) findViewById(R.id.idRecyvler);
        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        llistat.setLayoutManager(lim);



        //Execitem els mètodes.
        data();
        inicialitzaAdabter();

        //SetOnClik per al botó d'afegir vídeos.

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddVideos.class));
            }
        });


    }

    /**
     * Mètode data que omple l'array dels vídeos amb el nom corresponenet, en tenir la base dades hem de
     * recorre la base de dades en lloc de posar les dades una a una.
     */
    public  void data(){


        llistatVideos = new ArrayList<>();
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));
        llistatVideos.add(new LlistatVideos("Títol"));


    }


    /**
     * Mètode per a inicialitzar el Llistat de vídeos
     */
    public void inicialitzaAdabter(){

        adabter = new LlistatVideosAdabter(llistatVideos);
        llistat.setAdapter(adabter);




    }


    /**
     * Botó onClick per a canviar a l'activitat dels vídeos.
     * @param v
     */
    public void onClick(View v)
    {

        startActivity(new Intent(MainActivity.this, VideoFullScreen.class));


    }




}



