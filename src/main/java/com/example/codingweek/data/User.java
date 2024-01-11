package com.example.codingweek.data;

import com.example.codingweek.SerializedData;

public class User extends SerializedData {
    public String firstName, lastName, userName, email, password, address, city, zipCode;
    public Integer coins, avgEval;

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

    public Integer remainAfterBuy(Integer cost){
        return this.coins - cost;
    }


}
