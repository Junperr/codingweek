package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.database.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.UUID;

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
    public VBox goToOffer;

    private UUID offerId;

    public void setOfferId(UUID id){
        this.offerId = id;
    }
    public UUID getOfferId(){
        return this.offerId;
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goToOffer(MouseEvent mouseEvent) throws IOException {

        DataBase db = DataBase.getInstance();
        ArrayList<Object> list = db.fetchOne("select user, availability, price, description, title from Offers where id=?", this.offerId);

        String userPageOffer = list.get(0).toString();
        String availabilityPageOffer = list.get(1).toString();


        String pricePageOffer = list.get(2).toString();
        String descriptionPageOffer = list.get(3).toString();
        String titlePageOffer = list.get(4).toString();

        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/offerPage.fxml");
        FXMLLoader loader = new FXMLLoader(xmlUrl);
        AnchorPane offer = loader.load();

        Label title = (Label) offer.lookup("#offerPageTitle");
        title.setText(Objects.requireNonNullElse(titlePageOffer, "No title"));
        Label user = (Label) offer.lookup("#offerPageUser");
        user.setText(Objects.requireNonNullElse(userPageOffer, "No user"));

        Label availability = (Label) offer.lookup("#offerPageAvailability");
        if(availabilityPageOffer != null){
            if(availabilityPageOffer.equals("0")) availability.setText("No");
            else availability.setText("Yes");
        } else availability.setText("No availability");

        Label price = (Label) offer.lookup("#offerPagePrice");
        price.setText(Objects.requireNonNullElse(pricePageOffer, "No price"));
        //category

        Label description = (Label) offer.lookup("#offerPageDescription");
        description.setText(Objects.requireNonNullElse(descriptionPageOffer, "No description"));

        Stage modification = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modification.getScene().setRoot(offer);
    }
}
