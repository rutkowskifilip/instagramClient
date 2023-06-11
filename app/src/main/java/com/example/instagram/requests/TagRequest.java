package com.example.instagram.requests;

import java.util.ArrayList;

public class TagRequest {
    private int id;
    private ArrayList<String> tags;

    public TagRequest(int id, ArrayList<String> tags) {
        this.id = id;
        this.tags = tags;
    }
}
