package com.example.codingweek.controller;

import com.example.codingweek.database.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class OfferController {
    @FXML
    private Button order;
    @FXML
    private Label offerPageUser, offerPageAvailability, offerPagePrice, offerPageCategory, offerPageDescription, offerPageTitle;
    @FXML
    private ImageView pageOfferImage;
    private UUID offerId;
    private ArrayList<Object> data;

    public void initData(UUID offer_id) {
        this.offerId = offerId;

        DataBase db = DataBase.getInstance();
        data = db.fetchOne("select * from Offers where id=?", offer_id);
    }

    @FXML
    private void passOrder() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getClassLoader().getResource("static/fxml/modifyProfile.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setTitle("Change address");
        modification.setScene(new Scene(root));

        OrderController orderController = loader.getController();
        orderController.initData("address");
        orderController.setStage(modification);
        orderController.setMainController(this);
        modification.showAndWait();
    }


    public void initOfferPage(String userPageOffer, String availabilityPageOffer, String pricePageOffer, String descriptionPageOffer, String titlePageOffer, UUID offerId){
        this.offerPageUser.setText(userPageOffer);
        this.offerPageAvailability.setText(availabilityPageOffer);
        this.offerPagePrice.setText(pricePageOffer);
        this.offerPageDescription.setText(descriptionPageOffer);
        this.offerPageTitle.setText(titlePageOffer);
        this.offerId = offerId;
    }
}
