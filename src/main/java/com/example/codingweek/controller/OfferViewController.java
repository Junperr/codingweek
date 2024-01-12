package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.data.Offer;
import com.example.codingweek.facade.BigFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.StringJoiner;
import java.util.UUID;

public class OfferViewController extends VBox {

    @FXML
    public ImageView illu;
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
    public VBox goToOffer;
    public Label zipCode;



    private UUID offerId;

    public void setOfferId(UUID id){
        this.offerId = id;
    }
    public UUID getOfferId(){
        return this.offerId;
    }

    public void initOfferView(Offer offer) throws Exception {
        this.offerId = offer.getId();
        this.offerTitle.setText(offer.getTitle());
        this.offerDescription.setText(offer.getDescription());
        this.offerPrice.setText(offer.getPrice().toString());
        this.userOffer.setText(offer.getUser());
        this.offerType.setText(offer.getType());
        //access to user's zipCode
        BigFacade bf = new BigFacade();
        this.zipCode.setText(bf.getUserByUsername(offer.getUser()).zipCode);
        this.offerCategory.setText(offer.categoryString());
        System.out.println("image path : " + offer.getImagePath());
        System.out.println(Main.class.getClassLoader().getResource("static/images/") + offer.getImagePath());
        Image image = new Image(Main.class.getClassLoader().getResource("static/images/") + offer.getImagePath());
        this.imageOffer.setImage(image);
        illu.setImage(new Image("static/images/florain.png"));
        illu.setFitHeight(20);
    }

    public void goToOffer(MouseEvent mouseEvent) throws Exception {

        BigFacade bf = new BigFacade();
        Offer offer = bf.getOfferById(offerId);

        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/offerPage.fxml");
        FXMLLoader loader = new FXMLLoader(xmlUrl);
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) goToOffer.getScene().getWindow();
        modification.setResizable(false);

        OfferController offerController = loader.getController();
        offerController.initOfferPage(offer);
        modification.getScene().setRoot(root);
    }

    public void goToUser(MouseEvent mouseEvent) {
    }

    public void hoverHandler(MouseEvent mouseEvent) {
        offer.setStyle("-fx-background-color: #f2f2f2");
        offerTitle.setStyle("-fx-text-fill: #FFB5A7");
        offer.setStyle("-fx-border-padding: 3");
        offer.setStyle("-fx-border-color: #FFB5A7");
    }

    public void exitHandler(MouseEvent mouseEvent) {
        offer.setStyle("-fx-background-color: #ffffff");
        offerTitle.setStyle("-fx-text-fill: #000000");
        offer.setStyle("-fx-border-padding: 3");
        offer.setStyle("-fx-border-color: #ffffff");

    }
}
