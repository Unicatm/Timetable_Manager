package com.example.androidproject.clase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "tasks")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Task{" +
                "numeTask='" + numeTask + '\'' +
                ", denMaterie='" + denMaterie + '\'' +
                ", dataDeadline=" + dataDeadline +
                ", tipDdl=" + tipDdl +
                ", descriere='" + descriere + '\'' +
                '}';
    }
}
