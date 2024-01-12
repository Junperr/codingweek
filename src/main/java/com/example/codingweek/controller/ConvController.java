package com.example.codingweek.controller;

import com.example.codingweek.data.Message;
import com.example.codingweek.facade.BigFacade;
import com.example.codingweek.javafxSceneHandler.ChangeScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ConvController {
    @FXML
    private VBox oneMess;
    @FXML
    private Label theirName;
    @FXML
    private TextField messageTextField;
    @FXML
    private ScrollPane scroll;

    private String otherName;

    private final ChangeScene changeScene = new ChangeScene();

    public void setStage(Stage stage) {}
    public void setMainController(MiniConvController miniConvController) {}
    public void setMainControllerOffer(OfferController offerController) {}

    @FXML
    private void sendMess() {
        String message = messageTextField.getText();

        BigFacade bf = new BigFacade();
        bf.newMessage(message, this.otherName);

        initConv(this.otherName);
    }

    public void initConv(String otherName) {
        oneMess.getChildren().clear();
        BigFacade bf = new BigFacade();
        ArrayList<Message> conv = bf.getConv(otherName);

        for (Message m : conv) {
            changeScene.loadConvFromDatabase(m, oneMess, "static/fxml/messageVIew.fxml");
        }

        this.otherName = otherName;

        theirName.setText(otherName);
        scroll.setVvalue(1.0);
    }

    public void initConvOffer(String otherName) {
        this.otherName = otherName;
        theirName.setText(otherName);
    }
}
