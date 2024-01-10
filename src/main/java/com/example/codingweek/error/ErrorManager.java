package com.example.codingweek.error;

import com.example.codingweek.SerializedData;
import com.example.codingweek.data.User;

import java.util.HashMap;
import java.util.regex.Pattern;

public class ErrorManager {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public ErrorManager() {
        this.errorMessage = "";
    }

    public void raise() throws Exception {
        if (!errorMessage.isEmpty()) {
            throw new Exception(errorMessage);
        }
    }

    public void handleLogin(User user) throws Exception {
        if (user == null) {
            errorMessage = "Incorrect username or password";
        }
        raise();
    }

    public void handleEmptyField(String fieldName) {
        if (errorMessage.isEmpty()) {
            errorMessage = "Please fill all the fields, empty fields: " + fieldName;
        } else {
            errorMessage = errorMessage + ", " + fieldName;
        }
    }

    public void handleEmptyFields(SerializedData objet) throws Exception {
        HashMap<String, Object> objetMap = objet.toMap();
        for (HashMap.Entry<String, Object> entry : objetMap.entrySet()) {
            if (entry.getValue() == null || (entry.getValue().getClass() == String.class && entry.getValue().equals(""))) {
                handleEmptyField(entry.getKey());
            }
        }
        raise();
    }

    public void handleInvalidEmail(String email) throws Exception {
        Pattern emailPattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

        if (!emailPattern.matcher(email).find()) {
            errorMessage = "Please enter a valid e-mail address";
        }
        raise();
    }

    public void handleInvalidPassword(String password) throws Exception {
        if (password.length() < 8 || password.length() > 60) {
            errorMessage = "Your password must contain between 8 and 60 letters";
        }
        raise();
    }

    public void handleInvalidAddress(String address) throws Exception {
        if (address.length() > 200) {
            errorMessage = "Your address must be less than 200 character long";
        }
        raise();
    }

    public void handleInvalidCity(String city) throws Exception {
        if (city.length() > 100) {
            errorMessage = "Your city name must be less than 100 character long";
        }
        raise();
    }

    public void handleInvalidZipCode(String zipCode) throws Exception {
        if (zipCode.length() != 5) {
            errorMessage = "Your zipcode must be of length 5";
        }
        raise();
    }

    public void handleInvalidUserName(String username, User user) throws Exception {


        if (user != null) {
            errorMessage = "This username is already used, try another one";
        }
        raise();

        if (username.length() > 20) {
            errorMessage = "Your username must be less than 20 character long";
        }
        raise();
    }

    public void handleInvalidFirstName(String firstName) throws Exception {
        if (firstName.length() < 3 || firstName.length() > 30) {
            errorMessage = "Your first name must contain between 3 and 30 letters";
        }
        raise();
    }

    public void handleInvalidLastName(String lastName) throws Exception {
        if (lastName.length() < 3 || lastName.length() > 30) {
            errorMessage = "Your last name must contain between 3 and 30 letters";
        }
        raise();
    }

    public void handleCheckPassWord(User user) throws Exception {
        if (user.userName == null) {
            errorMessage = "Wrong password";
        }
        raise();

    }

//    public void checkMail(Pattern, field)
}
