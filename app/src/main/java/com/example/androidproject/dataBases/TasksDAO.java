package com.example.androidproject.dataBases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidproject.clase.Task;

import java.util.List;

@Dao
public interface TasksDAO {
    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM tasks")
    List<Task> getTasks();

    @Query("DELETE FROM tasks")
    void deleteAllTasks();

    @Query("SELECT * FROM tasks WHERE id=:idCautat")
    Task getTaskById(Long idCautat);

    @Query("SELECT COUNT(*) FROM tasks WHERE denMaterie = :materie")
    int getTaskCountForMaterie(String materie);
}
