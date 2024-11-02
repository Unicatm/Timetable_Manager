package com.example.androidproject.customAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidproject.R;
import com.example.androidproject.clase.Materie;
import com.example.androidproject.clase.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterTask extends ArrayAdapter<Task> {

    private Context context;
    private int layoutId;
    private List<Task> tasks;
    private LayoutInflater layoutInflater;

    public AdapterTask(@NonNull Context context, int layoutId, @NonNull List<Task> tasks, LayoutInflater layoutInflater) {
        super(context, layoutId, tasks);

        this.context = context;
        this.layoutId=layoutId;
        this.tasks = tasks;
        this.layoutInflater=layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(layoutId, parent, false);

        Task task = tasks.get(position);

        TextView tvDenTask = view.findViewById(R.id.tvDenTask);
        TextView tvDescrTask = view.findViewById(R.id.tvDescTask);
        TextView tvDenMaterie = view.findViewById(R.id.tvDenMaterie);
        TextView tvDeadline = view.findViewById(R.id.tvDeadline);
        TextView tvTimeLeft = view.findViewById(R.id.tvTimeLeft);

        tvDenTask.setText(task.getNumeTask());
        tvDescrTask.setText(task.getDescriere());
        tvDenMaterie.setText(task.getDenMaterie());
        tvDeadline.setText(new SimpleDateFormat("dd.MM.yyyy").format(task.getDataDeadline()));


        return view;
    }

}
