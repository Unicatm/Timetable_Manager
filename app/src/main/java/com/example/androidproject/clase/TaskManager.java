package com.example.androidproject.clase;

import java.util.List;

public class TaskManager {
    private static List<Task> tasks;

    public TaskManager() {
    }

    public TaskManager(List<Task> tasks) {
        this.tasks = tasks;
    }

    public static void adaugaTask(Task task) {
        tasks.add(task);
    }

    // Șterge materie
    public static void stergeTask(Task task) {
        tasks.remove(task);
    }

    public static int noTasksMaterie(String denMaterie){
        int count=0;
        for (Task task : tasks) {
            if (task.getNumeTask().compareTo(denMaterie) == 0) {
                count++;
            }
        }
        return count;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
