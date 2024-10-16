package com.example.androidproject.clase;

import java.util.Date;

public class Orar {
    private String facultate;
    private int an;
    private int semestru;
    private Date oraStart;
    private Date oraFinal;
    private int durata;

    public Orar(String facultate, int an, int semestru, Date oraStart, Date oraFinal, int durata) {
        this.facultate = facultate;
        this.an = an;
        this.semestru = semestru;
        this.oraStart = oraStart;
        this.oraFinal = oraFinal;
        this.durata = durata;
    }

    public String getFacultate() {
        return facultate;
    }

    public void setFacultate(String facultate) {
        this.facultate = facultate;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public int getSemestru() {
        return semestru;
    }

    public void setSemestru(int semestru) {
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

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }
}
