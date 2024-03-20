package sg.edu.nus.iss.practicetest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.validation.ClockProvider;
import sg.edu.nus.iss.practicetest.Model.Todo;
import sg.edu.nus.iss.practicetest.Service.FileService;
import sg.edu.nus.iss.practicetest.Service.TodoService;

@SpringBootApplication
public class PracticeTestApplication implements CommandLineRunner{

	@Autowired
	FileService fileService;

	@Autowired
	TodoService todoService;
	public static void main(String[] args) {
		SpringApplication.run(PracticeTestApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		String payload = fileService.readFile();
		
		// System.out.println(payload);
		System.out.println(payload);
		try (InputStream is = new ByteArrayInputStream(payload.getBytes())) {
			JsonReader reader = Json.createReader(is);
			JsonArray jArray = reader.readArray();
			
			for(JsonValue jValue: jArray){
				JsonObject jsonObject = (JsonObject) jValue;
				
				Todo todo = parseTodoFromHardcodedMethod(jsonObject);
				// System.out.println(todo.toJSOString());
				
				todoService.createTodoWithInfo(todo);
			}
		} 
		
		System.out.println("Data saved to Redis successfully.");
	}
	//cannot use repo function as the naming is different
	//caanot use object mapper as the format of date is not standard format
	public Todo parseTodoFromHardcodedMethod(JsonObject jsonObject) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, MM/dd/yyyy", Locale.ENGLISH);
    
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
	    // System.out.println(jsonObject.get("due_date"));
        Date dueDate = formatter.parse(jsonObject.getString("due_date")); //not sure why cannot use get().toString maybe because when get(), it become Json value, the tostring convert it to another format
        String priorityLevel = jsonObject.getString("priority_level");
        String status = jsonObject.getString("status");
        Date createdAt = formatter.parse(jsonObject.getString("created_at"));
        Date updatedAt = formatter.parse(jsonObject.getString("updated_at"));
    
        return new Todo(id, name, description, dueDate, priorityLevel, status, createdAt, updatedAt);
    }
	
}
