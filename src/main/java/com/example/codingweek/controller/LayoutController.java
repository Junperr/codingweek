package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.javafxSceneHandler.ChangeScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LayoutController {
    @FXML
    private Label goToProfile, goToOffers, logOut;

    private final ChangeScene changeScene = new ChangeScene();

    @FXML
    public void goToProfile() throws IOException {
        changeScene.changeSameSceneLabel("static/fxml/myProfile.fxml", goToProfile);
    }

    @FXML
    public void goToAllOffers() throws IOException {
        changeScene.changeSameSceneLabel("static/fxml/allOffers.fxml", goToOffers);
    }

    @FXML
    public void logout() throws IOException {
        changeScene.changeSameSceneLabel("static/fxml/form-login.fxml", logOut);

        CurrentUser.logoutUser();
    }

    @FXML
    public void goToOffers() throws IOException{
        changeScene.changeSameSceneLabel("static/fxml/form-new-offer.fxml", goToOffers);
    }
}
