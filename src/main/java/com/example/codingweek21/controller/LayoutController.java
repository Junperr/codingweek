package com.example.codingweek21.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LayoutController {

    public Label myProfile;
    public Label offers;
    public MenuBar menuBar;
    public Label logout;


    public void goToMyProfilePage(MouseEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("myProfile.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) myProfile.getScene().getWindow();
        modification.setScene(new Scene(root));

    }

    public void goToOffers(MouseEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("allOffers.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) offers.getScene().getWindow();
        modification.setScene(new Scene(root));
    }

    public void logout(MouseEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("form-login.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) logout.getScene().getWindow();
        modification.setScene(new Scene(root));

    }
}
