package com.example.androidproject.clase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;


@Entity(tableName = "orare")
public class Orar implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String facultate;
    private String an;
    private String semestru;
    private Date oraStart;
    private Date oraFinal;


    public Orar(String facultate, String an, String semestru, Date oraStart, Date oraFinal) {
        this.facultate = facultate;
        this.an = an;
        this.semestru = semestru;
        this.oraStart = oraStart;
        this.oraFinal = oraFinal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFacultate() {
        return facultate;
    }

    public void setFacultate(String facultate) {
        this.facultate = facultate;
    }

    public String getAn() {
        return an;
    }

    public void setAn(String an) {
        this.an = an;
    }

    public String getSemestru() {
        return semestru;
    }

    public void setSemestru(String semestru) {
        this.semestru = semestru;
    }

    public Date getOraStart() {
        return oraStart;
    }

    public void setOraStart(Date oraStart) {
        this.oraStart = oraStart;
    }

    public Date getOraFinal() {
        return oraFinal;
    }

    public void setOraFinal(Date oraFinal) {
        this.oraFinal = oraFinal;
    }

    @Override
    public String toString() {
        return "Orar{" +
                "facultate='" + facultate + '\'' +
                ", an='" + an + '\'' +
                ", semestru='" + semestru + '\'' +
                ", oraStart=" + oraStart +
                ", oraFinal=" + oraFinal +
                '}';
    }
}
