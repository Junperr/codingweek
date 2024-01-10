package com.example.codingweek.controller;

import com.example.codingweek.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OfferViewController extends VBox implements Initializable {
    @FXML
    public VBox offer;
    @FXML
    public Label offerTitle;
    @FXML
    public ImageView imageOffer;
    @FXML
    public Label offerType;
    @FXML
    public Label offerCategory;
    public Label offerDescription;
    @FXML
    public Label offerPrice;
    @FXML
    public Label userOffer;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goToOffer(KeyEvent keyEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/offerPage.fxml");

        //Label offerId = (Label) offer.lookup("#offerTitle");
        //offerTitle.setText(title);

        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) offerTitle.getScene().getWindow();
        modification.setScene(new Scene(root));
    }
}
