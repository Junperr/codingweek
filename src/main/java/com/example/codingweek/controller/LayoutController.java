package com.example.codingweek.controller;

import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.facade.BigFacade;
import com.example.codingweek.javafxSceneHandler.ChangeScene;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class LayoutController {
    @FXML
    private Label createOffer;
    @FXML
    private Label goToProfile, goToOffers, logOut, toMessage;

    @FXML
    private ImageView logout, message;


    private final ChangeScene changeScene = new ChangeScene();

    @FXML
    public void initialize(){
        logout.setImage(new Image("static/images/logout.png"));
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(1.0);
        logout.setEffect(colorAdjust);

        toMessage.setText("Messages");
        message.setImage(new Image("static/images/message.png"));
        message.setEffect(colorAdjust);
    }

    @FXML
    public void hoverHandlerL(){
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.1);
        logout.setEffect(colorAdjust);
        logOut.setStyle("-fx-text-fill: #000000");
    }

    @FXML
    public void hoverExitHandlerL(){
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(1.0);
        logout.setEffect(colorAdjust);
        logOut.setStyle("-fx-text-fill: #ffffff");
    }

    @FXML
    public void hoverHandlerM(){
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.1);
        message.setEffect(colorAdjust);
        toMessage.setStyle("-fx-text-fill: #000000");
    }

    @FXML
    public void hoverExitHandlerM(){
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(1.0);
        message.setEffect(colorAdjust);
        toMessage.setStyle("-fx-text-fill: #ffffff");
    }


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

    @FXML
    public void goToMessages() throws IOException {
        changeScene.changeSceneToMessage(this);
    }
}
