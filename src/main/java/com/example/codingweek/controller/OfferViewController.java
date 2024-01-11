package com.example.codingweek.controller;

import com.example.codingweek.DAO.OfferDAO;
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

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class OfferViewController extends VBox {
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

    private UUID offerId;

    public void setOfferId(UUID id){
        this.offerId = id;
    }
    public UUID getOfferId(){
        return this.offerId;
    }

    public void initOfferView(Offer offer){
        this.offerId = offer.getId();
        this.offerTitle.setText(offer.getTitle());
        this.offerDescription.setText(offer.getDescription());
        this.offerPrice.setText(offer.getPrice().toString());
        this.userOffer.setText(offer.getUser());

        URL imageUrl = Main.class.getClassLoader().getResource("static/images/" + offer.getImagePath());
        if (imageUrl == null) {
            imageUrl = Main.class.getClassLoader().getResource("static/images/default.png");
        }
        Image image = new Image(imageUrl.toExternalForm());
        this.imageOffer.setImage(image);


    }

    public void goToOffer(MouseEvent mouseEvent) throws IOException {

        BigFacade bf = new BigFacade();
        Offer offer = bf.getOfferById(offerId);

        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/offerPage.fxml");
        FXMLLoader loader = new FXMLLoader(xmlUrl);
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) goToOffer.getScene().getWindow();

        OfferController offerController = loader.getController();
        offerController.initOfferPage(offer);
        modification.getScene().setRoot(root);

    }

}
