package sg.edu.nus.iss.practicetest.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.practicetest.Model.Todo;
import sg.edu.nus.iss.practicetest.Service.TodoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
    
    @GetMapping("list")
    public ResponseEntity<List<Todo>> getAllTodos(){
        return ResponseEntity.ok(todoService.getTodoList());
    }
    
    
}
