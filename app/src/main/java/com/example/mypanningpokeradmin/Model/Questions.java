package com.example.mypanningpokeradmin.Model;

public class Questions {
    private String id;
    private String group_id;
    private String question;
    private String date_from;
    private String date_until;

    public Questions(String id, String group_id, String question, String date_from, String date_until) {
        this.id = id;
        this.group_id = group_id;
        this.question = question;
        this.date_from = date_from;
        this.date_until = date_until;
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
}
