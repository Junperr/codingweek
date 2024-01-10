package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.data.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class LayoutController {
    @FXML
    private Label goToProfile, goToOffers, logOut;

    @FXML
    public void goToProfile() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/myProfile.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) goToProfile.getScene().getWindow();
        modification.setScene(new Scene(root));
    }

    @FXML
    public void goToAllOffers() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/allOffers.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) goToOffers.getScene().getWindow();
        modification.setScene(new Scene(root));
    }

    @FXML
    public void logout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/form-login.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) logOut.getScene().getWindow();
        modification.setScene(new Scene(root));

        User currentUser = User.wipeInstance();
    }

    @FXML
    public void goToOffers() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/form-new-offer.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) goToOffers.getScene().getWindow();
        modification.setScene(new Scene(root));
    }
}
