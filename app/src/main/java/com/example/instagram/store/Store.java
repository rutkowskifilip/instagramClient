package com.example.instagram.store;

import com.example.instagram.model.User;

public class Store {
    private static String token;
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Store.user = user;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Store.token = token;
    }
}
