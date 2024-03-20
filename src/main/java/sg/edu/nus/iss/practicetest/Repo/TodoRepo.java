package sg.edu.nus.iss.practicetest.Repo;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.practicetest.Model.Todo;
import sg.edu.nus.iss.practicetest.Utils.Util;

@Repository
public class TodoRepo {
    
    @Autowired
    @Qualifier(Util.REDIS_ONE)
    RedisTemplate <String, String> template;
    
    
//Create
    public void createTodo(Todo todo) {
        HashOperations<String, String, String> hashOps = template.opsForHash();
        hashOps.put(Util.KEY_TODO, todo.getId(),todo.toJSOString().toString());        
    }
//Read
    public Boolean isTodoByIdExist (String id){
        HashOperations <String, String, Todo> hashOps = template.opsForHash();
        return hashOps.hasKey(Util.KEY_TODO, id);
    }

    public Todo getTodoById (String id){
        HashOperations<String, String, String> hashOps = template.opsForHash();
        String todoString = hashOps.get(Util.KEY_TODO, id);        
        Todo todo = parseTodoFromHardcodedMethod(todoString);
            
        return todo; 
    }
//getTodoList by objectMapper (to be modified)
    // public List<Todo> getTodoList() throws ParseException {
    //     ListOperations<String, String> listOps = template.opsForList();
    //     List<Todo> todos = new LinkedList<>();
    //     List<String> todosInString = listOps.range(Util.KEY_TODO, 0, -1);        

        
    //     for (String todoInString : todosInString) {
    //         try {
    //             Todo todo = parseTodoFromJson(todoInString);
    //             todos.add(todo);
    //         } catch (IOException e) {
    //             // Handle parsing error
    //             e.printStackTrace();
    //         }
    //     }
    
    //     return todos;
    // }

    public List<Todo> getTodoList(){
        HashOperations<String, String, String> hashOps = template.opsForHash();
        List<Todo> todos = new LinkedList<>();
        Map <String, String> todosInMap= hashOps.entries(Util.KEY_TODO);        
        
        for (String todoInString : todosInMap.values()) {
            Todo todo = parseTodoFromHardcodedMethod(todoInString);
            todos.add(todo);
        }
    
        return todos;
    }      
  
// Update
    public void updateTodo(Todo todo){
        HashOperations<String, String, String> hashOps = template.opsForHash();        
        hashOps.put(Util.KEY_TODO, todo.getId(), todo.toJSOString().toString());
    }

//Delete
    public void deleteTodo(String id){
        HashOperations<String, String, String> hashOps = template.opsForHash(); 
        hashOps.delete(Util.KEY_TODO, id);
    }

    public Todo parseTodoFromJson(String todoInString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(todoInString, Todo.class);
    }

    public Todo parseTodoFromHardcodedMethod(String todoInString) {
        JsonObject jsonObject = Json.createReader(new StringReader(todoInString)).readObject();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);

        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        Date dueDate = new Date(Long.parseLong(jsonObject.get("dueDate").toString())); //data save as long, so cannot use getString()
        String priorityLevel = jsonObject.getString("priority");
        String status = jsonObject.getString("status");
        Date createdAt = new Date(jsonObject.getJsonNumber("createAt").longValue()); //another way to convert long to Date
        // Date updatedAt = new Date(jsonObject.getJsonNumber("updatedAt").longValue());
        Date updatedAt = null;
        try {
            updatedAt = formatter.parse(jsonObject.getString("updatedAt"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    
        return new Todo(id, name, description, dueDate, priorityLevel, status, createdAt, updatedAt);
    }

}