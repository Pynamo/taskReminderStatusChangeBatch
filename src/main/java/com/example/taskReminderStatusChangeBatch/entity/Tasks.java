package com.example.taskReminderStatusChangeBatch.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import com.example.taskReminderStatusChangeBatch.common.Status;

import lombok.Data;

@Data
@Entity
public class Tasks {
	
	@Id
    private Long task_id;
    private Long user_id;
    private String load;
    private String name;
    private String content;
    private String status;
    private String image_url;
    private String deleted;
    private Date created_at;
    private Date updated_at;
    
    public void setStatus(Status status) {
    	this.status = status.getCode();
    }
    public Status getStatus() {
    	return Status.getValue(this.status);
    }
    
    public void statusInActive() {
    	this.setStatus(Status.NON_EXECTED);
    }

}
