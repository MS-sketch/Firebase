package com.ms.firebase;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_list")
public class usersDBEntity {

    @PrimaryKey
    private int taskID;

    @ColumnInfo(name = "taskTitle")
    private String taskTitle;

    @ColumnInfo(name = "taskCreationDate")
    private String taskCreationDate;

    @ColumnInfo(name = "taskCreationTime")
    private String taskCreationTime;

    @ColumnInfo(name = "taskDueDate")
    private String taskDueDate;

    @ColumnInfo(name = "taskDueTime")
    private String taskDueTime;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "taskType")
    private int taskType;
}
