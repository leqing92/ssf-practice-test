package sg.edu.nus.iss.practicetest.Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.annotation.Nullable;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public class Todo implements Serializable{   
    
    // @NotNull
    @Size (max = 50)
    private String id;

    @NotNull
    @NotBlank
    @Size (min = 10, max = 50)
    private String name;

    @Size (max = 255)
    // @Nullable
    private String description;
    
    @NotNull
    @PresentOrFuture
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    // @JsonFormat (pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Singapore")
    private Date dueDate;
    
    private String priority;
    private String status;

    // @DateTimeFormat (pattern = "EEE, MM/dd/yyyy")
    // @JsonFormat (pattern = "EEE, MM/dd/yyyy", timezone = "Asia/Singapore")
    private Date createAt;

    
    // @DateTimeFormat (pattern = "EEE, MM/dd/yyyy")
    @JsonFormat (pattern = "EEE, dd MMM yyyy HH:mm:ss zzz", timezone = "Asia/Singapore")
    private Date updatedAt;

    public Todo() {
    }    
    
    public Todo(String name, String description, Date dueDate, String priority, String status) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }

    public Todo(String id, String name, String description, Date dueDate, String priority, String status, Date createAt,
            Date updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long convertToDueDateEpoch(){
        return dueDate.getTime();
    }

    public Long convertToCreatedAtEpoch(){
        return createAt.getTime();
    }

    public Long convertToUpdateAtEpoch(){
        return updatedAt.getTime();
    }
    
    public JsonObject toJSOString() {
        JsonObjectBuilder jObjectbBuilder = Json.createObjectBuilder()
                                .add("id", id)
                                .add("name", name)
                                .add("description", description)
                                .add("dueDate", dueDate.getTime()) //save as long
                                .add("priority", priority)
                                .add("status", status)
                                .add("createAt", createAt.getTime()) //save as long
                                .add("updatedAt", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(updatedAt));
                                // .add("updatedAt", updatedAt.getTime());                                
                                //standard forms are ("yyyy-MM-dd'T'HH:mm:ss.SSSX", "yyyy-MM-dd'T'HH:mm:ss.SSS", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd")
                                //but date.toString() is in "EEE MMM dd HH:mm:ss zzz yyyy" so not compatible
                                //so cannot directly use date.toString() to save into redis else cannot be read later
        JsonObject jObject = jObjectbBuilder.build(); 
        return jObject;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((createAt == null) ? 0 : createAt.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (createAt == null) {
			if (other.createAt != null)
				return false;
		} else if (!createAt.equals(other.createAt))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

    
    
}
