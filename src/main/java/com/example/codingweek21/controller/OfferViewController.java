package com.example.codingweek21.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class OfferViewController extends VBox implements Initializable {
    public VBox offer;
    public Label offerTitle;
    public ImageView imageOffer;
    public Label offerType;
    public Label offerCategory;
    public Label offerDescription;
    public Label offerPrice;
    public Label userOffer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
