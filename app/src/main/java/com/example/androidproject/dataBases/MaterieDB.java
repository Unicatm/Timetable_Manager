package com.example.androidproject.dataBases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.androidproject.clase.Materie;

@Database(entities = {Materie.class}, version = 1, exportSchema = false)
public abstract class MaterieDB extends RoomDatabase {
    private static final String dbName = "materii.db";
    private static MaterieDB instance;

    public static MaterieDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, MaterieDB.class, dbName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract MaterieDAO getMaterieDAO();
}
