package main;

import main.model.Task;

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
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.addAll(tasks.values());
        return taskList;
    }

    public static Task getTask(int taskId) {
        if (tasks.containsKey(taskId)) {
            return tasks.get(taskId);
        }
        return null;
    }

    public static Task editTask(int id, Task task) {
        if (tasks.containsKey(id)) {
            tasks.replace(id,task);
        }
        return null;
    }

    public static void deleteTask(int taskId) {
        tasks.remove(taskId);
    }

}
