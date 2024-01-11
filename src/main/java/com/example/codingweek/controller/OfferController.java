package com.example.codingweek.controller;

import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.Offer;
import com.example.codingweek.data.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private Integer cost;
    private String sellerId;

    // to be used but petite flemme right now
//    public void initData(UUID offer_id) {
//        // this.offerId = offerId;
//
//        DataBase db = DataBase.getInstance();
//        data = db.fetchOne("select * from Offers where id=?", offer_id);
//    }

    // will use init data instead of this one
    public void initOfferPage(Offer offer){

        String availibility = offer.getAvailability() ? "Available" : "Unavailable";

        this.offerPageUser.setText(CurrentUser.getUser().userName);
        this.offerPageAvailability.setText(availibility);
        this.offerPagePrice.setText(offer.getPrice().toString());
        this.offerPageDescription.setText(offer.getDescription());
        this.offerPageTitle.setText(offer.getTitle());
        this.offerId = offer.getId();
        this.cost = offer.getPrice();
        this.sellerId = offer.getUser();
    }

    @FXML
    private void passOrder() throws IOException {
        User currentUser = CurrentUser.getUser();

        if (!currentUser.userName.equals(sellerId)) {
            if (currentUser.coins >= cost) {
                FXMLLoader loader = new FXMLLoader();
                URL xmlUrl = getClass().getClassLoader().getResource("static/fxml/orderViews.fxml");
                loader.setLocation(xmlUrl);
                Parent root = loader.load();

                Stage modification = new Stage();
                modification.setTitle("Confirm your order");
                modification.setScene(new Scene(root));

                OrderController orderController = loader.getController();
                orderController.initData(offerId, cost, offerPageTitle.getText());
                orderController.setStage(modification);
                orderController.setMainController(this);
                modification.showAndWait();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Poor little thing, forget it");
                a.show();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("You can't order from yourself");
            a.show();
        }
    }
}
