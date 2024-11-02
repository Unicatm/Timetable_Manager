package com.example.androidproject.clase;

import com.example.androidproject.customAdapters.AdapterMaterie;

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
        MateriiManager.updateSubjectCounts();
    }

    // Șterge materie
    public static void stergeTask(Task task) {
        tasks.remove(task);
        MateriiManager.updateSubjectCounts();
    }

    public static int getTaskCountForSubject(String denMaterie){
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
