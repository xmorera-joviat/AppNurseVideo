package com.example.nurseapp.TractamentVideos;

/**
 * Classe que utilitzem per a poder gestionar l'Id, nom, descripció i url de cada vídeo.
 */
public class LlistatVideos {

    //Inicialització de les variables
    private  int numId;
    private String texTitolVideos;
    private String descVideo;
    private String urlVideo;

    //Constructors corresponents.
    public  LlistatVideos(){}

    public LlistatVideos(int numId, String texTitolVideos, String descVideo, String urlVideo) {
        this.numId = numId;
        this.texTitolVideos = texTitolVideos;
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

    public String getTexTitolVideos() {
        return texTitolVideos;
    }

    public void setTexTitolVideos(String texTitolVideos) {
        this.texTitolVideos = texTitolVideos;
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
        return texTitolVideos;
    }
}
