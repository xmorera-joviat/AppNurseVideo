package com.example.nurseapp.TractamentVideos;


import android.content.Intent;
import android.os.Bundle;
import com.example.nurseapp.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Classe que utilitzem només per a implementar l'API de youtube.
 */
public class ApiYoutube extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    //Inicialització de les variables corresponents.
    String clauYoutube="AIzaSyCHXQMgIDXbgB5LZeK2ugWLCUMgyyV61xM";

    YouTubePlayerView youTubePlayerView;

    String nomUrl = "azxDhcKYku4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_youtube);

      //Vinculem les variables amb els corresponents objectes de l'apartat gràfic.
        youTubePlayerView = findViewById(R.id.idYoutube);
        youTubePlayerView.initialize(clauYoutube, this);

        //Obtenim l'url corresponent al vídeo que hem de reproduir.
        Bundle b = getIntent().getExtras();
        nomUrl = b.get("url").toString();
    }

    /**
     * Mètode utilitzat per a la càrrega i reproducció directament el vídeo en pantalla completa.
     * @param provider YouTubePlayer.Provider
     * @param youTubePlayer YouTubePlayer
     * @param b boolean
     */
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer=youTubePlayer;
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

        youTubePlayer.loadVideo(nomUrl);
        youTubePlayer.setFullscreen(true);
    }

    /**
     *
     * Mètode per comprovar el getErrorDialog.
     *
     * @param provider YouTubePlayer.Provider
     * @param youTubeInitializationResult YouTubeInitializationResult
     */
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError())
        {
            youTubeInitializationResult.getErrorDialog(this,1).show();
        }

    }

    /**
     *
     * Inicialitzem el getYoutubePlayerProvider amb la corresponent clau.
     *
     * @param requestCode int
     * @param resultCode int
     * @param data Intent
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            getYoutubePlayerProvider().initialize(clauYoutube, this);
        }

    }

    /**
     *
     * Mètode per retornar la View del vídeo corresponent.
     *
     * @return YouTubePlayer.Provider
     */
    protected YouTubePlayer.Provider getYoutubePlayerProvider(){
        return youTubePlayerView;
    }

}
