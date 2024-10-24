package com.example.androidproject.clase;

import java.util.ArrayList;

public class MateriiManager {
    private static ArrayList<String> numeMateriiList = new ArrayList<>();

    public static void adaugaMaterie(String materie) {
        numeMateriiList.add(materie);
    }

    // Șterge materie
    public static void stergeMaterie(String materie) {
        numeMateriiList.remove(materie);
    }

    // Returnează lista materiilor
    public static ArrayList<String> getMateriiList() {
        return numeMateriiList;
    }
}
