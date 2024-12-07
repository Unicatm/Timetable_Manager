package com.example.androidproject.dataBases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androidproject.clase.Orar;

import java.util.List;

@Dao
public interface AplicatieDAO {
    @Insert
    void insertOrar(Orar orar);

    @Delete
    void deleteOrar(Orar orar);

    @Query("SELECT * FROM orare")
    List<Orar> getOrare();
}
