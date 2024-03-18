package sg.edu.nus.iss.practicetest.Repo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.practicetest.Model.Todo;
import sg.edu.nus.iss.practicetest.Utils.Util;

@Repository
public class TodoRepo {
    
    @Autowired
    @Qualifier(Util.REDIS_TWO)
    RedisTemplate <String, Todo> template;
    
//Create
    public Boolean createTodo(Todo todo){
        HashOperations <String, String, Todo> hashOps = template.opsForHash();
        //System.out.println(todoRedis.getDueDateEpoch());

        return hashOps.putIfAbsent(Util.KEY_TODO, todo.getId(), todo);
    }
//Read
    public Boolean isTodoByIdExist (String id){
        HashOperations <String, String, Todo> hashOps = template.opsForHash();
        return hashOps.hasKey(Util.KEY_TODO, id);
    }

    public Todo getTodoById (String id){
        HashOperations <String, String, Todo> hashOps = template.opsForHash();
        return hashOps.get(Util.KEY_TODO, id);
    }

    public Map<String, Todo> getTodoList(){
        HashOperations <String, String, Todo> hashOps = template.opsForHash();
        
        return hashOps.entries(Util.KEY_TODO);
    }
//Update
    public void updateTodo(Todo todo){
        HashOperations <String, String, Todo> hashOps = template.opsForHash();
        hashOps.put(Util.KEY_TODO, todo.getId(), todo);
    }

//Delete
    public void deleteTodo(String id){
        HashOperations <String, String, Todo> hashOps = template.opsForHash();
        hashOps.delete(Util.KEY_TODO, id);
         
    }
}
