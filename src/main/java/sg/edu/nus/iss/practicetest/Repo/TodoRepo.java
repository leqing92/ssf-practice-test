package sg.edu.nus.iss.practicetest.Repo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.practicetest.Model.TodoRedis;
import sg.edu.nus.iss.practicetest.Utils.Util;

@Repository
public class TodoRepo {
    
    @Autowired
    @Qualifier(Util.REDIS_TWO)
    RedisTemplate <String, TodoRedis> template;
    
//Create
    public Boolean createTodo(TodoRedis todoRedis){
        HashOperations <String, String, TodoRedis> hashOps = template.opsForHash();
        //System.out.println(todoRedis.getDueDateEpoch());

        return hashOps.putIfAbsent(Util.KEY_TODO, todoRedis.getId(), todoRedis);
    }
//Read
    public TodoRedis getTodoById (String id){
        HashOperations <String, String, TodoRedis> hashOps = template.opsForHash();
        return hashOps.get(Util.KEY_TODO, id);
    }

    public Map<String, TodoRedis> getTodoList(){
        HashOperations <String, String, TodoRedis> hashOps = template.opsForHash();
        
        return hashOps.entries(Util.KEY_TODO);
    }
//Update
    public void updateTodo(TodoRedis todoRedis){
        HashOperations <String, String, TodoRedis> hashOps = template.opsForHash();
        hashOps.put(Util.KEY_TODO, todoRedis.getId(), todoRedis);
    }

//Delete
    public void deleteTodo(String id){
        HashOperations <String, String, TodoRedis> hashOps = template.opsForHash();
        hashOps.delete(Util.KEY_TODO, id);
         
    }
}
