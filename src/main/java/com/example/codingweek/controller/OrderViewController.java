package com.example.codingweek.controller;

import com.example.codingweek.data.Order;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.UUID;

public class OrderViewController {
    public VBox order;
    @FXML
    private VBox feedback;
    @FXML
    private Label commandUUID, sellerLabel, costLabel;
    private UUID orderId;
    private Integer cost;
    private String seller;

    public void initOrderView(Order order) {
        this.orderId = order.getId();
        this.cost = order.getCost();
        this.seller = order.getSeller();
        commandUUID.setText(orderId.toString());
        sellerLabel.setText(seller);
        costLabel.setText(cost.toString());
    }

    @FXML
    private void getFeedback() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getClassLoader().getResource("static/fxml/professor.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setTitle("Give a review");
        modification.setResizable(false);
        modification.setScene(new Scene(root));

        ProfessorController professorController = loader.getController();
        professorController.orderData(orderId);
        professorController.setStage(modification);
        professorController.setMainController(this);
        modification.showAndWait();
    }

}
