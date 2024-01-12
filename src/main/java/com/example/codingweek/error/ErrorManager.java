package com.example.codingweek.error;

import com.example.codingweek.SerializedData;
import com.example.codingweek.data.ImageFile;
import com.example.codingweek.data.User;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
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
            if (entry.getKey() != "imagePath" && ( entry.getValue() == null || (entry.getValue().getClass() == String.class && entry.getValue().equals("")))) {
                handleEmptyField(entry.getKey());
            }
        }
        raise();
    }

    public void handleInvalidEmail(String email) throws Exception {
        Pattern emailPattern = Pattern.compile("^[\\w-.]+@([\\w-]+.)+[\\w-]{2,4}$");
        if (!emailPattern.matcher(email).find()) {
            errorMessage = "Please enter a valid e-mail address";
        }
        raise();
    }

    public void handleInvalidPassword(String password) throws Exception {
        Pattern pwPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,60}$");

        if (!pwPattern.matcher(password).find()) {
            errorMessage = "Your password must contain at least, 1 uppercase and 1 lowercase characters, 1 digit, 1 special character and be between 8 and 60 letters long.";
        }
        raise();
    }

    public void handleInvalidAddress(String address) throws Exception {
        Pattern addressPattern = Pattern.compile("^([a-zA-Z\\u0080-\\u024F\\d]{1,50}(?:. |-| |')){0,3}[a-zA-Z\\u0080-\\u024F]{0,50}$");

        if (!addressPattern.matcher(address).find()) {
            errorMessage = "Please enter a valid address ";
        }
        raise();
    }

    public void handleInvalidCity(String city) throws Exception {
        Pattern cityPattern = Pattern.compile("^([a-zA-Z\\u0080-\\u024F]{1,50}(?:. |-| |')){0,3}[a-zA-Z\\u0080-\\u024F]{0,50}$");

        if (!cityPattern.matcher(city).find()) {
            errorMessage = "Your city name must be less than 100 character long";
        }
        raise();
    }

    public void handleInvalidZipCode(String zipCode) throws Exception {
        Pattern zipCodePattern = Pattern.compile("^(F-)?\\d{5}");

        if (!zipCodePattern.matcher(zipCode).find()) {
            errorMessage = "You need to enter a valid zip code";
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
        Pattern firstNamePattern = Pattern.compile("^([a-zA-Z\\u0080-\\u024F]{3,20}(?:. |-| |')){0,2}[a-zA-Z\\u0080-\\u024F]{3,20}$");


        if (!firstNamePattern.matcher(firstName).find()) {
            errorMessage = "Your first name must contain at least 3 letters";
        }
        raise();
    }

    public void handleInvalidLastName(String lastName) throws Exception {
        Pattern lastNamePattern = Pattern.compile("^([a-zA-Z\\u0080-\\u024F]{3,20}(?:. |-| |')){0,2}[a-zA-Z\\u0080-\\u024F]{3,20}$");


        if (!lastNamePattern.matcher(lastName).find()) {
            errorMessage = "Your last name must contain at least 3 letters";
        }
        raise();
    }

    public void handleCheckPassWord(User user) throws Exception {
        if (user == null) {
            errorMessage = "Wrong password";
        }
        raise();

    }

    public void handleInvalidCoinsOrder(Integer coins) throws Exception {
        if (coins < 0) {
            errorMessage = "Not enough coins to order that,(try to put offers to gain coins)";
        }
        raise();
    }

    public void handleInvalidDesc(String desc) throws Exception {
        //todo regex et en dessous mon reuf (tt le serr ou ya pas d effort dans el msg)
        if (desc == null) {
            errorMessage = "Desc";
        }
        raise();
    }

    public void handleInvalidTitle(String title) throws Exception {
        if (title == null) {
            errorMessage = "title";
        }
        raise();
    }

    public void handleInvalidPrice(Integer price) throws Exception {
        if (price == null || price < 0) {
            errorMessage = "prend ta tune mon reuf";
        }
        raise();
    }

    public void handleInvalidType(String type) throws Exception {
        if (type == null) {
            errorMessage = "make a choice";
        }
        raise();
    }

    public void handleInvalidImage(ImageFile image) throws Exception {
        if (image != null) {
            if (!(Objects.equals(image.getFileExtension(), "png") || Objects.equals(image.getFileExtension(), "jpg") || Objects.equals(image.getFileExtension(), "jpeg"))) {
                errorMessage = "Image must be png, jpg or jpeg";
            }
            if (image.getTotalSpace() > 1000000000000L) {// val mise au pif
                errorMessage = "Image to heavy";
            }
        }
        raise();
    }


//    public void checkMail(Pattern, field)
}
