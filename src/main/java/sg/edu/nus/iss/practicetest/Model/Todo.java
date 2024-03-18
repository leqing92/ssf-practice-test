package sg.edu.nus.iss.practicetest.Model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @FutureOrPresent
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    // @JsonFormat (pattern = "EEE, MM/dd/yyyy", timezone = "Asia/Singapore")
    private Date dueDate;
    
    private String priority;
    private String status;

    // @DateTimeFormat (pattern = "EEE, MM/dd/yyyy")
    // @JsonFormat (pattern = "EEE, MM/dd/yyyy", timezone = "Asia/Singapore")
    private Date createAt;

    
    // @DateTimeFormat (pattern = "EEE, MM/dd/yyyy")
    // @JsonFormat (pattern = "EEE, MM/dd/yyyy", timezone = "Asia/Singapore")
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
}
