package main.controller;
import main.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Task;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks/")
    public List<Task> list() {
     Iterable<Task> iterableTasks = taskService.getAllTasks();
     List<Task> tasks = new ArrayList<>();
     for (Task task : iterableTasks) {
         tasks.add(task);
     }
        return tasks;
    }

    @PostMapping("/tasks/")
    public int add (@Valid @RequestBody Task task) {
        return taskService.addTask(task);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity getTask(@PathVariable int id) {
        Task task = taskService.getTask(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> edit(@Valid @RequestBody Task task, @PathVariable int id) {
        Task newTask = taskService.editTask(id,task);
        if (newTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(newTask,HttpStatus.OK);
    }
}
