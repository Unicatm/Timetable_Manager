package com.example.androidproject.dataBases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.androidproject.clase.Task;

@Database(entities = {Task.class},version = 1,exportSchema = false)
public abstract class TasksDB extends RoomDatabase {
    private static final String dbName = "tasks.db";
    private static TasksDB instance;

    public static TasksDB getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context,TasksDB.class,dbName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }


        return instance;
    }

    public abstract TasksDAO getTaskDAO();
}
