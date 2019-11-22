package com.example.mypanningpokeradmin.Model;

public class Answer {
    private String id;//key
    private String question_id;
    private String user_id;
    private String answer;

    public Answer(String id, String question_id, String user_id, String answer) {
        this.id = id;
        this.question_id = question_id;
        this.user_id = user_id;
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getAnswer() {
        return answer;
    }
}
