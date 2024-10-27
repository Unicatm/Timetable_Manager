package com.example.androidproject.clase;

import java.io.Serializable;

public class Materie implements Serializable {
    private String numeMaterie;
    private String sala;
    private Boolean saptamanal;

    public Materie(String numeMaterie, String sala, Boolean saptamanal) {
        this.numeMaterie = numeMaterie;
        this.sala = sala;
        this.saptamanal = saptamanal;
    }

    public String getNumeMaterie() {
        return numeMaterie;
    }

    public void setNumeMaterie(String numeMaterie) {
        this.numeMaterie = numeMaterie;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public Boolean getSaptamanal() {
        return saptamanal;
    }

    public void setSaptamanal(Boolean saptamanal) {
        this.saptamanal = saptamanal;
    }

    @Override
    public String toString() {
        return "Materie{" +
                "numeMaterie='" + numeMaterie + '\'' +
                ", sala='" + sala + '\'' +
                ", saptamanal=" + saptamanal +
                '}';
    }
}
