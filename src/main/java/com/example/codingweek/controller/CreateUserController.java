package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.database.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.util.regex.Pattern;

public class CreateUserController {
    @FXML
    private TextField firstNameTextField, lastNameTextField, userNameTextField, emailTextField, passwordTextField, addressTextField, cityTextField, zipCodeTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button newAccountButton, back;

    @FXML
    private void submit() throws IOException {
        Pattern emailPattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

        String firstName = (firstNameTextField.getText() != null && !firstNameTextField.getText().isEmpty()) ? firstNameTextField.getText() : handleEmptyField("firstName");
        String lastName = (lastNameTextField.getText() != null && !lastNameTextField.getText().isEmpty()) ? lastNameTextField.getText() : handleEmptyField("lastName");
        String userName = (userNameTextField.getText() != null && !userNameTextField.getText().isEmpty()) ? userNameTextField.getText() : handleEmptyField("userName");
        String email = (emailTextField.getText() != null && !emailTextField.getText().isEmpty()) ? emailTextField.getText() : handleEmptyField("email");
        String password = (passwordTextField.getText() != null && !passwordTextField.getText().isEmpty()) ? passwordTextField.getText() : handleEmptyField("password");
        String address = (addressTextField.getText() != null && !addressTextField.getText().isEmpty()) ? addressTextField.getText() : handleEmptyField("address");
        String city = (cityTextField.getText() != null && !cityTextField.getText().isEmpty()) ? cityTextField.getText() : handleEmptyField("city");
        String zipCode = (zipCodeTextField.getText() != null && !zipCodeTextField.getText().isEmpty()) ? zipCodeTextField.getText() : handleEmptyField("zipCode");

        if (!errorLabel.getText().isEmpty()) {
            return;
        }

        if (firstName.length() < 3 || firstName.length() > 30) {
            errorLabel.setText("Your first name must contain between 3 and 30 letters");
            return;
        }
        if (lastName.length() < 3 || lastName.length() > 30) {
            errorLabel.setText("Your last name must contain between 3 and 30 letters");
            return;
        }
        if (userName.length() < 3 || userName.length() > 30) {
            errorLabel.setText("Your user name must contain between 3 and 30 letters");
            return;
        }
        if (!emailPattern.matcher(email).find()) {
            errorLabel.setText("You must enter a valid e-mail address");
            return;
        }
        if (password.length() < 8 || password.length() > 60) {
            errorLabel.setText("Your password must contain between 8 and 60 letters");
            return;
        }
        if (address.length() > 200) {
            errorLabel.setText("Your address must be less than 200 character long");
            return;
        }
        if (city.length() > 100) {
            errorLabel.setText("Your city name must be less than 200 character long");
            return;
        }

        // when the dependencies will work, need to hash the pass word
        DataBase db = DataBase.getInstance();
        db.exec("INSERT INTO Users (userName, firstName, lastName, email, address, zipCode, city, password, coins) VALUES (?,?,?,?,?,?,?,?,'100')", userName, firstName, lastName, email, address, zipCode, city, password);

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/form-login.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) newAccountButton.getScene().getWindow();
        modification.setScene(new Scene(root));
    }

    @FXML
    private void cancel() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/form-login.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) back.getScene().getWindow();
        modification.setScene(new Scene(root));
    }

    private <T> T handleEmptyField(String fieldName) {
        return handleEmptyField(fieldName, "String");
    }

    private <T> T handleEmptyField(String fieldName, String type) {
        if (errorLabel.getText().isEmpty()) {
            errorLabel.setText("Please fill all the fields, empty fields: " + fieldName);
        } else {
            errorLabel.setText(errorLabel.getText() + ", " + fieldName);
        }
        if (type.equals("String")) {
            return (T) "";
        } else if (type.equals("int")) {
            return (T) (Integer) 0;
        }
        return null;
    }
}
