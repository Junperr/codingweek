package com.example.codingweek21.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AllOffersController implements Initializable {
    @FXML
    public HBox menuBar;
    @FXML
    public ChoiceBox<String> type, radiusOrPostcode;
    @FXML
    public ComboBox<String> category;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public TextField location;
    public VBox offerToAdd;
    public Label offerType, offerCategory, offerDescription, offerPrice, offerTitle;
    public VBox offer;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
        type.getItems().addAll("Service", "Loan");
        radiusOrPostcode.getItems().addAll("Radius", "Post Code");
    }



    @FXML
    public void saveFilters(MouseEvent mouseEvent) {
        String chosenType = type.getValue();
        String chosenCategory = category.getValue();
        String chosenRadiusPostCode = radiusOrPostcode.getValue();
        String chosenNumber = location.getText();

        //db command

        //affichage
        

    }
}

