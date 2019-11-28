package main;
import main.model.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import main.model.Task;
import sun.net.httpserver.HttpsServerImpl;

import javax.net.ssl.HttpsURLConnection;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private ToDoRepository toDoRepository;

    //loadTasks
    @GetMapping("/tasks/")
    public List<Task> list() {
        Iterable<Task> taskIterable =  toDoRepository.findAll();
        ArrayList<Task> tasklist = new ArrayList<>();
        for (Task task : taskIterable) {
            tasklist.add(task);
        }
        return tasklist;
    }


    //addTasks
    @PostMapping(value = "/tasks/", consumes = "application/json")
    @ResponseBody
    public int add (@RequestBody Task task) {
       Task newTask = toDoRepository.save(task);
        return newTask.getId();
    }

    //getTaskFromId
    @GetMapping("/tasks/{id}")
    public ResponseEntity getTask(@PathVariable int id) {
        Optional<Task> optionalTask = toDoRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalTask.get(), HttpStatus.OK);
    }

    //deleteTaskFromId
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity deleteTask(@PathVariable int id) {
        toDoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    //updateTask

}
