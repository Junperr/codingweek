package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.database.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

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
    @FXML
    public VBox offerToAdd, offers;
    @FXML
    public Label offerType, offerCategory, offerDescription, offerPrice, offerTitle;
    @FXML
    public UUID offerId;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
        type.getItems().addAll("Service", "Loan");
        radiusOrPostcode.getItems().addAll("Radius", "Post Code");
        offers.getChildren().clear();

        DataBase db = DataBase.getInstance();
        ArrayList<ArrayList<Object>> list = db.fetchAll("select title, description, imagePath, price, user, id from Offers");
        for (ArrayList<Object> o : list) {
            String imagePath = (o.get(2) != null) ? o.get(2).toString() : "default.png";

            loadOffersFromDatabase(
                    o.get(0).toString(),   // title
                    o.get(1).toString(),   // description
                    imagePath,             // imagePath
                    o.get(3).toString(),   // price
                    o.get(4).toString(),    // user
                    o.get(5).toString()
            );

        }
    }

    public void loadOffersFromDatabase(String title, String description, String imagePath, String price, String user, String id) {
        try {
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/offerView.fxml");
            FXMLLoader loader = new FXMLLoader(xmlUrl);
            loader.setLocation(xmlUrl);
            VBox offer = loader.load();


            OfferViewController offerViewController = loader.getController();
            offerViewController.initOfferView(title, description, imagePath, price, user, id);
            offers.getChildren().add(offer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

