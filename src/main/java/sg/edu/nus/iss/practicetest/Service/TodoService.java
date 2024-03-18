package sg.edu.nus.iss.practicetest.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.practicetest.Model.Todo;
import sg.edu.nus.iss.practicetest.Repo.TodoRepo;

@Service
public class TodoService {
    
    @Autowired
    TodoRepo todoRepo;

//Create
    public Todo createTodoWithInfo (Todo todo){        
        todoRepo.createTodo(todo);
        return todo;
    }

    public String generateUniqueId() {
        String id;
        do {
            id = UUID.randomUUID().toString();
        } while (todoRepo.getTodoById(id) != null);
        return id;
    }

    public Todo createTodoWithoutDate (Todo todo){
        // String id = generateUniqueId();
        
        // todo.setId(id);
        todo.setCreateAt(new Date());
        todo.setUpdatedAt(new Date());
        
        todoRepo.createTodo(todo);
        todoRepo.createTodo(todo);
        return todo;
    }
//Read
    public Boolean isTodoByIdExist (String id){
        return todoRepo.isTodoByIdExist(id);
    }

    public Todo getTodoById (String id){
        Todo todo = todoRepo.getTodoById(id);        
        return todo;
    }

    public List<Todo> getTodoList(){
        // Map<String, TodoRedis> todoMap = ;
        // List <TodoRedis> todoRedisList = new ArrayList<>(todoMap.values());
        
        // List<Todo> todoList = new ArrayList<>();
        // for (TodoRedis todoRedis : todoRedisList) {
        //     todoList.add(setTodo(todoRedis));
        // }
    
        return todoRepo.getTodoList().values().stream()                        
                        .collect(Collectors.toList());
    }
//Update
    public void updateTodo(Todo todo){
        
        todo.setUpdatedAt(new Date());
        todoRepo.updateTodo(todo);
        todoRepo.updateTodo(todo);
    }
//Delete
    public void deleteTodoById(String id){
        todoRepo.deleteTodo(id);
    }

//Filter
    public List<Todo> getTodoListByStatus(String status) {
        List<Todo> filteredList = getTodoList();
        if (!"all".equalsIgnoreCase(status)) {
            filteredList = filteredList.stream()
                    .filter(todo -> status.equalsIgnoreCase(todo.getStatus()))
                    .collect(Collectors.toList());
        }
        return filteredList;
    }

    public List<Todo> getTodoListByPriority(String status) {
        List<Todo> filteredList = getTodoList();
        if (!"all".equalsIgnoreCase(status)) {
            filteredList = filteredList.stream()
                    .filter(todo -> status.equalsIgnoreCase(todo.getPriority()))
                    .collect(Collectors.toList());
        }
        return filteredList;
    }

    public List<Todo> sortTodoListByPriority(String order) {
        List<Todo> sortedList = getTodoList();
        sortedList = sortedList.stream()                
                .sorted(Comparator.comparingInt(this::getPriorityValue))
                .collect(Collectors.toList());
        if("asc".equals(order)){
            return sortedList;
        }else{
            return sortedList.reversed();
        }
    }

    private int getPriorityValue(Todo todo) {
        switch (todo.getPriority()) {
            case "low":
                return 1;
            case "medium":
                return 2;
            case "high":
                return 3;
            default:
                return Integer.MAX_VALUE; // Handle unknown priorities
        }
    
    }
}
