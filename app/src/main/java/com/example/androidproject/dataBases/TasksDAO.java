package com.example.androidproject.dataBases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androidproject.clase.Task;

import java.util.List;

@Dao
public interface TasksDAO {
    @Insert
    void insertTask(Task task);

    @Query("SELECT * FROM tasks")
    List<Task> getTasks();

    @Query("SELECT * FROM tasks WHERE id=:idCautat")
    Task getTaskById(Long idCautat);

    @Query("SELECT COUNT(*) FROM tasks WHERE denMaterie = :materie")
    int getTaskCountForMaterie(String materie);
}
