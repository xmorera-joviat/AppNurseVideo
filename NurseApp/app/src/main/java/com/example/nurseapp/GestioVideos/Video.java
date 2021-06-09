package com.example.nurseapp.GestioVideos;

/**
 * Classe que utilitzem per a poder gestionar l'Id, nom, descripció i url de cada vídeo.
 */
public class Video {

    //Inicialització de les variables
    private int numId;
    private String titol;
    private String descVideo;
    private String urlVideo;
    private String categoria;
    private String mostra;

    public Video() {
    }

    public Video(int numId, String titol, String descVideo, String urlVideo, String categoria, String mostra) {
        this.numId = numId;
        this.titol = titol;
        this.descVideo = descVideo;
        this.urlVideo = urlVideo;
        this.categoria = categoria;
        this.mostra = mostra;
    }

    public int getNumId() {
        return numId;
    }

    public void setNumId(int numId) {
        this.numId = numId;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMostra() {
        return mostra;
    }

    public void setMostra(String mostra) {
        this.mostra = mostra;
    }




    /**
     * Mètode toStrings per tal de retornar el títol dels vídeos.
     * @return string.
     */
    @Override
    public String toString() {
        return titol;
    }
}
