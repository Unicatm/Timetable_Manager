package com.example.androidproject;

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
import com.example.androidproject.clase.Nota;
import com.example.androidproject.clase.Task;
import com.example.androidproject.clase.TaskManager;
import com.example.androidproject.customAdapters.AdapterTask;
import com.example.androidproject.dataBases.AplicatieDAO;
import com.example.androidproject.dataBases.AplicatieDB;
import com.example.androidproject.dataBases.MaterieDAO;
import com.example.androidproject.dataBases.MaterieDB;
import com.example.androidproject.dataBases.TasksDAO;
import com.example.androidproject.dataBases.TasksDB;
import com.example.androidproject.jsonHttps.HttpsManager;
import com.example.androidproject.jsonHttps.MaterieParser;
import com.example.androidproject.jsonHttps.TaskParser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PaginaTasks extends AppCompatActivity {
    private FloatingActionButton fabAdaugaTask;
    private ListView lvListaTasks;
    private List<Task> listaTasks = new ArrayList<>();
    private static List<Task> listaDBTasks = new ArrayList<>();

    //private TaskManager listaTasks = new TaskManager();
    private ActivityResultLauncher<Intent> launcher;
    private static AdapterTask adapter;
    private Long orarId;
    private static TasksDAO tasksDAO;
    private final static String URL_TASKS = "https://www.jsonkeeper.com/b/V7VS";
    private FirebaseService firebaseService;
    private int ItemClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagina_tasks);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseService = FirebaseService.getInstance();

        AplicatieDB aplicatieDB = AplicatieDB.getInstance(getApplicationContext());
        MaterieDAO materieDAO = aplicatieDB.getMaterieDAO();


        orarId = getIntent().getLongExtra("orarIdPtTasks", -1L);
        Log.i("ORAR_ID",orarId.toString());

        listaDBTasks.clear();
        incarcaTasksFromFire();


        lvListaTasks = findViewById(R.id.lvListaTasks);
        adapter = new AdapterTask(getApplicationContext(), R.layout.card_task, listaDBTasks,getLayoutInflater(),launcher,orarId);
        lvListaTasks.setAdapter(adapter);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->{
            Log.d("DEBUG", "onActivityResult a fost apelat cu codul " + result.getResultCode());
            if(result.getResultCode() == RESULT_OK) {
                if (result.getData().hasExtra("taskFromIntent")) {
                    Intent intent = result.getData();
                    Task task = (Task) intent.getSerializableExtra("taskFromIntent");

                    if (task != null) {
//                   listaDBTasks.add(task);
//                   adapter.notifyDataSetChanged();

                        firebaseService.insertTask(task);
                        incarcaTasksFromFire();
                    }
                } else if (result.getData().hasExtra("taskEditat")) {
                    Intent intent = result.getData();
                    Task task = (Task) intent.getSerializableExtra("taskEditat");

                    firebaseService.updateTask(task);
                    incarcaTasksFromFire();
                } else if (result.getData().hasExtra("taskSters")) {
                    Intent intent = result.getData();
                    Task taskSters = (Task) intent.getSerializableExtra("taskSters");
                    Log.e("STERG", result.getData().toString());
                    if (taskSters != null) {
                        firebaseService.deleteTask(taskSters);
                        incarcaTasksFromFire();
                    }
                }
            }
        });


//        getTasksFromHttps();

        BottomNavigationView btmNav = findViewById(R.id.btmNav);
        btmNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int id = item.getItemId();

                if(id ==R.id.pgMaterii){
                    intent =new Intent (getApplicationContext(), PaginaMaterii.class);
                    intent.putExtra("orarIdPtMaterii", orarId);
                    Log.i("ORAR_ID_MAT_T",orarId.toString());
                    finish();
                    return true;
                } else if (id == R.id.pgOrar) {
                    intent = new Intent(getApplicationContext(), PaginaOrar.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.pgAnunturi) {
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

        lvListaTasks.setOnItemClickListener((adapterView,view,position,l)->{
            ItemClicked = position;

            Task task = listaDBTasks.get(position);
            Intent intent = new Intent(getApplicationContext(), AdaugareTask.class);
            intent.putExtra("orarIdAdaugare", orarId);
            intent.putExtra("editTask",task);
            launcher.launch(intent);
        });

        fabAdaugaTask = findViewById(R.id.fabAdaugaTask);
        fabAdaugaTask.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AdaugareTask.class);
            intent.putExtra("orarIdAdaugare", orarId);
            launcher.launch(intent);
        });

    }

    private static void getTasksFromHttps(){
        Thread thread=new Thread(){
            @Override
            public void run() {
                HttpsManager manager = new HttpsManager(URL_TASKS);
                String json = manager.procesare();

                new Handler(Looper.getMainLooper()).post(()->{
                    listaDBTasks.addAll(TaskParser.parseJSON(json));
                    adapter.notifyDataSetChanged();

                });
            }
        };
        thread.start();
    }

    private void incarcaTasksFromFire() {
        DatabaseReference tasksRef = firebaseService.getReference("tasks");
        tasksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDBTasks.clear();
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                    Task task = taskSnapshot.getValue(Task.class);
                    if (task != null && task.getOrarId().equals(orarId)) {
                        listaDBTasks.add(task);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", error.getMessage());
            }
        });
    }


}