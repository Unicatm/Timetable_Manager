package com.example.androidproject.dataBases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.androidproject.clase.Task;

import java.util.Date;

@TypeConverters({Converters.class})
//@Database(entities = {Task.class},version = 2,exportSchema = false)
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
