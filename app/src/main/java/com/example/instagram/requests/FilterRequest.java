package com.example.instagram.requests;

public class FilterRequest {
    private int id;
    private String lastChange;

    public FilterRequest(int id, String lastChange) {
        this.id = id;
        this.lastChange = lastChange;
    }
}
