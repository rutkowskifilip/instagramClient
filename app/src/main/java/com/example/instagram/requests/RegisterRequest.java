package com.example.instagram.requests;

public class RegisterRequest {

    private String username;
    private String name;
    private String lastname;
    private String email;
    private String password;

    public RegisterRequest(String username, String name, String lastname, String email, String password) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
}
