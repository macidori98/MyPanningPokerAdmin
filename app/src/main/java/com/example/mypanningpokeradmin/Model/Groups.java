package com.example.mypanningpokeradmin.Model;

public class Groups {
    private String id;
    private String key;
    private String name;
    private boolean active;
    private int active_time;

    public Groups(String id, String name, boolean active, int active_time, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.active = active;
        this.active_time = active_time;
    }

    public String getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public int getActive_time() {
        return active_time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setActive_time(int active_time) {
        this.active_time = active_time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
