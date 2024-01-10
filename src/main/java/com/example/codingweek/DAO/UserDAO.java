package com.example.codingweek.DAO;

import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.User;
import com.example.codingweek.database.DataBase;
import com.example.codingweek.error.ErrorManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDAO {

    public User newUser(String firstName, String lastName, String userName, String email, String password, String address, String city, String zipCode) throws Exception {
        User user = new User(firstName, lastName, userName, email, password, address, city, zipCode, 100);
        addUser(user);
        return user;
    }

    public void addUser(User user) throws Exception {
        // Check if an attribute of user is null
        ErrorManager errManager = new ErrorManager();

        try {
            errManager.handleRegister(user, getUserByUsername(user.userName));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }

        DataBase db = DataBase.getInstance();
        db.exec("INSERT INTO Users (firstName, lastName, userName, email, address , zipCode , city, password, coins) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                user.firstName,
                user.lastName,
                user.userName,
                user.email,
                user.address,
                user.zipCode,
                user.city,
                user.password,
                user.coins);
    }

    public void logUser(String username, String pwd) throws Exception {

        User user = getUserByPassword(username, pwd);

        ErrorManager errManager = new ErrorManager();

        try {
            errManager.handleLogin(user);
            CurrentUser.logUser(user);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public User getUserByPassword(String username, String pwd){
        DataBase db = DataBase.getInstance();

        Map<String,Object> userMap = db.fetchOneMap("SELECT * FROM Users WHERE username = ? AND password = ?", username, pwd);

        if (userMap.get("userName") == null) {
            return null;
        }

        return new User(userMap.get("firstName").toString(),
                userMap.get("lastName").toString(),
                username,
                userMap.get("email").toString(),
                pwd,
                userMap.get("address").toString(),
                userMap.get("city").toString(),
                userMap.get("zipCode").toString(),
                Integer.parseInt(userMap.get("coins").toString()));
    }



    public User getUserByUsername(String username) {
        //todo make regex for each field

        DataBase db = DataBase.getInstance();

        Map<String,Object> userMap = db.fetchOneMap("SELECT * FROM Users WHERE username = ?", username);

        if (userMap.get("userName") == null) {
            return null;
        }

        return new User(userMap.get("firstName").toString(),
                userMap.get("lastName").toString(),
                username,
                userMap.get("email").toString(),
                userMap.get("password").toString(),
                userMap.get("address").toString(),
                userMap.get("city").toString(),
                userMap.get("zipCode").toString(),
                Integer.parseInt(userMap.get("coins").toString()));
    }

    public ArrayList<User> getAllUsers() {
        DataBase db = DataBase.getInstance();

        ArrayList<HashMap<String,Object>> usersMap = db.fetchAllMap("SELECT * FROM Users WHERE username");

        ArrayList<User> users = new ArrayList<>();

        for (HashMap<String, Object> user : usersMap) {
            users.add(new User(user.get("firstName").toString(),
                    user.get("lastName").toString(),
                    user.get("userName").toString(),
                    user.get("email").toString(),
                    user.get("address").toString(),
                    user.get("zipCode").toString(),
                    user.get("city").toString(),
                    user.get("password").toString(),
                    Integer.parseInt(user.get("coins").toString())));
        }
        return users;
    }

}
