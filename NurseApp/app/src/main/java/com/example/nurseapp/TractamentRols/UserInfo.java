package com.example.nurseapp.TractamentRols;

import java.util.List;

public class UserInfo {
    private String rol;
    private String nom;
    private String cognoms;

    public UserInfo() { }

    public UserInfo(String rol, String nom, String cognoms) {
        this.rol = rol;
        this.nom = nom;
        this.cognoms = cognoms;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setNom(String nom) { this.nom = nom; }

    public String getNom() { return nom; }

    public String getCognoms() { return cognoms; }

    public void setCognoms(String cognoms) { this.cognoms = cognoms; }

}
