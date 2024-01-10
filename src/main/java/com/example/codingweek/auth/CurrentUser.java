package com.example.codingweek.auth;

import com.example.codingweek.data.User;

public class CurrentUser {
    private static User currentUser = null;

    private CurrentUser() {
    }

    public static void logUser(User user) {
        currentUser = user;
    }

    public static void logoutUser() {
        currentUser = null;
    }

    public static User getUser() {
        return currentUser;
    }
}
