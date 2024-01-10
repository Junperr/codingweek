package com.example.codingweek.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class OfferController {

    @FXML
    private void passOrder() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getClassLoader().getResource("static/fxml/modifyProfile.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setTitle("Change address");
        modification.setScene(new Scene(root));

        OrderController orderController = loader.getController();
        orderController.initData("address");
        orderController.setStage(modification);
        orderController.setMainController(this);
        modification.showAndWait();
    }
}
