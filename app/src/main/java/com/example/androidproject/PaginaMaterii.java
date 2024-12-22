package com.example.androidproject;

import static com.example.androidproject.R.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
import com.example.androidproject.clase.Orar;
import com.example.androidproject.clase.Task;
import com.example.androidproject.customAdapters.AdapterMaterie;
import com.example.androidproject.dataBases.AplicatieDAO;
import com.example.androidproject.dataBases.AplicatieDB;
import com.example.androidproject.dataBases.MaterieDAO;
import com.example.androidproject.dataBases.MaterieDB;
import com.example.androidproject.dataBases.TasksDAO;
import com.example.androidproject.dataBases.TasksDB;
import com.example.androidproject.jsonHttps.HttpsManager;
import com.example.androidproject.jsonHttps.MaterieParser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PaginaMaterii extends AppCompatActivity {

    private FloatingActionButton fabAdaugaMaterie;
    private static List<Materie> listaDBMaterii;
    private ListView lvListaMaterii;
    private static AdapterMaterie adapter;
    private ActivityResultLauncher<Intent> launcher;
    private Long orarId;
    private MaterieDAO materiiDAO;
    private static final String URL_MATERII = "https://www.jsonkeeper.com/b/0JB8";
    private FirebaseService firebaseService;
    private List<Task> listaTaskuriFirebase = new ArrayList<>();

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


        firebaseService = FirebaseService.getInstance();

        AplicatieDB aplicatieDB = AplicatieDB.getInstance(getApplicationContext());
        materiiDAO = aplicatieDB.getMaterieDAO();
        AplicatieDAO aplicatieDAO = aplicatieDB.getAplicatieDAO();

        orarId = getIntent().getLongExtra("orarId", -1);
        if(orarId==-1){
            orarId = getIntent().getLongExtra("orarIdPtMaterii",-1);
        }
        if (orarId != -1) {
            listaDBMaterii = materiiDAO.getMateriiOrar(orarId);
        } else {
            Toast.makeText(this, "Nu s-a transmis orarul selectat.", Toast.LENGTH_SHORT).show();
            listaDBMaterii = new ArrayList<>();
        }

        Orar orar = aplicatieDAO.getOrarById(orarId);

        TextView tvFacultateAn = findViewById(R.id.tvFacultateAn);
        tvFacultateAn.setText(orar.getFacultate().toString()+ " anul " + orar.getAn().toString() + " sem. "+ orar.getSemestru().toString());

        incarcaTasksFromFire(taskList->{
            listaTaskuriFirebase = taskList;

            for (Materie m : listaDBMaterii) {
                int no = 0;
                for (Task task : listaTaskuriFirebase) {
                    if (m.getNumeMaterie().equals(task.getDenMaterie())) {
                        no++;
                    }
                }
                Log.e("LISTA",m.getNumeMaterie() + no);
                m.setNoAssignments(no);
                materiiDAO.updateMaterie(m);
            }
            adapter.notifyDataSetChanged();
        });


        lvListaMaterii = findViewById(R.id.lvListaMaterii);


        ChipGroup chipGroup = findViewById(R.id.cgSortare);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->{
            if(result.getResultCode()==RESULT_OK) {
                if(result.getData().hasExtra("addMaterie")) {
                    Intent intent = result.getData();
                    Materie materie = (Materie) intent.getSerializableExtra("addMaterie");

                    materie.setOrarId(orarId);

                    if (materie != null) {
//                    listaMaterii.add(materie);
//                    adapter.notifyDataSetChanged();

                        materiiDAO.insertMaterie(materie);
                        listaDBMaterii.clear();

                        listaDBMaterii.addAll(materiiDAO.getMateriiOrar(orarId));
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

                        listaDBMaterii.clear();
                        listaDBMaterii.addAll(materiiDAO.getMateriiOrar(orarId));
                        adapter.notifyDataSetChanged();

                    }
                }else if(result.getData().hasExtra("materieStearsa")){
                    listaDBMaterii.clear();
                    listaDBMaterii.addAll(materiiDAO.getMateriiOrar(orarId));
                    adapter.notifyDataSetChanged();
                }
            }
        });

        adapter =new AdapterMaterie(getApplicationContext(), layout.card_materie,listaDBMaterii, getLayoutInflater(),launcher);
        lvListaMaterii.setAdapter(adapter);

        //getMateriiFromHttps();

        // ========= Butoane ==========

        BottomNavigationView btmNav = findViewById(id.btmNav);
        btmNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int id = item.getItemId();

                if(id ==R.id.pgMaterii){
                    return true;
                } else if (id == R.id.pgOrar) {
                    intent = new Intent(getApplicationContext(), PaginaOrar.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.pgAnunturi) {
                    intent = new Intent(getApplicationContext(), PaginaTasks.class);
                    Log.i("ORAR_ID_MAT",orarId.toString());
                    intent.putExtra("orarIdPtTasks", orarId);
                    launcher.launch(intent);
                    return true;
                }else if (id == R.id.pgNotite) {
                    intent = new Intent(getApplicationContext(), PaginaNote.class);
                    intent.putExtra("orarIdPtNote", orarId);
                    launcher.launch(intent);
                    return true;
                }

                return false;
            }
        });

        fabAdaugaMaterie = findViewById(R.id.fabAdaugaMaterie);
        fabAdaugaMaterie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdaugareMaterie.class);
                //startActivity(intent);
                launcher.launch(intent);
            }
        });

        FloatingActionButton fabBack = findViewById(R.id.fabBack);
        fabBack.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) {
                listaDBMaterii.clear();
                listaDBMaterii.addAll(materiiDAO.getMateriiOrar(orarId));
                adapter.notifyDataSetChanged();
            }else{
                int checkedId = checkedIds.get(0);

                if (checkedId == R.id.chipAZ) {
                    Collections.sort(listaDBMaterii, Comparator.comparing(Materie::getNumeMaterie));

                } else if (checkedId == R.id.chipZA) {
                    Collections.sort(listaDBMaterii, (m1, m2) -> m2.getNumeMaterie().compareTo(m1.getNumeMaterie()));

                } else if (checkedId == R.id.chipAssignments) {
                    Collections.sort(listaDBMaterii, (m1, m2) -> Integer.compare(m2.getNoAssignments(), m1.getNoAssignments()));
                }
                adapter.notifyDataSetChanged();
            }

        });
    }


    private static void getMateriiFromHttps(){
        Thread thread=new Thread(){
            @Override
            public void run() {
                HttpsManager manager = new HttpsManager(URL_MATERII);
                String json = manager.procesare();

                new Handler(Looper.getMainLooper()).post(()->{
                    listaDBMaterii.addAll(MaterieParser.parseJSON(json));
                    adapter.notifyDataSetChanged();

                });
            }
        };
        thread.start();
    }

    private void incarcaTasksFromFire(Callback callback) {
        DatabaseReference tasksRef = firebaseService.getReference("tasks");
        tasksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Task> tempTaskList = new ArrayList<>();
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                    Task task = taskSnapshot.getValue(Task.class);
                    if (task != null && task.getOrarId().equals(orarId)) {
                        tempTaskList.add(task);
                    }
                }
                callback.onTaskListLoaded(tempTaskList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", error.getMessage());
            }
        });
    }

}