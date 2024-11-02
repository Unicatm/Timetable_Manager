package com.example.androidproject.clase;

import java.io.Serializable;

public class Materie implements Serializable {
    private String numeMaterie;
    private String sala;
    private Boolean saptamanal;
    private String tipSaptamana;
    private int noAssignments;

    public Materie(String numeMaterie, String sala, Boolean saptamanal) {
        this.numeMaterie = numeMaterie;
        this.sala = sala;
        this.saptamanal = saptamanal;
    }

    public Materie(String numeMaterie, String sala, Boolean saptamanal, String tipSaptamana) {
        this.numeMaterie = numeMaterie;
        this.sala = sala;
        this.saptamanal = saptamanal;
        this.tipSaptamana = tipSaptamana;
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

    public String getTipSaptamana() {
        return tipSaptamana;
    }

    public void setTipSaptamana(String tipSaptamana) {
        this.tipSaptamana = tipSaptamana;
    }

    public int getNoAssignments() {
        return noAssignments;
    }

    public void setNoAssignments(int noAssignments) {
        this.noAssignments = noAssignments;
    }

    @Override
    public String toString() {
        return "Materie{" +
                "numeMaterie='" + numeMaterie + '\'' +
                ", sala='" + sala + '\'' +
                ", saptamanal=" + saptamanal +
                ", tipSaptamana='" + tipSaptamana + '\'' +
                '}';
    }

    public void updateTaskCount() {
        this.noAssignments = TaskManager.getTaskCountForSubject(numeMaterie);
    }
}
