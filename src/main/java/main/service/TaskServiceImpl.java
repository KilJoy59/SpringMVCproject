package main.service;

import main.model.Task;
import main.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    ToDoRepository toDoRepository;

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        toDoRepository.findAll().forEach(tasks::add);
        return tasks;
    }

    @Override
    public Task getTask(int id) {
        Optional<Task> optionalTask = toDoRepository.findById(id);
        return optionalTask.orElse(null);
    }

    @Override
    public int addTask(Task task) {
        return toDoRepository.save(task).getId();
    }

    @Override
    public Task editTask(int id, Task task) {
        Optional<Task> optionalTask = toDoRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task newTask = optionalTask.get();
            newTask.setName(task.getName());
            newTask.setId(task.getId());
            newTask.setDescription(task.getDescription());
            return toDoRepository.save(newTask);
        }
        return null;
    }

    @Override
    public Task deleteTask(int id) {
        Task task = getTask(id);
        if (task != null) {
            toDoRepository.deleteById(id);
            return task;
        }
        return null;
    }
}
