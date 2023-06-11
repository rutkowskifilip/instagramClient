package com.example.instagram.requests;

public class UpdateProfileRequest {
    private String email;
    private String name;
    private String lastName;
    private String password;

    public UpdateProfileRequest(String email, String name, String lastName, String password) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
    }
}
