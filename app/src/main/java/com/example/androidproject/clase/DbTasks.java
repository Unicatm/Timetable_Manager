package com.example.androidproject.clase;

import java.util.List;

public class DbTasks {
    private List<Task> tasks;

    public DbTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
