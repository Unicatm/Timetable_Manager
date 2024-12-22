package com.example.androidproject.clase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

//@Entity(tableName = "tasks",foreignKeys = @ForeignKey(entity = Materie.class,parentColumns = "id",childColumns = "materie_id",onDelete = ForeignKey.CASCADE))
public class Task implements Serializable {

    //@PrimaryKey(autoGenerate = true)
    private String id;
    private String numeTask;
    private String denMaterie;
    private String dataDeadline;
    private String tipDdl;
    private String descriere;
    //@ColumnInfo(name = "materie_id")
    private Long orarId;


    public Long getOrarId() {
        return orarId;
    }

    public void setOrarId(Long orarId) {
        this.orarId = orarId;
    }

    public Task(String numeTask, String denMaterie, String dataDeadline, String tipDdl, String descriere, Long orarId) {
        this.numeTask = numeTask;
        this.denMaterie = denMaterie;
        this.dataDeadline = dataDeadline;
        this.tipDdl = tipDdl;
        this.descriere = descriere;
        this.orarId=orarId;
    }

    public Task() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getDataDeadline() {
        return dataDeadline;
    }

    public void setDataDeadline(String dataDeadline) {
        this.dataDeadline = dataDeadline;
    }

    public String getTipDdl() {
        return tipDdl;
    }

    public void setTipDdl(String tipDdl) {
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
                "id='" + id + '\'' +
                "numeTask='" + numeTask + '\'' +
                ", denMaterie='" + denMaterie + '\'' +
                ", dataDeadline=" + dataDeadline +
                ", tipDdl=" + tipDdl +
                ", descriere='" + descriere + '\'' +
                ", idMaterie='" + orarId+ '\'' +
                '}';
    }
}
