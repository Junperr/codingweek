package com.example.codingweek21.controller;

import com.example.codingweek21.Main;
import com.example.codingweek21.auth.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LayoutController {
    @FXML
    private Label goToProfile, goToOffers, logOut;


    public void goToProfile() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/myProfile.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) goToProfile.getScene().getWindow();
        modification.setScene(new Scene(root));
    }

    public void goToOffers() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/allOffers.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) goToOffers.getScene().getWindow();
        modification.setScene(new Scene(root));
    }

    public void logout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/form-login.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) logOut.getScene().getWindow();
        modification.setScene(new Scene(root));

        User currentUser = User.wipeInstance();
    }
}
