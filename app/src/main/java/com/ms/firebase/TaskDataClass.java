package com.ms.firebase;

public class TaskDataClass {

    private String taskTitle;
    private String taskCreation;
    private String taskDue;
    private String status;
    private int taskTypeImage;

    public TaskDataClass(String taskTitle, String taskCreation, String taskDue, int taskTypeImage, String status) {
        this.taskTitle = taskTitle;
        this.taskCreation = taskCreation;
        this.taskDue = taskDue;
        this.taskTypeImage = taskTypeImage;
        this.status = status;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskCreation() {
        return taskCreation;
    }

    public String getTaskDue() {
        return taskDue;
    }

    public int getTaskTypeImage() {
        return taskTypeImage;
    }

    public String getStatus() {
        return status;
    }

}

