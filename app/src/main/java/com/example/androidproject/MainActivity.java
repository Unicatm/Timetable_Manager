package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.example.androidproject.dataBases.TasksDAO;
import com.example.androidproject.jsonHttps.HttpsManager;
import com.example.androidproject.jsonHttps.OrarParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static List<Orar> lista = new ArrayList<>();
    private static AdapterOrar adapterOrare;
    private ListView lvOrare;
    private ActivityResultLauncher<Intent> launcher;
    private static final String URL_ORARE = "https://www.jsonkeeper.com/b/G4PH";
    private FirebaseService firebaseService;


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
        TasksDAO tasksDAO = aplicatieDB.getTasksDAO();

//        tasksDAO.deleteAllTasks();


        lvOrare = findViewById(R.id.lvOrare);
        //ArrayAdapter<Orar> adapterOrare =new ArrayAdapter<>(getApplicationContext(),R.layout.s,lista);
        adapterOrare = new AdapterOrar(getApplicationContext(), R.layout.card_orar,lista,getLayoutInflater());
        lvOrare.setAdapter(adapterOrare);

        lista.clear();
        lista.addAll(aplicatieDAO.getOrare());


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
                }else if(result.getData().hasExtra("orarSters")){
                    lista.clear();
                    lista.addAll(aplicatieDAO.getOrare());
                    adapterOrare.notifyDataSetChanged();
                }
            }
        });

        //getOrareFromHttps();


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

        lvOrare.setOnItemLongClickListener((parent, view, position, id) -> {
            Orar orar = lista.get(position);
            Intent intent = new Intent(getApplicationContext(), CreeareOrar.class);
            intent.putExtra("editOrar", orar);
            launcher.launch(intent);
            return true;
        });

    }

    private static void getOrareFromHttps(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                HttpsManager manager = new HttpsManager(URL_ORARE);
                String json = manager.procesare();

                new Handler(Looper.getMainLooper()).post(()->{
                    lista.addAll(OrarParser.parseJson(json));
                    adapterOrare.notifyDataSetChanged();
                });
            }
        };
        thread.start();
    }

}