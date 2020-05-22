package com.example.m07_uf2_projecte_3;

import java.io.Serializable;
import java.util.Objects;

public class Jugadors implements Serializable {
    public static final long serialVersionID = 1L;

    private String idJug;
    private String nomJug;
    private String partidesGuanyades;
    private String partidesPerdudes;
    private String partidesTotals;
    private String imatge;

    public Jugadors() {
    }

    public Jugadors( String nomJug, String partidesGuanyades, String partidesPerdudes, String partidesTotals, String imatge) {

        this.nomJug = nomJug;
        this.partidesGuanyades = partidesGuanyades;
        this.partidesPerdudes = partidesPerdudes;
        this.partidesTotals = partidesTotals;
        this.imatge = imatge;
        /*
        setIdJug(idJug);
        setNomJug(nomJug);
        setPartidesGuanyades(partidesGuanyades);
        setPartidesPerdudes(partidesPerdudes);
        setPartidesTotals(partidesTotals);
        */
    }

    public String getIdJug() {
        return idJug;
    }

    public void setIdJug(String idJug) {
        this.idJug = idJug;
    }

    public String getNomJug() {
        return nomJug;
    }

    public void setNomJug(String nomJug) {
        this.nomJug = nomJug;
    }

    public String getPartidesGuanyades() {
        return partidesGuanyades;
    }

    public void setPartidesGuanyades(String partidesGuanyades) {
        this.partidesGuanyades = partidesGuanyades;
    }

    public String getPartidesPerdudes() {
        return partidesPerdudes;
    }

    public void setPartidesPerdudes(String partidesPerdudes) {
        this.partidesPerdudes = partidesPerdudes;
    }

    public String getPartidesTotals() {
        return partidesTotals;
    }

    public void setPartidesTotals(String partidesTotals) {
        this.partidesTotals = partidesTotals;
    }

    public String getImatge() {
        return imatge;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugadors jugadors = (Jugadors) o;
        return idJug == jugadors.idJug &&
                partidesGuanyades == jugadors.partidesGuanyades &&
                partidesPerdudes == jugadors.partidesPerdudes &&
                partidesTotals == jugadors.partidesTotals &&
                Objects.equals(nomJug, jugadors.nomJug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJug, nomJug, partidesGuanyades, partidesPerdudes, partidesTotals);
    }
}
