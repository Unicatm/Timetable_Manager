package com.example.androidproject;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.androidproject.clase.Materie;
import com.example.androidproject.clase.Nota;
import com.example.androidproject.clase.Orar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseService {
    private final DatabaseReference reference;
    private static FirebaseService firebaseService;

    private FirebaseService() {
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseService getInstance() {
        if (firebaseService == null) {
            synchronized (FirebaseService.class) {
                if (firebaseService == null) {
                    firebaseService = new FirebaseService();
                }
            }
        }
        return firebaseService;
    }


    // ===== NOTA ====

    public DatabaseReference getReference(String path) {
        return reference.child(path);
    }

    public void insert(Nota nota) {
        if (nota == null || nota.getId() != null) {
            return;
        }
        String id =  reference.child("note").push().getKey();
        nota.setId(id);
        reference.child("note").child(nota.getId()).setValue(nota);
    }

    public void update(Nota nota) {
        if (nota == null || nota.getId() == null) {
            return;
        }
        reference.child("note").child(nota.getId()).setValue(nota);
    }

    public void delete(Nota nota) {
        if (nota == null || nota.getId() == null) {
            return;
        }
        reference.child("note").child(nota.getId()).removeValue();
    }

}

