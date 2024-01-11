package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.User;
import com.example.codingweek.database.DataBase;
import com.example.codingweek.facade.BigFacade;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;


public class ModifyProfileController {
    @FXML
    private Button saveButton;
    @FXML
    private TextField newWhat;
    @FXML
    private PasswordField currentPW;
    @FXML
    private Label errorLabel;
    private Stage stage;
    private MyProfileController myProfileController;
    private String index;


    @FXML
    private void initialize() {

        currentPW.setOnKeyPressed(this::handleEnterKeyPress);
        newWhat.setOnKeyPressed(this::handleEnterKeyPress);
    }

    public void initData(String toChange) {
        this.index = toChange;
        newWhat.setPromptText("New " + toChange);
    }

    public void setStage(Stage stage) {this.stage = stage;}

    public void setMainController(MyProfileController myProfileController) {this.myProfileController = myProfileController;}

    @FXML
    private void submit() throws IOException {
        BigFacade bf = new BigFacade();
        try{
            bf.updateField(CurrentUser.getUser(), newWhat.getText(), index, currentPW.getText());

            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/valid.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            Stage modification = (Stage) saveButton.getScene().getWindow();
            modification.setScene(new Scene(root));

            myProfileController.initialize();
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
