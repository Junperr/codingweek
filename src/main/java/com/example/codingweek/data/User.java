package com.example.codingweek.data;

import com.example.codingweek.SerializedData;

public class User extends SerializedData {
    public String firstName, lastName, userName, email, password, address, city, zipCode;
    public Integer coins, avgEval;
    private static User instance;


    public User(String firstName, String lastName, String userName, String email, String password, String address, String city, String zipCode, int coins) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.coins = coins;
        this.avgEval = -1;
    }

    public static synchronized User makeInstance(String firstName, String lastName, String userName, String email, String password, String address, String city, String zipCode, int coins) {
        instance = new User(firstName, lastName, userName, email, password, address, city, zipCode, coins);
        return instance;
    }

    public static synchronized User wipeInstance() {
        instance = null;
        return null;
    }

    public static synchronized User getInstance() {
        return instance;
    }

    private void changeUsername(String newName) {
        this.userName = newName;
    }

    private void changeEmail(String newEmail) {
        this.email = newEmail;
    }

}
