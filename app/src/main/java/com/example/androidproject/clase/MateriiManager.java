package com.example.androidproject.clase;

import java.util.ArrayList;

public class MateriiManager {
    private static ArrayList<Materie> materiiList = new ArrayList<>();

    public static void adaugaMaterie(Materie materie) {
        materiiList.add(materie);
    }

    // Șterge materie
    public static void stergeMaterie(Materie materie) {
        materiiList.remove(materie);
    }

    // Returnează lista materiilor
    public static ArrayList<String> getNumeMateriiList() {
        ArrayList<String> listaNumeMaterii = new ArrayList<>();
        for(Materie m:materiiList){
            listaNumeMaterii.add(m.getNumeMaterie());
        }
        return listaNumeMaterii;
    }

    public static ArrayList<Materie> getMateriiList() {
        return materiiList;
    }


}
