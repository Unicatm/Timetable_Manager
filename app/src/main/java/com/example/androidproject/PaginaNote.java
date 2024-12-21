package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.clase.Nota;
import com.example.androidproject.customAdapters.AdapterNota;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PaginaNote extends AppCompatActivity {
    private FloatingActionButton fabAddNota;
    private List<Nota> listaNote = new ArrayList<>();
    private ListView lvNote;
    private ArrayAdapter<Nota> adapter;
    private ActivityResultLauncher<Intent> launcher;
    private Long orarId;
    private int ItemClicked;
    private FirebaseService firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagina_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseService = FirebaseService.getInstance();
        incarcaNoteDinFire();

        orarId = getIntent().getLongExtra("orarIdPtNote", -1L);
        Log.i("IDORAR_M",orarId.toString());

        lvNote = findViewById(R.id.lvNote);
        adapter = new AdapterNota(getApplicationContext(),R.layout.card_nota,listaNote,getLayoutInflater(),orarId);
        lvNote.setAdapter(adapter);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
            if(result.getData().hasExtra("NotaFromIntent")) {
                Intent intent = result.getData();

                Nota nota = (Nota) intent.getSerializableExtra("NotaFromIntent");

                firebaseService.insert(nota);
                incarcaNoteDinFire();
            }else if (result.getData().hasExtra("NotaEditat")){
                Intent intent = result.getData();

                Nota notaE = (Nota) intent.getSerializableExtra("NotaEditat");

                firebaseService.update(notaE);
                incarcaNoteDinFire();
            }else if(result.getData().hasExtra("notaStearsa")){
                Intent intent = result.getData();
                Nota notaStearsa = (Nota) intent.getSerializableExtra("notaStearsa");
                if (notaStearsa != null) {
                    firebaseService.delete(notaStearsa);
                    incarcaNoteDinFire();
                }
            }
        });



        BottomNavigationView btmNav = findViewById(R.id.btmNavNote);
        btmNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int id = item.getItemId();

                if(id == R.id.pgMaterii){
                    intent =new Intent (getApplicationContext(), PaginaMaterii.class);
                    intent.putExtra("orarIdPtMaterii", orarId);
                    launcher.launch(intent);
                    return true;
                } else if (id == R.id.pgOrar) {
                    intent = new Intent(getApplicationContext(), PaginaOrar.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.pgAnunturi) {
                    intent = new Intent(getApplicationContext(), PaginaTasks.class);
                    intent.putExtra("orarIdPtTasks", orarId);
                    launcher.launch(intent);
                    return true;
                }else if (id == R.id.pgNotite) {
                    return true;
                }

                return false;
            }
        });

        lvNote.setOnItemClickListener((adapterView,view,position,l)->{
            ItemClicked = position;

            Nota nota = listaNote.get(position);
            Intent intent = new Intent(getApplicationContext(), AddNota.class);
            intent.putExtra("orarIdAdaugare", orarId);
            intent.putExtra("editNota",nota);
            launcher.launch(intent);

        });

        fabAddNota = findViewById(R.id.fabAddNota);
        fabAddNota.setOnClickListener(v-> {
            Intent intent = new Intent(getApplicationContext(), AddNota.class);
            intent.putExtra("orarIdAdaugare", orarId);
            launcher.launch(intent);
        });


    }

    private void incarcaNoteDinFire() {
        DatabaseReference noteRef = firebaseService.getReference("note");

        noteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaNote.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Nota nota = dataSnapshot.getValue(Nota.class);
                    if (nota != null && nota.getOrarId().equals(orarId)) {
                        listaNote.add(nota);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Eroare la citirea din Firebase: " + error.getMessage());
            }
        });
    }

}