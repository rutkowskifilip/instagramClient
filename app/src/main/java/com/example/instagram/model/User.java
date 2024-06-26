package com.example.instagram.model;

import com.google.gson.annotations.SerializedName;

public class User {
    private int id;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private boolean confirmed;
    private String token;
    private String profilePic;

    public String getProfilePic() {
        return profilePic;
    }

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
