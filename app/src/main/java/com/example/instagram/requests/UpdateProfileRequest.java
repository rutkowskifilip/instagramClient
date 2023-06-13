package com.example.instagram.requests;

public class UpdateProfileRequest {
    private String email;
    private String name;
    private String lastName;
    private String password;
    private String profilePic;

    public UpdateProfileRequest(String email, String name, String lastName, String password, String profilePic) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.profilePic = profilePic;
    }
}
