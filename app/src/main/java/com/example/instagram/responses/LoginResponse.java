package com.example.instagram.responses;

import com.example.instagram.model.User;

public class LoginResponse {
    private String token;
    private User user;

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
