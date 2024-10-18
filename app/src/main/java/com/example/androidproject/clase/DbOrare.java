package com.example.androidproject.clase;

import java.util.List;

public class DbOrare {
    private List<Orar> orare;

    public DbOrare(List<Orar> orare) {
        this.orare = orare;
    }

    public List<Orar> getOrare() {
        return orare;
    }

    public void setOrare(List<Orar> orare) {
        this.orare = orare;
    }
}
