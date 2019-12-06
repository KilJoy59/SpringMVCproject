package main;
import main.model.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import main.model.Task;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping("/tasks/")
    public List<Task> list() {
     Iterable<Task> iterable = toDoRepository.findAll();
     List<Task> tasks = new ArrayList<>();
     for (Task task : iterable) {
         tasks.add(task);
     }
        return tasks;
    }

    @PostMapping("/tasks/")
    public int add (@Valid @RequestBody Task task) {
        return toDoRepository.save(task).getId();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity getTask(@PathVariable int id) {
        Optional<Task> optionalTask = toDoRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalTask.get(), HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity deleteTask(@PathVariable int id) {
        toDoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> edit(@Valid @RequestBody Task task, @PathVariable int id) {
        toDoRepository.deleteById(id);
         Task newTask = toDoRepository.save(task);
        if (newTask == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(newTask);
    }
}
