package sg.edu.nus.iss.practicetest.Model;

import java.io.Serializable;
import java.util.Date;

public class TodoRedis implements Serializable{

    private String id;    
    private String name;
    private String description;
    private Long dueDateEpoch;    
    private String priority;
    private String status;
    private Long createAtEpoch;
    private Long updateAtEpoch;
    
    public TodoRedis() {
    }    

    public TodoRedis(String id, String name, String description, Long dueDateEpoch, String priority, String status,
            Long createAtEpoch, Long updatedAtEpoch) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDateEpoch = dueDateEpoch;
        this.priority = priority;
        this.status = status;
        this.createAtEpoch = createAtEpoch;
        this.updateAtEpoch = updatedAtEpoch;
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

    public Long getDueDateEpoch() {
        return dueDateEpoch;
    }

    public void setDueDateEpoch(Long dueDateEpoch) {
        this.dueDateEpoch = dueDateEpoch;
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

    public Long getCreateAtEpoch() {
        return createAtEpoch;
    }

    public void setCreateAtEpoch(Long createAtEpoch) {
        this.createAtEpoch = createAtEpoch;
    }

    public Long getUpdateAtEpoch() {
        return updateAtEpoch;
    }

    public void setUpdateAtEpoch(Long updatedAtEpoch) {
        this.updateAtEpoch = updatedAtEpoch;
    }

    public Date convertToDueDate(){
        return new Date(getDueDateEpoch());
    }

    public Date convertToCreateAt(){
        return new Date(getCreateAtEpoch());
    }

    public Date convertToUpdateAt(){
        return new Date(getUpdateAtEpoch());
    }
}
