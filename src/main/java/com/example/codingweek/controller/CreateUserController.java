package com.example.codingweek.controller;

import com.example.codingweek.Main;
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

public class CreateUserController {
    @FXML
    private TextField firstNameTextField, lastNameTextField, userNameTextField, emailTextField, passwordTextField, addressTextField, cityTextField, zipCodeTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button newAccountButton, back;

    @FXML
    private void submit() {
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

            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/form-login.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            Stage modification = (Stage) newAccountButton.getScene().getWindow();
            modification.setScene(new Scene(root));

        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
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
}
