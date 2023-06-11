package com.example.instagram.model;

public class Tag {
    private int id;
    private String tag;
    private int popularity;

    public Tag(int id, String tag, int popularity) {
        this.id = id;
        this.tag = tag;
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public int getPopularity() {
        return popularity;
    }
}
