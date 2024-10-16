package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreeareOrar extends AppCompatActivity {

    Spinner spnAnFac;
    Spinner spnStartOrar;
    Spinner spnFinalOrar;
    Spinner spnDurataActivitate;

    String[] arrAniFacultate = {"1","2","3","4"};
    String[] arrOreStart = {"7:00","7:30","8:00","8:30","9:00"};
    String[] arrOreFinal = {"17:00","17:30","18:00","18:30","19:00","19:30"};
    String[] arrDurataActivitate = {"1 ora","1:30 ora","2 ore"};

    Button btnCreeareOrar;

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

        btnCreeareOrar =findViewById(R.id.btnCreeareOrar);
        btnCreeareOrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreeareOrar.this, adaugareMaterie.class);
                startActivity(intent);
            }
        });


        spnAnFac = findViewById(R.id.spnAnFac);
        spnStartOrar = findViewById(R.id.spnStartOrar);
        spnFinalOrar = findViewById(R.id.spnFinalOrar);
        spnDurataActivitate = findViewById(R.id.spnDurataActivitate);

        ArrayAdapter adapterAn =new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrAniFacultate);
        adapterAn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAnFac.setAdapter(adapterAn);

        ArrayAdapter adapterOraStart =new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrOreStart);
        adapterOraStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStartOrar.setAdapter(adapterOraStart);

        ArrayAdapter adapterOraFin =new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrOreFinal);
        adapterOraFin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFinalOrar.setAdapter(adapterOraFin);

        ArrayAdapter adapterDurataAct =new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrDurataActivitate);
        adapterOraFin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDurataActivitate.setAdapter(adapterDurataAct);

    }
}