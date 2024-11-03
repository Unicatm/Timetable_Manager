package com.example.androidproject.clase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MateriiManager {
    private static ArrayList<Materie> materiiList = new ArrayList<>();

    public static void adaugaMaterie(Materie materie) {
//        for (Materie m : materiiList) {
//            if (m.getNumeMaterie().equals(materie.getNumeMaterie())) {
//                return;
//            }
//        }
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
        for (Materie m : materiiList) {
            m.updateTaskCount();
        }
        return materiiList;
    }

    public static void updateSubjectCounts() {
        for (Materie m : materiiList) {
            int count = TaskManager.getTaskCountForSubject(m.getNumeMaterie());
            m.setNoAssignments(count);
        }
    }

    public static void sorteazaMateriiAlfabetic() {
        Collections.sort(materiiList, new Comparator<Materie>() {
            @Override
            public int compare(Materie m1, Materie m2) {
                return m1.getNumeMaterie().compareToIgnoreCase(m2.getNumeMaterie());
            }
        });
    }


}
