package com.example.androidproject.customAdapters;

import android.annotation.SuppressLint;
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
import androidx.core.content.ContextCompat;

import com.example.androidproject.AdaugareTask;
import com.example.androidproject.R;
import com.example.androidproject.clase.Nota;
import com.example.androidproject.clase.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class AdapterNota extends ArrayAdapter<Nota> {
    private Context context;
    private int layoutId;
    private List<Nota> note;
    private LayoutInflater layoutInflater;
    private long orarId;

    public AdapterNota(@NonNull Context context, int layoutId, @NonNull List<Nota> note, LayoutInflater layoutInflater, long orarId) {
        super(context, layoutId, note);

        this.context = context;
        this.layoutId=layoutId;
        this.note = note;
        this.layoutInflater=layoutInflater;
        this.orarId=orarId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(layoutId, parent, false);

        Nota nota = note.get(position);

        TextView tvMat = view.findViewById(R.id.tvNotaMat);
        TextView tvNota = view.findViewById(R.id.tvNotaGrade);

        tvMat.setText(nota.getMaterie());
        tvNota.setText(String.valueOf(nota.getNota()));

        return view;
    }
}
