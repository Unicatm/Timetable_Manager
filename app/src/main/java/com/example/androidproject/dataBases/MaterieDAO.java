package com.example.androidproject.dataBases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidproject.clase.Materie;

import java.util.List;

@Dao
public interface MaterieDAO {
    @Insert
    void insertMaterie(Materie materie);

    @Update
    void updateMaterie(Materie materie);

    @Delete
    void deleteMaterie(Materie materie);

    @Query("DELETE FROM materii")
    void deleteAllMaterii();

    @Query("SELECT *FROM materii")
    List<Materie> getMaterii();

    @Query("SELECT * FROM materii WHERE orar_id=:orarId")
    List<Materie> getMateriiOrar(Long orarId);

    @Query("SELECT * FROM materii where id=:idCautat")
    Materie getMaterieById(Long idCautat);

    @Query("SELECT * FROM materii WHERE nume_materie=:nume_materie")
    Materie getMaterieByName(String nume_materie);
}
