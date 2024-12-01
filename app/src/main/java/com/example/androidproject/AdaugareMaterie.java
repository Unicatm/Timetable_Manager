package com.example.androidproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
    private boolean isEditing=false;

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
        TextView tvTitlu = findViewById(R.id.tvTitlu);

        EditText etDenMaterie = findViewById(R.id.etDenMaterie);
        EditText etSala = findViewById(R.id.etSala);
        CheckBox ckbFrecventa = findViewById(R.id.ckbFrecventa);
        RadioGroup rgFrecventa = findViewById(R.id.rgFrecventa);
        RadioButton rbSaptamanaPara = findViewById(R.id.rbSaptamanaPara);
        RadioButton rbSaptamanaImpara = findViewById(R.id.rbSaptamanaImpara);

        ckbFrecventa.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(ckbFrecventa.isChecked()){
                rgFrecventa.setVisibility(View.VISIBLE);
            }else{
                rgFrecventa.setVisibility(View.GONE);
            }
        });

        Intent editIntent = getIntent();

        if(editIntent.hasExtra("editMaterie")){
            isEditing = true;
            btnAdaugaMaterie.setText("Salveaza modificarile");
            tvTitlu.setText("Editeaza materia");

            Materie materieApasata = (Materie) editIntent.getSerializableExtra("editMaterie");

            etDenMaterie.setText(materieApasata.getNumeMaterie());
            etSala.setText(materieApasata.getSala());

            if(!materieApasata.getSaptamanal()){
                ckbFrecventa.setChecked(true);
                rgFrecventa.setVisibility(View.VISIBLE);

                switch (materieApasata.getTipSaptamana()){
                    case "para": {
                        rgFrecventa.check(R.id.rbSaptamanaPara);
                        break;
                    }
                    case "impara": {
                        rgFrecventa.check(R.id.rbSaptamanaImpara);
                        break;
                    }
                }
            }else if(materieApasata.getSaptamanal()){
                ckbFrecventa.setChecked(false);
                rgFrecventa.setVisibility(View.GONE);
            }

        }


        btnAdaugaMaterie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String denMaterie = String.valueOf(etDenMaterie.getText());
                String nrSala = String.valueOf(etSala.getText());
                Boolean isWeekly = !ckbFrecventa.isChecked();

                int checkedId = rgFrecventa.getCheckedRadioButtonId();
                String tipSaptamana="";
                Materie materie;

                if(ckbFrecventa.isChecked()){
                    if (checkedId == rbSaptamanaPara.getId()) {
                        tipSaptamana = "para";
                    } else if (checkedId == rbSaptamanaImpara.getId()) {
                        tipSaptamana = "impara";
                    }

                    materie = new Materie(denMaterie,nrSala, isWeekly, tipSaptamana);
                }else{
                    materie = new Materie(denMaterie, nrSala, isWeekly,"");
                }


////                Am adaugat denumirea materiei in lista din MateriiManager
//                MateriiManager.adaugaMaterie(materie);

                Intent intent = getIntent();
                if(isEditing){
                    intent.putExtra("materieEditata", materie);
                    isEditing = false;
                }else{
                    intent.putExtra("addMaterie",materie);
                }

                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
