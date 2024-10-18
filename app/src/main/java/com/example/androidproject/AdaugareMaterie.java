package com.example.androidproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdaugareMaterie extends AppCompatActivity {

    Button btnAdaugaMaterie;
    FloatingActionButton fabBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adaugare_materie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ========= Butoane ==========
        btnAdaugaMaterie = findViewById(R.id.btnAdaugaMaterie);
        btnAdaugaMaterie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdaugareMaterie.this, PaginaMaterii.class);
                startActivity(intent);
            }
        });

        fabBackBtn = findViewById(R.id.fabBackBtn);
        fabBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdaugareMaterie.this, PaginaMaterii.class);
                startActivity(intent);
            }
        });

    }
}
