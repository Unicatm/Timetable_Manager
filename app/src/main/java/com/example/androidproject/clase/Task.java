package com.example.androidproject.clase;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private String numeTask;
    private String denMaterie;
    private Date dataDeadline;
    private Categorie tipDdl;
    private String descriere;


    public Task(String numeTask, String denMaterie, Date dataDeadline, Categorie tipDdl, String descriere) {
        this.numeTask = numeTask;
        this.denMaterie = denMaterie;
        this.dataDeadline = dataDeadline;
        this.tipDdl = tipDdl;
        this.descriere = descriere;
    }

    public String getNumeTask() {
        return numeTask;
    }

    public void setNumeTask(String numeTask) {
        this.numeTask = numeTask;
    }

    public String getDenMaterie() {
        return denMaterie;
    }

    public void setDenMaterie(String denMaterie) {
        this.denMaterie = denMaterie;
    }

    public Date getDataDeadline() {
        return dataDeadline;
    }

    public void setDataDeadline(Date dataDeadline) {
        this.dataDeadline = dataDeadline;
    }

    public Categorie getTipDdl() {
        return tipDdl;
    }

    public void setTipDdl(Categorie tipDdl) {
        this.tipDdl = tipDdl;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
}
