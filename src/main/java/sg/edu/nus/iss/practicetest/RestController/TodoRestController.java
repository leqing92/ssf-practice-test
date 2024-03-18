package sg.edu.nus.iss.practicetest.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    public ResponseEntity<List<Todo>> getAllTodos() throws ParseException, IOException{
        return ResponseEntity.ok(todoService.getTodoList());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Object> getTodoById(@PathVariable ("id")String id) throws IOException{
        if (todoService.isTodoByIdExist(id)) {
            return ResponseEntity.ok(todoService.getTodoById(id));
        }
        else{
            return ResponseEntity.badRequest().body("ID: " + id + " not exist");
        }
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<?> putMethodName(@PathVariable ("id") String id, @RequestBody String entity) {        
        if (todoService.isTodoByIdExist(id)) {
            try {
                // Deserialize JSON string to Todo object
                ObjectMapper objectMapper = new ObjectMapper();
                Todo todo = objectMapper.readValue(entity, Todo.class);
                todoService.updateTodo(todo);
                return ResponseEntity.ok().body(id + " is updated");
            }catch(IOException e){
                e.printStackTrace();
                return ResponseEntity.badRequest().body("Invalid JSON format");
            }
            //standard forms are ("yyyy-MM-dd'T'HH:mm:ss.SSSX", "yyyy-MM-dd'T'HH:mm:ss.SSS", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd")
            //but date.toString() is in "EEE MMM dd HH:mm:ss zzz yyyy" so not compatible
            //so cannot directly use date.toString() to save into redis else cannot be read later
        }
        else{
            return ResponseEntity.badRequest().body("ID: " + id + " not exist");
        }
    }
    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable ("id") String id){        
        if (todoService.isTodoByIdExist(id)) {
            todoService.deleteTodoById(id);
            return ResponseEntity.ok().body("ID: " + id + "is deleted");
        } 
        else{
            return ResponseEntity.badRequest().body("ID: " + id + " not exist");
        }
    }
}
