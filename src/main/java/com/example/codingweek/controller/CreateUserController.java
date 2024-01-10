package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.database.DataBase;
import com.example.codingweek.facade.BigFacade;
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

import java.util.ArrayList;
import java.util.Arrays;
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
        System.out.println("submitting");
        BigFacade bf = new BigFacade();
        try {
            bf.createNewUser(firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    userNameTextField.getText(),
                    emailTextField.getText(),
                    passwordTextField.getText(),
                    addressTextField.getText(),
                    cityTextField.getText(),
                    zipCodeTextField.getText());
            System.out.println("How did it arrived");
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/form-login.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            Stage modification = (Stage) newAccountButton.getScene().getWindow();
            modification.setScene(new Scene(root));

        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
            e.printStackTrace();
        }


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
