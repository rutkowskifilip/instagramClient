package com.example.instagram.model;

import java.util.ArrayList;
import java.util.Map;

public class Post {
    private String id;
    private String album;
    private String originalName;
    private String url;
    private String lastChange;
    private ArrayList<Map<String, String>> history;
    private ArrayList<Map<String, String>> tags;
    private String location;

    public String getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public String getAlbum() {
        return album;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getUrl() {
        return url;
    }

    public String getLastChange() {
        return lastChange;
    }

    public ArrayList<Map<String, String>> getHistory() {
        return history;
    }

    public ArrayList<Map<String, String>> getTags() {
        return tags;
    }
}
