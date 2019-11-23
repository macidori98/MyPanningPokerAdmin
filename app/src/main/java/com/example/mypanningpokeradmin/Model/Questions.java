package com.example.mypanningpokeradmin.Model;

public class Questions {
    private String id;//key
    private String group_id;
    private String question;
    private String date_from;
    private String date_until;
    private boolean active;

    public Questions(String id, String group_id, String question, String date_from, String date_until, boolean active) {
        this.id = id;
        this.group_id = group_id;
        this.question = question;
        this.date_from = date_from;
        this.date_until = date_until;
        this.active = active;
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

    public String getDate_from() {
        return date_from;
    }

    public String getDate_until() {
        return date_until;
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

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public void setDate_until(String date_until) {
        this.date_until = date_until;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
