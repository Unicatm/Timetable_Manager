package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.androidproject.clase.Nota;
import com.example.androidproject.clase.Task;
import com.example.androidproject.dataBases.AplicatieDB;
import com.example.androidproject.dataBases.MaterieDAO;
import com.example.androidproject.dataBases.MaterieDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNota extends AppCompatActivity {

    private Long orarId;
    private List<String> listaDenMateriiDB = new ArrayList<>();
    private Boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_nota);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AplicatieDB aplicatieDB = AplicatieDB.getInstance(getApplicationContext());
        MaterieDAO materieDAO = aplicatieDB.getMaterieDAO();

        Button btnAddNota = findViewById(R.id.btnAddNota);
        Button btnDeleteNota = findViewById(R.id.btnStergereNota);
        Spinner spnMaterie = findViewById(R.id.spnMatNota);
        EditText etNota = findViewById(R.id.etNota);


        orarId = getIntent().getLongExtra("orarIdAdaugare",-1L);
        Log.i("IDORAR",orarId.toString());


        List<Materie> listaMateriiDB = materieDAO.getMateriiOrar(orarId);
        for (Materie materie : listaMateriiDB) {
            listaDenMateriiDB.add(materie.getNumeMaterie());
        }

        ArrayAdapter<String> adapterDenMaterie = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listaDenMateriiDB);
        spnMaterie.setAdapter(adapterDenMaterie);


        Intent editIntent = getIntent();

        if(editIntent.hasExtra("editNota")){
            isEditing = true;
            orarId = getIntent().getLongExtra("orarIdAdaugare",-1L);

            btnDeleteNota.setVisibility(View.VISIBLE);

            Nota notaEdit = (Nota) editIntent.getSerializableExtra("editNota");

            for(int i=0;i<spnMaterie.getCount();i++){
                if(notaEdit.getMaterie().equals(adapterDenMaterie.getItem(i))){
                    spnMaterie.setSelection(i);
                }
            }

            etNota.setText(String.valueOf(notaEdit.getNota()));

            btnDeleteNota.setOnClickListener(v->{
                Intent intent = new Intent(getApplicationContext(), PaginaNote.class);
                intent.putExtra("notaStearsa", notaEdit);
                setResult(RESULT_OK,intent);
                finish();
            });

        }

        btnAddNota.setOnClickListener(view->{
            String materie = spnMaterie.getSelectedItem().toString();
            float nota = Float.valueOf(etNota.getText().toString());

            Nota notaAdd = new Nota(materie,nota,orarId);
            Intent intent = getIntent();

            if(isEditing){
                Nota notaEditata = (Nota) editIntent.getSerializableExtra("editNota");
                if (notaEditata != null) {
                    notaAdd.setId(notaEditata.getId());
                }
                intent.putExtra("NotaEditat",notaAdd);
                isEditing = false;
            }else{
                intent.putExtra("NotaFromIntent", notaAdd);
            }
            setResult(RESULT_OK, intent);
            finish();
        });


        FloatingActionButton fabBackNota = findViewById(R.id.fabBackNota);
        fabBackNota.setOnClickListener(v-> {
            finish();
        });
    }
}