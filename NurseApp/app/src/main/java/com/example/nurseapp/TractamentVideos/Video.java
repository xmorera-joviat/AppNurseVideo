package com.example.nurseapp.TractamentVideos;

/**
 * Classe que utilitzem per a poder gestionar l'Id, nom, descripció i url de cada vídeo.
 */
public class Video {

    //Inicialització de les variables
    private int numId;
    private String titol;
    private String descVideo;
    private String urlVideo;

    //Constructors corresponents.
    public Video(){}

    public Video(int numId, String titol, String descVideo, String urlVideo) {
        this.numId = numId;
        this.titol = titol;
        this.descVideo = descVideo;
        this.urlVideo = urlVideo;
    }

    //Getters i Setters de cada una de les variables.
    public int getNumId() {
        return numId;
    }

    public void setNumId(int numId) {
        this.numId = numId;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String texTitolVideos) {
        this.titol = texTitolVideos;
    }

    public String getDescVideo() {
        return descVideo;
    }

    public void setDescVideo(String descVideo) {
        this.descVideo = descVideo;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    /**
     *
     * Mètode toStrings per tal de retornar el títol dels vídeos.
     *
     * @return string.
     */
    @Override
    public String toString() {
        return titol;
    }
}
