package com.example.androidproject.clase;

import java.util.List;

public class DbCategorii {
    List<Categorie> categorii;

    public DbCategorii(List<Categorie> categorii) {
        this.categorii = categorii;
    }

    public List<Categorie> getCategorii() {
        return categorii;
    }

    public void setCategorii(List<Categorie> categorii) {
        this.categorii = categorii;
    }
}
