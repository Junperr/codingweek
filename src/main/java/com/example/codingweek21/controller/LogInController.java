package com.example.codingweek21.controller;

import com.example.codingweek21.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LogInController {

    public Button loginButton;
    public Button registerButton;

    public void login(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/allOffers.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) loginButton.getScene().getWindow();
        modification.setScene(new Scene(root));
    }

    public void register(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/form-new-account.fxml");
        System.out.println(xmlUrl);
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) registerButton.getScene().getWindow();
        modification.setScene(new Scene(root));
    }
}
