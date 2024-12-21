package com.example.androidproject.clase;

import java.io.Serializable;

public class Nota implements Serializable {
    private String id;
    private String materie;
    private float nota;
    private Long orarId;

    public Nota() {
    }

    public Nota(String materie, float nota, Long orarId) {
        this.materie = materie;
        this.nota = nota;
        this.orarId = orarId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOrarId() {
        return orarId;
    }

    public void setOrarId(Long orarId) {
        this.orarId = orarId;
    }

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Nota " + nota + " la materia " + materie;
    }
}
