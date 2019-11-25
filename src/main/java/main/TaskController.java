package main;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import responceObjects.Task;
import java.util.List;

@RestController
public class TaskController {

    @GetMapping(value = "/tasks/")
    public List<Task> get() {
        return TaskStorage.getAllTasks();
    }

    @PostMapping(value = "/tasks/")
    public int addTask(Task task) {
       return TaskStorage.addTask(task);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity getTask(@PathVariable int id) {
        Task task = TaskStorage.getTask(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }
}
