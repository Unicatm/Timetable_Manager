package com.example.androidproject.dataBases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androidproject.clase.Materie;

import java.util.List;

@Dao
public interface MaterieDAO {
    @Insert
    void insertMaterie(Materie materie);

    @Query("SELECT * FROM materii")
    List<Materie> getMaterii();

    @Query("SELECT * FROM materii where id=:idCautat")
    Materie getMaterieById(Long idCautat);
}
