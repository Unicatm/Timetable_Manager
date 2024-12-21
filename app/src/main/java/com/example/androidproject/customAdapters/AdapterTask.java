package com.example.androidproject.customAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.androidproject.AdaugareMaterie;
import com.example.androidproject.AdaugareTask;
import com.example.androidproject.R;
import com.example.androidproject.clase.Materie;
import com.example.androidproject.clase.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class AdapterTask extends ArrayAdapter<Task> {

    private Context context;
    private int layoutId;
    private List<Task> tasks;
    private LayoutInflater layoutInflater;
    private ActivityResultLauncher<Intent> launcher;
    private long orarId;

    public AdapterTask(@NonNull Context context, int layoutId, @NonNull List<Task> tasks, LayoutInflater layoutInflater,ActivityResultLauncher<Intent> launcher,long orarId) {
        super(context, layoutId, tasks);

        this.context = context;
        this.layoutId=layoutId;
        this.tasks = tasks;
        this.layoutInflater=layoutInflater;
        this.launcher=launcher;
        this.orarId=orarId;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(layoutId, parent, false);

        Task task = tasks.get(position);

        MaterialCardView card = view.findViewById(R.id.cardTask);
        FloatingActionButton fabSetariTask = view.findViewById(R.id.fabSetariTask);

        TextView tvDenTask = view.findViewById(R.id.tvDenTask);
        TextView tvDescrTask = view.findViewById(R.id.tvDescTask);
        TextView tvDenMaterie = view.findViewById(R.id.tvDenMaterie);
        TextView tvDeadline = view.findViewById(R.id.tvDeadline);
        TextView tvTimeLeft = view.findViewById(R.id.tvTimeLeft);

        tvDenTask.setText(task.getNumeTask());
        tvDescrTask.setText(task.getDescriere());
        tvDenMaterie.setText(task.getDenMaterie());
        tvDeadline.setText(new SimpleDateFormat("dd.MM.yyyy").format(task.getDataDeadline()));

        //TIMP RAMAS DDL
        LocalDate dataCurenta = LocalDate.now();

        LocalDate deadline = task.getDataDeadline().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        long zileRamase = ChronoUnit.DAYS.between(dataCurenta, deadline);

        tvTimeLeft.setText(zileRamase + " zile");

        if(zileRamase<7){
            card.setCardBackgroundColor(ContextCompat.getColor(this.getContext(),R.color.whitePurple));
            card.setStrokeColor(ContextCompat.getColor(this.getContext(), R.color.darkPurple));
            tvTimeLeft.setTextColor(ContextCompat.getColor(this.getContext(), R.color.darkPurple));
        }

        fabSetariTask.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext(), AdaugareTask.class);
            intent.putExtra("editTask", position);
            intent.putExtra("orarIdAdaugare",orarId);
            launcher.launch(intent);
        });

        return view;
    }

}
