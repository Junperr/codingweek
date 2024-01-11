package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.User;
import com.example.codingweek.database.DataBase;
import com.example.codingweek.facade.BigFacade;
import com.example.codingweek.javafxSceneHandler.ChangeScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ModifyAddressController {
    @FXML
    private Button saveButton;
    @FXML
    private TextField newAddressTextField, newZipCodeTextField, newCityTextField;
    @FXML
    private PasswordField currentPW;
    @FXML
    private Label errorLabel;
    private Stage stage;
    private MyProfileController myProfileController;
    private String index;

    private final ChangeScene changeScene = new ChangeScene();
    
    @FXML
    private void initialize() {
        currentPW.setOnKeyPressed(this::handleEnterKeyPress);
    }
    
    public void setStage(Stage stage) {this.stage = stage;}

    public void initData() {
        newAddressTextField.setPromptText("New Address");
        newZipCodeTextField.setPromptText("New Zip Code");
        newCityTextField.setPromptText("New City");
    }

    public void setMainController(MyProfileController myProfileController) {this.myProfileController = myProfileController;}

    @FXML
    private void submit() throws IOException {

        BigFacade bf = new BigFacade();
        try {
            bf.updatePosUser(CurrentUser.getUser(),newAddressTextField.getText(),newCityTextField.getText(), newZipCodeTextField.getText(), currentPW.getText());

            changeScene.changeSameSceneButton("static/fxml/valid.fxml", saveButton);

        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void handleEnterKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                submit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
