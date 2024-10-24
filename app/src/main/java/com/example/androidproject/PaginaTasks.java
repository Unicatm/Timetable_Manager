package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.clase.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PaginaTasks extends AppCompatActivity {
    private FloatingActionButton fabAdaugaTask;
    private ListView lvListaTasks;
    private List<Task> listaTasks = new ArrayList<>();
    private ActivityResultLauncher<Intent> launcher;

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
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->{
           if(result.getResultCode() == RESULT_OK){
               Intent intent = result.getData();
               Task task = (Task) intent.getSerializableExtra("taskFromIntent");
               if (task!=null){
                    listaTasks.add(task);
                    ArrayAdapter<Task> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listaTasks);
                    lvListaTasks.setAdapter(adapter);
               }
               Toast.makeText(this,task.toString(),Toast.LENGTH_SHORT).show();

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