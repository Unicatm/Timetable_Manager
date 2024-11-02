package com.example.androidproject;

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

import com.example.androidproject.clase.Task;
import com.example.androidproject.clase.TaskManager;
import com.example.androidproject.customAdapters.AdapterTask;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class PaginaTasks extends AppCompatActivity {
    private FloatingActionButton fabAdaugaTask;
    private ListView lvListaTasks;
    private List<Task> listaTasks = new ArrayList<>();
    //private TaskManager listaTasks = new TaskManager();
    private ActivityResultLauncher<Intent> launcher;
    private AdapterTask adapter;

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

        lvListaTasks = findViewById(R.id.lvListaTasks);
        adapter = new AdapterTask(getApplicationContext(), R.layout.card_task, listaTasks,getLayoutInflater());
        lvListaTasks.setAdapter(adapter);

        // ========= NAVIGATION ==========
        BottomNavigationView btmNav = findViewById(R.id.btmNav);
        btmNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int id = item.getItemId();

                if(id ==R.id.pgMaterii){
                    intent = new Intent(PaginaTasks.this, PaginaMaterii.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    launcher.launch(intent);
                    return true;
                } else if (id == R.id.pgOrar) {
                    intent = new Intent(PaginaTasks.this, PaginaOrar.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    launcher.launch(intent);
                    return true;
                } else if (id == R.id.pgAnunturi) {
                    intent = new Intent(PaginaTasks.this, PaginaTasks.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    launcher.launch(intent);
                    return true;
                }else if (id == R.id.pgNotite) {
                    intent = new Intent(PaginaTasks.this, PaginaNotite.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    launcher.launch(intent);
                    return true;
                }

                return false;
            }
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->{
           if(result.getResultCode() == RESULT_OK){
               Intent intent = result.getData();
               Task task = (Task) intent.getSerializableExtra("taskFromIntent");
               if (task!=null){
                   listaTasks.add(task);
                   adapter.notifyDataSetChanged();
                   //Toast.makeText(this, task.toString(), Toast.LENGTH_SHORT).show();
               }else{
               }
               //Toast.makeText(this,task.toString(),Toast.LENGTH_SHORT).show();

           }
        });

        fabAdaugaTask = findViewById(R.id.fabAdaugaTask);
        fabAdaugaTask.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AdaugareTask.class);
            //startActivity(intent);
            launcher.launch(intent);
        });

    }
}