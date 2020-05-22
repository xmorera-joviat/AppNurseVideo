package com.example.provaapiyoutube;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Classe per a posar en marxa l'Api de youtube
 */
public class VideoFullScreen extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener  {

    //Variables per tal de treballar amb l'Api de youtube

    YouTubePlayerView youTubePlayerView;

    String clauYoutube = "AIzaSyCDm4uB8JQWJxtHqL5Rwo3J-WmmEwmPGLM";

    //Aquesta variable es la que conte el que sería el vídeo que volem.

    String test = "4FFzzK7m2Co";

    private YouTubePlayer youTubePlayer1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_full_screen);

        //Connectem la part de les variables amb la part gràfica.

        youTubePlayerView = findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(clauYoutube, this);




    }



    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {



        youTubePlayer1=youTubePlayer;
        youTubePlayer1.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);


        //Càrrega i reprodueix directament el vídeo

        youTubePlayer1.loadVideo(test);
        youTubePlayer1.setFullscreen(true);


    }

    //Mostrem si el vídeo es correcte o informem de l'error.

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {


        if(youTubeInitializationResult.isUserRecoverableError())
        {

            youTubeInitializationResult.getErrorDialog(this,1).show();


        } else
        {

            String error = "Error al iniciar Youtube"+youTubeInitializationResult.toString();

            Toast.makeText(getApplication(),error, Toast.LENGTH_LONG).show();




        }


    }

    //Inicialitzem la player de youtube si tot és correcte.

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if(resultCode==1)
        {

            getYoutubePlayerProvider().initialize(clauYoutube,this);

        }

    }

//Retorna la vista de youtube.

    protected YouTubePlayer.Provider getYoutubePlayerProvider() {

        return  youTubePlayerView;
    }





}
