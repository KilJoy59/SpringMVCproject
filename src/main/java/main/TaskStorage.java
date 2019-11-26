package main;

import main.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskStorage {


    private static ArrayList<Task> tasks = new ArrayList<>();

    public static int addTask(Task task) {
        int id = tasks.size()+1;
        task.setId(id);
        tasks.add(task);
        return id;
    }

    public static List<Task> getAllTasks() {
        return tasks;
    }

}
