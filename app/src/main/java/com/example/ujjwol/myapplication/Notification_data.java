package com.example.ujjwol.myapplication;

/**
 * Created by ujjwol on 1/17/2018.
 */

public class Notification_data {

    private int id;
    private String title;
    private String content;

    public Notification_data(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
