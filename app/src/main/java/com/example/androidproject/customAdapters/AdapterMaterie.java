package com.example.androidproject.customAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidproject.R;
import com.example.androidproject.clase.Materie;

import java.util.List;

public class AdapterMaterie extends ArrayAdapter<Materie> {

    private Context context;
    private int layoutId;
    private List<Materie> materii;
    private LayoutInflater layoutInflater;

    public AdapterMaterie(@NonNull Context context, int layoutId, @NonNull List<Materie> materii, LayoutInflater layoutInflater) {
        super(context, layoutId, materii);

        this.context = context;
        this.layoutId=layoutId;
        this.materii = materii;
        this.layoutInflater=layoutInflater;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(layoutId, parent, false);

        Materie materie = materii.get(position);

        TextView tvDenMaterie = view.findViewById(R.id.tvDenMaterie);
        TextView tvNoAssingments = view.findViewById(R.id.tvNoOfAssignments);
        TextView tvPerioada = view.findViewById(R.id.tvPerioada);

        tvDenMaterie.setText(materie.getNumeMaterie());
        if (materie.getSaptamanal()) {
            tvPerioada.setText("Weekly");
        } else {

            if(materie.getTipSaptamana().compareTo("para")==0){
                tvPerioada.setText("Saptamana para");
            }else{
                tvPerioada.setText("Saptamana impara");
            }
        }
        //MAI E DE ADAUGAT NR DE ASSINGMENTS TOTAL

        return view;
    }
}
