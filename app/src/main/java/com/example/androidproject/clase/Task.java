package com.example.androidproject.clase;

import java.util.Date;

public class Task {
    private String numeTask;
    private Date dataDeadline;
    private String descriere;
    private Categorie categorie;

    public Task(String numeTask, Date dataDeadline, String descriere, Categorie categorie) {
        this.numeTask = numeTask;
        this.dataDeadline = dataDeadline;
        this.descriere = descriere;
        this.categorie = categorie;
    }

    public String getNumeTask() {
        return numeTask;
    }

    public void setNumeTask(String numeTask) {
        this.numeTask = numeTask;
    }

    public Date getDataDeadline() {
        return dataDeadline;
    }

    public void setDataDeadline(Date dataDeadline) {
        this.dataDeadline = dataDeadline;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
