package com.example.provaapiyoutube;

/**
 * Classe dels atributs que ha de tenir la classe dels vídeos, en tenir la base de dades haurem d'ampliar.
 */
public class LlistatVideos {

    private String texTitolVideos;


    public LlistatVideos(String texTitolVideos) {
        this.texTitolVideos = texTitolVideos;
    }


    public String getTitol() {
        return texTitolVideos;
    }

    public void setTitol(String texTitolVideos) {
        this.texTitolVideos = texTitolVideos;
    }


}
