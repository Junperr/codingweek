package com.example.codingweek.controller;

import com.example.codingweek.facade.BigFacade;
import com.example.codingweek.javafxSceneHandler.ChangeScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class CreateUserController {
    @FXML
    private TextField firstNameTextField, lastNameTextField, userNameTextField, emailTextField, passwordTextField, addressTextField, cityTextField, zipCodeTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button newAccountButton, back;

    @FXML
    private void initialize() {
        passwordTextField.setOnKeyPressed(this::handleEnterKeyPress);
        firstNameTextField.setOnKeyPressed(this::handleEnterKeyPress);
        lastNameTextField.setOnKeyPressed(this::handleEnterKeyPress);
        userNameTextField.setOnKeyPressed(this::handleEnterKeyPress);
        emailTextField.setOnKeyPressed(this::handleEnterKeyPress);
        addressTextField.setOnKeyPressed(this::handleEnterKeyPress);
        cityTextField.setOnKeyPressed(this::handleEnterKeyPress);
        zipCodeTextField.setOnKeyPressed(this::handleEnterKeyPress);
    }

    private final ChangeScene changeScene = new ChangeScene();

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

            changeScene.changeSameSceneButton("static/fxml/form-login.fxml", newAccountButton);

        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }

    }

    @FXML
    private void cancel() throws IOException {
        changeScene.changeSameSceneButton("static/fxml/form-login.fxml", back);
    }

    private void handleEnterKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            submit();
        }
    }
}
