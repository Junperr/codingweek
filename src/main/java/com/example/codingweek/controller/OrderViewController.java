package com.example.codingweek.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.UUID;

public class OrderViewController {
    @FXML
    private VBox feedback;
    @FXML
    private Label commandUUID, sellerLabel, costLabel;
    private UUID orderId;
    private Integer cost;
    private String seller;

    public void initOrderView(UUID orderId, Integer cost, String seller) {
        this.orderId = orderId;
        this.cost = cost;
        this.seller = seller;
        commandUUID.setText(orderId.toString());
        sellerLabel.setText(seller);
        costLabel.setText(cost.toString());
    }
}
