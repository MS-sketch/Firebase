package com.ms.firebase;

public class todoActivityDataStruct {

    String title, dueDate, dueTime, workType, description, dateCreated, timeCreated;

    public todoActivityDataStruct(){

    }

    public todoActivityDataStruct(String title, String dueDate, String dueTime, String workType,
                                  String description, String dateCreated, String timeCreated) {
        this.title = title;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.workType = workType;
        this.description = description;
        this.dateCreated = dateCreated;
        this.timeCreated = timeCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

}
