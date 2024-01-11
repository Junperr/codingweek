package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.Offer;
import com.example.codingweek.data.User;
import com.example.codingweek.facade.BigFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.UUID;

public class OfferController {
    public Label offerPageZipCode;
    @FXML
    private Button order;
    @FXML
    private Label offerPageAvailability, offerPagePrice, offerPageCategory, offerPageDescription, offerPageTitle, sellerId;
    @FXML
    private ImageView pageOfferImage;
    private UUID offerId;
    private ArrayList<Object> data;
    private Integer cost;


    // to be used but petite flemme right now
//    public void initData(UUID offer_id) {
//        // this.offerId = offerId;
//
//        DataBase db = DataBase.getInstance();
//        data = db.fetchOne("select * from Offers where id=?", offer_id);
//    }

    // will use init data instead of this one
    public void initOfferPage(Offer offer) throws Exception {

        String availibility = offer.getAvailability() ? "Available" : "Unavailable";
        System.out.println(offer.getAvailability().toString());

        this.sellerId.setText(offer.getUser());
        this.offerPageAvailability.setText(availibility);
        this.offerPagePrice.setText(offer.getPrice().toString());
        this.offerPageDescription.setText(offer.getDescription());
        this.offerPageTitle.setText(offer.getTitle());
        this.offerId = offer.getId();
        this.cost = offer.getPrice();
        StringJoiner categories = new StringJoiner(", ");
        for (String str : offer.getCategories()) {
            categories.add(str);
        }
        this.offerPageCategory.setText(categories.toString());
        BigFacade bf = new BigFacade();
        this.offerPageZipCode.setText(bf.getUserByUsername(offer.getUser()).zipCode);

        URL imageUrl = Main.class.getClassLoader().getResource("static/images/" + offer.getImagePath());
        if (imageUrl == null) {
            imageUrl = Main.class.getClassLoader().getResource("static/images/default.png");
        }
        Image image = new Image(imageUrl.toExternalForm());
        this.pageOfferImage.setImage(image);

    }

    @FXML
    private void passOrder() throws IOException {
        User currentUser = CurrentUser.getUser();

        if (!currentUser.userName.equals(sellerId.getText())) {
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

    public void goToUserProfile(MouseEvent mouseEvent) throws Exception {
        BigFacade bf = new BigFacade();
        User seller = bf.getUserByUsername(this.sellerId.getText());

        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/offerPage.fxml");
        FXMLLoader loader = new FXMLLoader(xmlUrl);
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) sellerId.getScene().getWindow();

        ProfileForOthersController profileForOthersController = loader.getController();
        profileForOthersController.initUserPageForOthers(bf.getUserByUsername(sellerId.getText()));
        modification.getScene().setRoot(root);
    }
}
