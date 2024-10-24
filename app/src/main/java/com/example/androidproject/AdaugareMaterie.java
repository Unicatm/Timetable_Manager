package com.example.androidproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.clase.Materie;
import com.example.androidproject.clase.MateriiManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdaugareMaterie extends AppCompatActivity {

    private Button btnAdaugaMaterie;
     private FloatingActionButton fabBackBtn;

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

        // ========= BUTOANE ==========
        btnAdaugaMaterie = findViewById(R.id.btnAdaugaMaterie);


        fabBackBtn = findViewById(R.id.fabBackBtn);
        fabBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdaugareMaterie.this, PaginaMaterii.class);
                startActivity(intent);
            }
        });

        // ========= COMPONENTE VIZUALE =========
        EditText etDenMaterie = findViewById(R.id.etDenMaterie);
        EditText etSala = findViewById(R.id.etSala);
        CheckBox ckbFrecventa = findViewById(R.id.ckbFrecventa);

        btnAdaugaMaterie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String denMaterie = String.valueOf(etDenMaterie.getText());
                String nrSala = String.valueOf(etSala.getText());
                Boolean isWeekly = ckbFrecventa.isChecked() ? true:false;

                Materie materie = new Materie(denMaterie,nrSala,isWeekly);

                //Am adaugat denumirea materiei in lista din MateriiManager
                MateriiManager.adaugaMaterie(denMaterie);

                Intent intent = getIntent();
                intent.putExtra("materieFromIntent",materie);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
