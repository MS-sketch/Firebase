package com.ms.firebase;
import androidx.room.Entity;

@Entity(tableName = "todo_list")
public class usersDBEntity {
    private int taskID;
    private String taskTitle;
    private String taskCreationDate;
    private String taskCreationTime;
    private String taskDueDate;
    private String taskDueTime;
    private String status;
    private int taskType;
}
