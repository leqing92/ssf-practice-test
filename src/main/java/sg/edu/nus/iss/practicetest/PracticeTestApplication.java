package sg.edu.nus.iss.practicetest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
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
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, MM/dd/yyyy");
		// System.out.println(payload);
		
		try (InputStream is = new ByteArrayInputStream(payload.getBytes())) {
			JsonReader reader = Json.createReader(is);
			JsonArray jArray = reader.readArray();
			
			for(JsonValue jValue: jArray){
				JsonObject jsonObject = (JsonObject) jValue;

				String id = jsonObject.getString("id");
				String name = jsonObject.getString("name");
				String description = jsonObject.getString("description");
				Date dueDate = formatter.parse(jsonObject.getString("due_date"));
				String priorityLevel = jsonObject.getString("priority_level");
				String status = jsonObject.getString("status");
				Date createdAt = formatter.parse(jsonObject.getString("created_at"));
				Date updatedAt = formatter.parse(jsonObject.getString("updated_at")); 

				Todo todo = new Todo(id, name, description, dueDate, priorityLevel, status, createdAt, updatedAt);
				
				todoService.createTodoWithInfo(todo);
			}
		} 
		
		System.out.println("Data saved to Redis successfully.");
	}

	// private Date changeStoD (String dateS) throws ParseException{
	// 	SimpleDateFormat formatter = new SimpleDateFormat("EEE, MM/dd/yyyy");		
	// 	Date date = formatter.parse(dateS);
	// 	Long epochMilliseconds = date.getTime();
	// 	return date;
	// }
	
}
