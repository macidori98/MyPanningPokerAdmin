package com.example.mypanningpokeradmin.Model;

public class Questions {
    private String id;//key
    private String group_id;
    private String question;
    private boolean active;
    private int active_time_seconds;

    public Questions(String id, String group_id, String question, boolean active, int active_time_seconds) {
        this.id = id;
        this.group_id = group_id;
        this.question = question;
        this.active = active;
        this.active_time_seconds = active_time_seconds;
    }

    public String getId() {
        return id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getActive_time_seconds() {
        return active_time_seconds;
    }

    public void setActive_time_seconds(int active_time_seconds) {
        this.active_time_seconds = active_time_seconds;
    }
}
