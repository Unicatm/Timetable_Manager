package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.clase.Orar;
import com.example.androidproject.customAdapters.AdapterOrar;
import com.example.androidproject.dataBases.AplicatieDAO;
import com.example.androidproject.dataBases.AplicatieDB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Orar> lista = new ArrayList<>();
    private ListView lvOrare;
    private ActivityResultLauncher<Intent> launcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        AplicatieDB aplicatieDB = AplicatieDB.getInstance(getApplicationContext());
        AplicatieDAO aplicatieDAO = aplicatieDB.getAplicatieDAO();

        lvOrare = findViewById(R.id.lvOrare);
        ArrayAdapter<Orar> adapterOrare = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,lista);
        //AdapterOrar adapterOrare = new AdapterOrar(getApplicationContext(), R.layout.card_orar,lista,getLayoutInflater(),launcher);
        lvOrare.setAdapter(adapterOrare);

        lista.addAll(aplicatieDAO.getOrare());

        Date oraStart;
        Date oraFinal;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            oraStart = sdf.parse("07:00");
            oraFinal = sdf.parse("18:00");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->{
            if(result.getResultCode()==RESULT_OK) {
                if(result.getData().hasExtra("addOrar")){
                    Intent intent = result.getData();
                    Orar orar = (Orar) intent.getSerializableExtra("addOrar");

                    if(orar!=null){
                        aplicatieDAO.insertOrar(orar);
                        lista.clear();
                        lista.addAll(aplicatieDAO.getOrare());
                        adapterOrare.notifyDataSetChanged();
                    }
                }
            }
        });

        Button btnCreeareOrar =findViewById(R.id.btnStart);
        btnCreeareOrar.setOnClickListener(v-> {
            Intent intent = new Intent(getApplicationContext(), CreeareOrar.class);
            launcher.launch(intent);
        });

        lvOrare.setOnItemClickListener((parent,view,position,id)->{
            Orar orarSelectat = lista.get(position);
            Intent intent = new Intent(getApplicationContext(), PaginaMaterii.class);
            intent.putExtra("orarId", orarSelectat.getId());
            launcher.launch(intent);
        });

    }

}