package com.example.androidproject.dataBases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.androidproject.clase.Materie;
import com.example.androidproject.clase.Orar;
import com.example.androidproject.clase.Task;

@TypeConverters({Converters.class})
@Database(entities = {Orar.class ,Materie.class},version = 5,exportSchema = false)
public abstract class AplicatieDB extends RoomDatabase {
    private static final String dbName = "aplicatie.db";
    private static AplicatieDB instance;

    public static synchronized AplicatieDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, AplicatieDB.class,dbName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract AplicatieDAO getAplicatieDAO();
    public abstract MaterieDAO getMaterieDAO();
    public abstract TasksDAO getTasksDAO();
}
