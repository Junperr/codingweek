package com.example.codingweek.controller;

import com.example.codingweek.data.Message;
import com.example.codingweek.facade.BigFacade;
import com.example.codingweek.javafxSceneHandler.ChangeScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

import java.net.URL;
import java.util.ResourceBundle;

public class AllConvController implements Initializable {
    @FXML
    private VBox messages;

    private final ChangeScene changeScene = new ChangeScene();

    public void setStage(Stage stage) {}
    public void setMainController(LayoutController layoutController) {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BigFacade bf = new BigFacade();

        ArrayList<Message> convPreview = bf.getConvPreview();

        // mettre message si aucune conv
        for (Message message : convPreview) {
            System.out.println(3);
            changeScene.loadConvPreviewFromDatabase(message, messages, "static/fxml/miniConv.fxml");
        }
    }

    public void initialize() {
        BigFacade bf = new BigFacade();

        ArrayList<Message> convPreview = bf.getConvPreview();

        // mettre message si aucune conv
        for (Message message : convPreview) {
            System.out.println(3);
            changeScene.loadConvPreviewFromDatabase(message, messages, "static/fxml/miniConv.fxml");
        }
    }
}
