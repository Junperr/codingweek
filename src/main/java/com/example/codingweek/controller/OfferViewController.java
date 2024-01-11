package com.example.codingweek.controller;

import com.example.codingweek.DAO.OfferDAO;
import com.example.codingweek.Main;
import com.example.codingweek.data.Offer;
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

    public void initOfferView(String title, String description, String imagePath, String price, String user, String id){
        this.offerId = UUID.fromString(id);
        this.offerTitle.setText(title);
        this.offerDescription.setText(description);
        this.offerPrice.setText(price);
        this.userOffer.setText(user);

        URL imageUrl = Main.class.getClassLoader().getResource("static/images/" + imagePath);
        if (imageUrl == null) {
            imageUrl = Main.class.getClassLoader().getResource("static/images/default.png");
        }
        Image image = new Image(imageUrl.toExternalForm());
        this.imageOffer.setImage(image);


    }

    public void goToOffer(MouseEvent mouseEvent) throws IOException {

        OfferDAO offerDAO = new OfferDAO();
        Offer offer = offerDAO.getOfferById(this.offerId);

        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/offerPage.fxml");
        FXMLLoader loader = new FXMLLoader(xmlUrl);
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) goToOffer.getScene().getWindow();

        OfferController offerController = loader.getController();
        offerController.initOfferPage(offer.getUser(), offer.getAvailability().toString(), offer.getPrice().toString(), offer.getDescription(), offer.getTitle(), offerId);
        modification.getScene().setRoot(root);

    }

}
