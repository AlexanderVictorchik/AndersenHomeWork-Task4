package ru.taksebe.telegram.mentalCalculation.telegram.commands.operations.model;

public class Task {
    int id;
    int timeSpended;
    String description;
    int reportId;

    public Task() {}

    public Task(int id, int timeSpended, String description, int reportId) {
        this.id = id;
        this.timeSpended = timeSpended;
        this.description = description;
        this.reportId = reportId;
    }

    public int getId() {
        return id;
    }

    public int getTimeSpended() {
        return timeSpended;
    }

    public String getDescription() {
        return description;
    }

    public int getReportId() {
        return reportId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimeSpended(int timeSpended) {
        this.timeSpended = timeSpended;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", timeSpended=" + timeSpended +
                ", description='" + description + '\'' +
                ", reportId=" + reportId +
                '}';
    }
}
