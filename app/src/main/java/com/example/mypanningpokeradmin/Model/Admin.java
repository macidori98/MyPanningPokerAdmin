package com.example.mypanningpokeradmin.Model;

public class Admin {
    private String id;
    private String name;

    public Admin(String id, String name) {
        this.id = id;//key
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId(){
        return id;
    }
}
