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
    private final static String URL_TASKS = "https://www.jsonkeeper.com/b/V7VS";

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

        AplicatieDB aplicatieDB = AplicatieDB.getInstance(getApplicationContext());
        TasksDAO tasksDAO = aplicatieDB.getTasksDAO();
        MaterieDAO materieDAO = aplicatieDB.getMaterieDAO();

//        tasksDB =TasksDB.getInstance(getApplicationContext());
//        tasksDAO = tasksDB.getTaskDAO();

        Long orarId = getIntent().getLongExtra("orarIdPtTasks", -1L);
        Log.i("ORAR_ID",orarId.toString());

        listaDBTasks=tasksDAO.getTasksForOrar(orarId);
        lvListaTasks = findViewById(R.id.lvListaTasks);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->{
           if(result.getResultCode() == RESULT_OK){
               if(result.getData().hasExtra("taskFromIntent")) {
                   Intent intent = result.getData();
                   Task task = (Task) intent.getSerializableExtra("taskFromIntent");
                   Materie materieDinTask = materieDAO.getMaterieByName(task.getDenMaterie());
                   Log.i("TASK",materieDinTask.getId().toString());
                   task.setMaterieId(materieDinTask.getId());

                   if (task != null) {
                   //listaTasks.add(task);
                   //adapter.notifyDataSetChanged();

                       tasksDAO.insertTask(task);
                       listaDBTasks.clear();
                       listaDBTasks.addAll(tasksDAO.getTasksForOrar(orarId));
                       adapter.notifyDataSetChanged();
                   }
               }else if(result.getData().hasExtra("taskEditat")){
                   Intent intent = result.getData();
                   Task task = (Task) intent.getSerializableExtra("taskEditat");

                   if(task!=null){
                       Task taskDeEditat = (Task) intent.getSerializableExtra("editTask");

                       taskDeEditat.setNumeTask(task.getNumeTask());
                       taskDeEditat.setDenMaterie(task.getDenMaterie());
                       taskDeEditat.setDescriere(task.getDescriere());
                       taskDeEditat.setDataDeadline(task.getDataDeadline());
                       taskDeEditat.setTipDdl(task.getTipDdl());

                       tasksDAO.updateTask(taskDeEditat);
//
                       listaDBTasks.clear();
                       listaDBTasks.addAll(tasksDAO.getTasksForOrar(orarId));
                       adapter.notifyDataSetChanged();
                   }
               }else if(result.getData().hasExtra("taskSters")){

                   Intent intent = result.getData();
                   Task task = (Task) intent.getSerializableExtra("taskSters");

                   if(task!=null){
                       tasksDAO.deleteTask(task);
                       listaDBTasks.clear();
                       listaDBTasks.addAll(tasksDAO.getTasksForOrar(orarId));
                       adapter.notifyDataSetChanged();
                   }
               }

           }
        });

        adapter = new AdapterTask(getApplicationContext(), R.layout.card_task, listaDBTasks,getLayoutInflater(),launcher);
        lvListaTasks.setAdapter(adapter);

        getTasksFromHttps();

        BottomNavigationView btmNav = findViewById(R.id.btmNav);
        btmNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int id = item.getItemId();

                if(id ==R.id.pgMaterii){
                    intent =new Intent (getApplicationContext(), PaginaMaterii.class);
                    intent.putExtra("orarIdPtMaterii", orarId);
                    setResult(RESULT_OK, intent);
                    finish();
                    return true;
                } else if (id == R.id.pgOrar) {
                    intent = new Intent(getApplicationContext(), PaginaOrar.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.pgAnunturi) {
                    return true;
                }else if (id == R.id.pgNotite) {
                    intent = new Intent(getApplicationContext(), PaginaNotite.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
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
}