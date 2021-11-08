package ru.taksebe.telegram.mentalCalculation.telegram.commands.operations.model;

import java.sql.Date;
import java.util.List;

/**
 * Student's table entity.
 */
public class Report {
    int id;
    Date date;
    long userId;
    List<Task> tasks;

    public Report() {}

    public Report(int id, Date date, long userId) {
        this.id = id;
        this.date = date;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public long getUserId() {
        return userId;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", date=" + date +
                ", userId='" + userId + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
