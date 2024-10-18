package com.example.androidproject.clase;

import java.util.List;

public class DbMaterii {
    private List<Materie> materii;

    public DbMaterii(List<Materie> materii) {
        this.materii = materii;
    }

    public List<Materie> getMaterii() {
        return materii;
    }

    public void setMaterii(List<Materie> materii) {
        this.materii = materii;
    }
}
