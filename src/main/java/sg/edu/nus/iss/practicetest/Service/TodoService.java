package sg.edu.nus.iss.practicetest.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.practicetest.Model.Todo;
import sg.edu.nus.iss.practicetest.Model.TodoRedis;
import sg.edu.nus.iss.practicetest.Repo.TodoRepo;

@Service
public class TodoService {
    
    @Autowired
    TodoRepo todoRedisRepo;
//Create
    public Todo createTodoWithInfo (Todo todo){        
        todoRedisRepo.createTodo(convertToTodoRedisFull(todo));
        return todo;
    }

    public String generateUniqueId() {
        String id;
        do {
            id = UUID.randomUUID().toString();
        } while (todoRedisRepo.getTodoById(id) != null);
        return id;
    }

    public Todo createTodoWithoutDate (Todo todo){
        // String id = generateUniqueId();
        
        // todo.setId(id);
        todo.setCreateAt(new Date());
        todo.setUpdatedAt(new Date());
        
        todoRedisRepo.createTodo(convertToTodoRedisFull(todo));
        return todo;
    }
//Read
    public Todo getTodoById (String id){
        TodoRedis todoRedis = todoRedisRepo.getTodoById(id);        
        return convertToTodoFull(todoRedis);
    }

    public List<Todo> getTodoList(){
        // Map<String, TodoRedis> todoMap = ;
        // List <TodoRedis> todoRedisList = new ArrayList<>(todoMap.values());
        
        // List<Todo> todoList = new ArrayList<>();
        // for (TodoRedis todoRedis : todoRedisList) {
        //     todoList.add(setTodo(todoRedis));
        // }
    
        return todoRedisRepo.getTodoList().values().stream()
                        .map(this::convertToTodoFull)
                        .collect(Collectors.toList());
    }
//Update
    public void updateTodo(Todo todo){
        
        todo.setUpdatedAt(new Date());
        todoRedisRepo.updateTodo(convertToTodoRedisFull(todo));
    }
//Delete
    public void deleteTodoById(String id){
        todoRedisRepo.deleteTodo(id);
    }

//Setter
    private Todo convertToTodoFull (TodoRedis todoRedis){
        return new Todo(todoRedis.getId(), todoRedis.getName(), todoRedis.getDescription(), 
        todoRedis.convertToDueDate(), todoRedis.getPriority(), todoRedis.getStatus(), 
        todoRedis.convertToCreateAt(), todoRedis.convertToUpdateAt());
    }

    private TodoRedis convertToTodoRedisFull (Todo todo){
        return new TodoRedis(todo.getId(), todo.getName(), todo.getDescription(), 
        todo.convertToDueDateEpoch(), todo.getPriority(), todo.getStatus(), 
        todo.convertToCreatedAtEpoch(), todo.convertToUpdateAtEpoch());
    }

    public List<Todo> getTodoListByStatus(String status) {
        List<Todo> filteredList = getTodoList();
        if (!"all".equalsIgnoreCase(status)) {
            filteredList = filteredList.stream()
                    .filter(todo -> status.equalsIgnoreCase(todo.getStatus()))
                    .collect(Collectors.toList());
        }
        return filteredList;
    }
    
}
