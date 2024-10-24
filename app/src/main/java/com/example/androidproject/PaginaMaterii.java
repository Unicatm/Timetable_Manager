package com.example.androidproject;

import static com.example.androidproject.R.*;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class PaginaMaterii extends AppCompatActivity {

    //BottomNavigationView btmNav = findViewById(R.id.btmNav);
    private FloatingActionButton fabAdaugaMaterie;
    private List<Materie> listaMaterii= new ArrayList<>();
    private ListView lvListaBilete;
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

        // ========= Navigation ==========

        BottomNavigationView btmNav = findViewById(R.id.btmNav);

        // UITA-TE AICI PT PROBLEMA DE LA TASKS CU AFISAREA!!!!!
        btmNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int id = item.getItemId();

                if(id ==R.id.pgMaterii){
                    intent = new Intent(PaginaMaterii.this, PaginaMaterii.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.pgOrar) {
                    return true;
                } else if (id == R.id.pgAnunturi) {
                    intent = new Intent(PaginaMaterii.this, PaginaTasks.class);
                    startActivity(intent);
                    return true;
                }else if (id == R.id.pgNotite) {
                    return true;
                }

                return false;
            }
        });


        lvListaBilete = findViewById(R.id.lvListaMaterii);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->{
            if(result.getResultCode()==RESULT_OK){
                Intent intent = result.getData();
                Materie materie = (Materie) intent.getSerializableExtra("materieFromIntent");
                if(materie!=null){
                    listaMaterii.add(materie);
                    ArrayAdapter<Materie> adapter =new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,listaMaterii);
                    lvListaBilete.setAdapter(adapter);
                }
            }


        });


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
    }

}