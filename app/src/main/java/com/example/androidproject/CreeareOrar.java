package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.clase.Orar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreeareOrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_creeare_orar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etAddFac = findViewById(R.id.etAddFac);
        Spinner spnAnFac = findViewById(R.id.spnAnFac);
        Spinner spnStartOrar = findViewById(R.id.spnStartOrar);
        Spinner spnFinalOrar = findViewById(R.id.spnFinalOrar);
        Spinner spnSem = findViewById(R.id.spnSemestru);

        String[] arrAniFacultate = {"1","2","3","4"};
        String[] arrOreStart = {"07:00","07:30","08:00","08:30","09:00"};
        String[] arrOreFinal = {"17:00","17:30","18:00","18:30","19:00","19:30"};
        String[] arrSemestre = {"1","2","3","4"};


        ArrayAdapter adapterAn =new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrAniFacultate);
        spnAnFac.setAdapter(adapterAn);

        ArrayAdapter adapterOraStart =new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrOreStart);
        spnStartOrar.setAdapter(adapterOraStart);

        ArrayAdapter adapterOraFin =new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrOreFinal);
        spnFinalOrar.setAdapter(adapterOraFin);

        ArrayAdapter adapterSem = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,arrSemestre);
        spnSem.setAdapter(adapterSem);


        Button btnCreeareOrar =findViewById(R.id.btnCreeareOrar);
        Button  btnAnulare = findViewById(R.id.btnAnulare);
        FloatingActionButton fabBackBtn = findViewById(R.id.fabBackBtn);

        // ========= Butoane ==========


        btnCreeareOrar.setOnClickListener(v-> {

            String fac = etAddFac.getText().toString();
            String an = spnAnFac.getSelectedItem().toString();
            String semestru = spnSem.getSelectedItem().toString();
            Date oraStart;
            Date oraFinal;
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            try {
               oraStart = sdf.parse(spnStartOrar.getSelectedItem().toString());
               oraFinal = sdf.parse(spnFinalOrar.getSelectedItem().toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Orar orar = new Orar(fac,an,semestru,oraStart,oraFinal);


            Intent intent = getIntent();
            intent.putExtra("addOrar",orar);
            setResult(RESULT_OK,intent);
            finish();
        });

        btnAnulare.setOnClickListener(v-> {
            Intent intent = new Intent(CreeareOrar.this, MainActivity.class);
            startActivity(intent);
        });


        fabBackBtn.setOnClickListener(v-> {
            Intent intent = new Intent(CreeareOrar.this, MainActivity.class);
            startActivity(intent);
        });
    }
}