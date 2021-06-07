package com.example.nurseapp;

import java.util.List;

public class UserInfo {

    private String email;
    private String nom;
    private String rol;

    // Constructor de la classe UserInfo sense paràmetres.
    public UserInfo() { }

    // Constructor amb les variables per a crear un objecte UserInfo.
    public UserInfo(String rol) {
        setRol(rol);
    }

    // Setters per poder assignar el valor a les variables corresponents,
    // i Getters per a poder obtenir els valors assignats a les variables.

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
