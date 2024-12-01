package com.example.androidproject;

import static com.example.androidproject.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.clase.Materie;
import com.example.androidproject.clase.MateriiManager;
import com.example.androidproject.customAdapters.AdapterMaterie;
import com.example.androidproject.dataBases.MaterieDAO;
import com.example.androidproject.dataBases.MaterieDB;
import com.example.androidproject.dataBases.TasksDAO;
import com.example.androidproject.dataBases.TasksDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PaginaMaterii extends AppCompatActivity {

    private MaterieDB materiiDb;
    private MaterieDAO materiiDAO;
    private FloatingActionButton fabAdaugaMaterie;
    private List<Materie> listaMaterii= MateriiManager.getMateriiList();
    private List<Materie> listaDBMaterii;
    private ListView lvListaMaterii;
    private AdapterMaterie adapter;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagina_materii);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        TasksDB tasksDB = TasksDB.getInstance(getApplicationContext());
        TasksDAO tasksDAO = tasksDB.getTaskDAO();

        materiiDb = MaterieDB.getInstance(getApplicationContext());
        materiiDAO = materiiDb.getMaterieDAO();
        listaDBMaterii=materiiDAO.getMaterii();

        lvListaMaterii = findViewById(R.id.lvListaMaterii);

        ChipGroup chipGroup = findViewById(R.id.cgSortare);
        Chip chipAZ = findViewById(R.id.chipAZ);
        Chip chipZA = findViewById(R.id.chipZA);
        Chip chipNoAssignments = findViewById(R.id.chipAssignments);


        // ========= NAVIGATION ==========
        BottomNavigationView btmNav = findViewById(id.btmNav);
        btmNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int id = item.getItemId();

                if(id ==R.id.pgMaterii){
                    intent = new Intent(PaginaMaterii.this, PaginaMaterii.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    launcher.launch(intent);
                    return true;
                } else if (id == R.id.pgOrar) {
                    intent = new Intent(PaginaMaterii.this, PaginaOrar.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    launcher.launch(intent);
                    return true;
                } else if (id == R.id.pgAnunturi) {
                    intent = new Intent(PaginaMaterii.this, PaginaTasks.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    launcher.launch(intent);
                    return true;
                }else if (id == R.id.pgNotite) {
                    intent = new Intent(PaginaMaterii.this, PaginaNotite.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    launcher.launch(intent);
                    return true;
                }

                return false;
            }
        });


        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->{
            if(result.getResultCode()==RESULT_OK) {
                if(result.getData().hasExtra("addMaterie")) {
                    Intent intent = result.getData();
                    Materie materie = (Materie) intent.getSerializableExtra("addMaterie");

                    if (materie != null) {
//                    listaMaterii.add(materie);
//                    adapter.notifyDataSetChanged();

                        materiiDAO.insertMaterie(materie);
                        listaDBMaterii.clear();
                        listaDBMaterii.addAll(materiiDAO.getMaterii());


                        for (Materie m : listaDBMaterii) {
                            int numarTaskuri = tasksDAO.getTaskCountForMaterie(m.getNumeMaterie());
                            m.setNoAssignments(numarTaskuri);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }else if(result.getData().hasExtra("materieEditata")){
                    Intent intent = result.getData();
                    Materie materie = (Materie) intent.getSerializableExtra("materieEditata");


                    if(materie!=null){
                        Materie materieDeActualizat = (Materie) intent.getSerializableExtra("editMaterie");

                        materieDeActualizat.setNumeMaterie(materie.getNumeMaterie());
                        materieDeActualizat.setTipSaptamana(materie.getTipSaptamana());
                        materieDeActualizat.setSala(materie.getSala());
                        materieDeActualizat.setSaptamanal(materie.getSaptamanal());


                        materiiDAO.updateMaterie(materieDeActualizat);

                        materiiDAO.insertMaterie(materie);
                        listaDBMaterii.clear();
                        listaDBMaterii.addAll(materiiDAO.getMaterii());
                        adapter.notifyDataSetChanged();

//                        AdapterMaterie adapterNou = (AdapterMaterie) lvListaMaterii.getAdapter();
                    }
                }
            }
        });

        adapter =new AdapterMaterie(getApplicationContext(), layout.card_materie,listaDBMaterii, getLayoutInflater(),launcher);
        lvListaMaterii.setAdapter(adapter);

        // ========= Butoane ==========

        fabAdaugaMaterie = findViewById(R.id.fabAdaugaMaterie);
        fabAdaugaMaterie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdaugareMaterie.class);
                //startActivity(intent);
                launcher.launch(intent);
            }
        });

        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) {
                listaDBMaterii.clear();
                listaDBMaterii.addAll(materiiDAO.getMaterii());
                adapter.notifyDataSetChanged();
            }else{
                int checkedId = checkedIds.get(0);

                if (checkedId == R.id.chipAZ) {
                    Collections.sort(listaDBMaterii, Comparator.comparing(Materie::getNumeMaterie));
                    adapter.notifyDataSetChanged();

                } else if (checkedId == R.id.chipZA) {
                    Collections.sort(listaDBMaterii, (m1, m2) -> m2.getNumeMaterie().compareTo(m1.getNumeMaterie()));
                    adapter.notifyDataSetChanged();

                } else if (checkedId == R.id.chipAssignments) {
/*                        materiiList.clear();
                        materiiList.addAll(originalMateriiList);
                        subjectAdapter.notifyDataSetChanged();*/
                }
            }

        });
    }


}