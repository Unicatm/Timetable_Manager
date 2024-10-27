package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.clase.Categorie;
import com.example.androidproject.clase.MateriiManager;
import com.example.androidproject.clase.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdaugareTask extends AppCompatActivity  {

    FloatingActionButton fabBackBtn;
    Button btnAdaugaTask;
    List<String> listaDenMaterii = MateriiManager.getMateriiList();
    List<String> categoriiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adaugare_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ========= Butoane ==========

        fabBackBtn = findViewById(R.id.fabBackBtn);
        fabBackBtn.setOnClickListener(v -> finish());


        EditText etDenMaterie = findViewById(R.id.etDenMaterie);
        Spinner spnMaterie = findViewById(R.id.spnMaterie);
        EditText etDeadline = findViewById(R.id.etDeadline);
        Spinner spnTip = findViewById(R.id.spnTip);
        EditText etDescriere = findViewById(R.id.etDescriere);

        ArrayAdapter<String> adapterDenMaterie = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listaDenMaterii);
        spnMaterie.setAdapter(adapterDenMaterie);

        categoriiList = new ArrayList<>();
        for (Categorie categ : Categorie.values()) {
            categoriiList.add(categ.name());
        }

        ArrayAdapter<String> adapterCateg = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoriiList);
        spnTip.setAdapter(adapterCateg);

        btnAdaugaTask = findViewById(R.id.btnAdaugaTask);
        btnAdaugaTask.setOnClickListener(view->{
                String denTask = String.valueOf(etDenMaterie.getText());
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Date deadline =null;
                try {
                    deadline = sdf.parse(etDeadline.getText().toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                String materie = spnMaterie.getSelectedItem().toString();
                Categorie tipDdl = Categorie.valueOf(spnTip.getSelectedItem().toString());
                String descriere = String.valueOf(etDescriere.getText());

                Task task = new Task(denTask,materie,deadline,tipDdl,descriere);


                Intent intent = getIntent();
                intent.putExtra("taskFromIntent", task);
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
        });


    }
}