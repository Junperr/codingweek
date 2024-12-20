package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.data.Offer;
import com.example.codingweek.facade.BigFacade;
import com.example.codingweek.javafxComponent.ComboPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

public class AllOffersController implements Initializable {
    @FXML
    public HBox menuBar;
    @FXML
    public ChoiceBox<String> type;

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
    public TextField priceMin;
    public TextField priceMax;
    public ComboPanel themeComboPanel;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
        type.getItems().addAll("Service", "Loan");
        offers.getChildren().clear();

        BigFacade bf = new BigFacade();
        try {
            updateVBoxContent(bf.getOffersWithFilters(null, null, null, null, null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void saveFilters(MouseEvent mouseEvent) throws Exception {
        BigFacade bf = new BigFacade();
        updateVBoxContent(bf.getOffersWithFilters(type.getValue(), location.getText(), priceMin.getText(), priceMax.getText(), new ArrayList<>(themeComboPanel.getSelectedThemes())));
    }

    public void updateVBoxContent(ArrayList<Offer> offers) throws Exception {
        this.offers.getChildren().clear();
        for (Offer offer : offers) {
            System.out.println("offer : "+ offer.getTitle() );
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/offerView.fxml");
            FXMLLoader loader = new FXMLLoader(xmlUrl);
            loader.setLocation(xmlUrl);
            VBox offerVbox = loader.load();
            OfferViewController offerViewController = loader.getController();
            offerViewController.initOfferView(offer);
            this.offers.getChildren().add(offerVbox);
        }
    }
}

