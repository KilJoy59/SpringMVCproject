package main.service;

import main.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();
    Task getTask(int id);
    int addTask(Task task);
    Task editTask(int id, Task task);
    Task deleteTask(int id);
}
