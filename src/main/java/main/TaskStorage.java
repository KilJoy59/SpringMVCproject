package main;

import responceObjects.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskStorage {

    private static int currentId = 1;
    private static HashMap<Integer, Task> tasks = new HashMap<>();

    public static int addTask(Task task) {
        int id = currentId++;
        task.setId(id);
        tasks.put(id,task);
        return id;
    }

    public static List<Task> getAllTasks() {
        List<Task> list = new ArrayList<>();
        list.addAll(tasks.values());
        return list;
    }

    public static Task getTask(int taskId) {
        if (tasks.containsKey(taskId)) {
           return tasks.get(taskId);
        }
        return null;
    }

}
