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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class LogInController {
    @FXML
    private Button loginButton, registerButton;
    @FXML
    private TextField userNameTextField, passwordTextField;
    @FXML
    private Label errorLabel;

    public void login() throws IOException {
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();

        DataBase db = DataBase.getInstance();
        Object userInfo = db.fetchOne("select * from Users where userName=?", userName);

        if (userInfo == null) {
            errorLabel.setText("Incorrect password or username");
        } else {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/allOffers.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            Stage modification = (Stage) loginButton.getScene().getWindow();
            modification.setScene(new Scene(root));
        }
    }

    public void register() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/form-new-account.fxml");
        System.out.println(xmlUrl);
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) registerButton.getScene().getWindow();
        modification.setScene(new Scene(root));
    }
}
