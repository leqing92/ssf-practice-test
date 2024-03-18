package sg.edu.nus.iss.practicetest.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.practicetest.Model.Todo;
import sg.edu.nus.iss.practicetest.Service.TodoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/todos")
public class TodoRestController {
    
    @Autowired
    TodoService todoService;   
    
    @PostMapping("")
    public ResponseEntity<Object> postMethodName(@RequestBody Todo todo) {
        todoService.createTodoWithInfo(todo);
        
        return ResponseEntity.ok().body("todo created");
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<Todo>> getAllTodos(){
        return ResponseEntity.ok(todoService.getTodoList());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Object> getTodoById(@PathVariable("id") String id){
        if (todoService.isTodoByIdExist(id)) {
            return ResponseEntity.ok(todoService.getTodoById(id));
        }
        else{
            return ResponseEntity.badRequest().body("ID: " + id + " not exist");
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> updateTodo(@PathVariable("id") String id, @RequestBody Todo todo) {
        if (todoService.isTodoByIdExist(id)) {
            todoService.updateTodo(todo);
            return ResponseEntity.ok().body(id + " is updated");
        }
        else{
            return ResponseEntity.badRequest().body("ID: " + id + " not exist");
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteTodo(@PathVariable("id") String id){
        if (todoService.isTodoByIdExist(id)) {
            todoService.deleteTodoById(id);
            return ResponseEntity.ok().body("ID: " + id + " is deleted");
        }
        else{
            return ResponseEntity.badRequest().body("ID: " + id + " not exist");
        }
    }
    
}
