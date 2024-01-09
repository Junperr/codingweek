package com.example.codingweek.auth;

public class User {
    public String firstName, lastName, userName, email, passWord, address, city, zipCode;
    public int coins;
    private static User instance;


    private User(String firstName, String lastName, String userName, String email, String passWord, String address, String city, String zipCode, int coins) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.coins = coins;
    }

    public static synchronized User makeInstance(String firstName, String lastName, String userName, String email, String passWord, String address, String city, String zipCode, int coins) {
        instance = new User(firstName, lastName, userName, email, passWord, address, city, zipCode, coins);
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
