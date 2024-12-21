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

    @Query("DELETE FROM tasks WHERE id = :taskId")
    void deleteTaskById(long taskId);

    @Query("SELECT * FROM tasks")
    List<Task> getTasks();

    @Query("SELECT t.*, m.* FROM tasks t " +
            "JOIN materii m ON t.materie_id = m.id " +
            "WHERE m.orar_id = :orarId")
    List<Task> getTasksForOrar(Long orarId);

    @Query("SELECT * FROM tasks WHERE materie_id= :orarId")
    List<Task> getTasksRightOrar(Long orarId);

    @Query("DELETE FROM tasks")
    void deleteAllTasks();

    @Query("SELECT * FROM tasks WHERE id=:idCautat")
    Task getTaskById(Long idCautat);

    @Query("SELECT COUNT(*) FROM tasks WHERE denMaterie = :materie")
    int getTaskCountForMaterie(String materie);
}
