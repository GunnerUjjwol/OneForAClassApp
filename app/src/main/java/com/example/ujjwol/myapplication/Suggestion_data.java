package com.example.ujjwol.myapplication;

/**
 * Created by ujjwol on 1/17/2018.
 */

public class Suggestion_data {

    private int id;
    private String suggester;
    private String title;
    private String content;

    public Suggestion_data(int id,String suggester, String title, String content) {

        this.id = id;
        this.suggester=suggester;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSuggester() {
        return suggester;
    }

    public void setSuggester(String suggester) {
        this.suggester = suggester;
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
