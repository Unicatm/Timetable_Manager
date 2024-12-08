package com.example.androidproject.customAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidproject.AdaugareMaterie;
import com.example.androidproject.CreeareOrar;
import com.example.androidproject.R;
import com.example.androidproject.clase.Materie;
import com.example.androidproject.clase.Orar;
import com.example.androidproject.dataBases.AplicatieDAO;
import com.example.androidproject.dataBases.AplicatieDB;
import com.example.androidproject.dataBases.MaterieDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdapterOrar extends ArrayAdapter<Orar> {
    private Context context;
    private int layoutId;
    private List<Orar> lista;
    private LayoutInflater layoutInflater;

    public AdapterOrar(@NonNull Context context, int layoutId, @NonNull List<Orar> lista, LayoutInflater layoutInflater) {
        super(context, layoutId, lista);

        this.context = context;
        this.layoutId=layoutId;
        this.lista = lista;
        this.layoutInflater=layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(layoutId, parent, false);

        Orar orar = lista.get(position);

        AplicatieDB aplicatieDB = AplicatieDB.getInstance(context);
        MaterieDAO materiiDAO = aplicatieDB.getMaterieDAO();

        TextView tvFac = view.findViewById(R.id.tvFac);
        TextView tvSemestru = view.findViewById(R.id.tvSemestru);
        TextView tvNoMaterii = view.findViewById(R.id.tvNoMaterii);
        //FloatingActionButton btnEdit = view.findViewById(R.id.fabSetariOrar);

        tvFac.setText(orar.getFacultate());
        tvSemestru.setText("Semestrul "+orar.getSemestru());
        tvNoMaterii.setText(materiiDAO.getNoMaterii(orar.getId()) + " materii");


//        btnEdit.setOnClickListener(v -> {
//            Intent intent = new Intent(context.getApplicationContext(), CreeareOrar.class);
//            intent.putExtra("editOrar", orar);
//            launcher.launch(intent);
//        });

        return view;
    }
}
