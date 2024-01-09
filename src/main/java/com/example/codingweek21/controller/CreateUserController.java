package com.example.codingweek21.controller;

import com.example.codingweek21.Main;
import com.example.codingweek21.database.DataBase;
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
        Pattern EmailPattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String userName = userNameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String address = addressTextField.getText();
        String city = cityTextField.getText();
        int zipCode = Integer.parseInt(zipCodeTextField.getText());

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
        if (!EmailPattern.matcher(email).find()) {
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
        if (zipCode < 1000 || zipCode > 98899) {
            errorLabel.setText("Your zip code must be between 01000 and 98899");
            return;
        }

        // when the dependencies will work, need to hash the pass word
        DataBase db = DataBase.getInstance();
        db.exec("INSERT INTO Users (userName, firstName, lastName, email, password, coins) VALUES (?,?,?,?,?,100)", userName, firstName, lastName, email, password);

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
}
