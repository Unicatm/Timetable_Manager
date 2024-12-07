package com.example.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.clase.Categorie;
import com.example.androidproject.clase.Materie;
import com.example.androidproject.clase.MateriiManager;
import com.example.androidproject.clase.Task;
import com.example.androidproject.dataBases.AplicatieDB;
import com.example.androidproject.dataBases.MaterieDAO;
import com.example.androidproject.dataBases.MaterieDB;
import com.example.androidproject.dataBases.TasksDAO;
import com.example.androidproject.dataBases.TasksDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdaugareTask extends AppCompatActivity  {

    private FloatingActionButton fabBackBtn;
    private Button btnAdaugaTask;
    private List<String> listaDenMaterii = MateriiManager.getNumeMateriiList();
    private List<String> categoriiList;
    private List<String> listaDenMateriiDB = new ArrayList<>();
    private Boolean isEditing = false;


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

        AplicatieDB aplicatieDB = AplicatieDB.getInstance(getApplicationContext());
        MaterieDAO materieDAO = aplicatieDB.getMaterieDAO();
        TasksDAO tasksDAO = aplicatieDB.getTasksDAO();

        // ========= Butoane ==========

        Button btnStergeTask = findViewById(R.id.btnStergeTask);

        fabBackBtn = findViewById(R.id.fabBackBtn);
        fabBackBtn.setOnClickListener(v -> finish());


        EditText etDenTask = findViewById(R.id.etDenTask);
        Spinner spnMaterie = findViewById(R.id.spnMaterie);
        EditText etDeadline = findViewById(R.id.etDeadline);
        Spinner spnTip = findViewById(R.id.spnTip);
        EditText etDescriere = findViewById(R.id.etDescriere);

        Long orarId = getIntent().getLongExtra("orarIdAdaugare",-1L);

        List<Materie> listaMateriiDB = materieDAO.getMateriiOrar(orarId);
        for (Materie materie : listaMateriiDB) {
            listaDenMateriiDB.add(materie.getNumeMaterie());
        }

        ArrayAdapter<String> adapterDenMaterie = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listaDenMateriiDB);
        spnMaterie.setAdapter(adapterDenMaterie);

        categoriiList = new ArrayList<>();
        for (Categorie categ : Categorie.values()) {
            categoriiList.add(categ.name());
        }

        ArrayAdapter<String> adapterCateg = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoriiList);
        spnTip.setAdapter(adapterCateg);


        Intent editIntent = getIntent();

        if(editIntent.hasExtra("editTask")){
            isEditing = true;

            TextView tvTitlu = findViewById(R.id.tvTitlu);
            tvTitlu.setText("Editeaza taskul");
            btnStergeTask.setVisibility(View.VISIBLE);

            Button btnAdaugaTask = findViewById(R.id.btnAdaugaTask);
            btnAdaugaTask.setText("Salveaza modificarile");

            Task task = (Task) editIntent.getSerializableExtra("editTask");
            etDenTask.setText(task.getNumeTask());
            for(int i=0;i<spnMaterie.getCount();i++){
                if(task.getDenMaterie().equals(adapterDenMaterie.getItem(i))){
                    spnMaterie.setSelection(i);
                }
            }

            etDeadline.setText(new SimpleDateFormat("dd.MM.yyyy").format(task.getDataDeadline()));

            for(int i=0;i<spnTip.getCount();i++){
                if(task.getTipDdl().equals(adapterCateg.getItem(i))){
                    spnTip.setSelection(i);
                }
            }

            etDescriere.setText(task.getDescriere());

            btnStergeTask.setOnClickListener(v->{
                tasksDAO.deleteTask(task);

                Intent intent = new Intent();
                intent.putExtra("taskSters", true);
                setResult(RESULT_OK,intent);
                finish();
            });

        }

        btnAdaugaTask = findViewById(R.id.btnAdaugaTask);
        btnAdaugaTask.setOnClickListener(view->{
                String denTask = String.valueOf(etDenTask.getText());
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

                Task task = new Task(denTask,materie,deadline,tipDdl,descriere, (long) -1);

                Intent intent = getIntent();

                if(isEditing){
                    intent.putExtra("taskEditat",task);
                }else{
                    intent.putExtra("taskFromIntent", task);
                }
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
        });


    }
}