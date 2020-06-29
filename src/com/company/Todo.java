package com.company;

public class Todo {
    private String name;
    private String description;
    private boolean isFinished;

    public Todo(String name, String description, boolean isFinished) {
        this.name = name;
        this.description = description;
        this.isFinished = isFinished;
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

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
