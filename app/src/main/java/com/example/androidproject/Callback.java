package com.example.androidproject;

import com.example.androidproject.clase.Task;

import java.util.List;

public interface Callback {
    void onTaskListLoaded(List<Task> taskList);
}
