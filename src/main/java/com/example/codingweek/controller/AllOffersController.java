package com.example.codingweek.controller;

import com.example.codingweek.DAO.OfferDAO;
import com.example.codingweek.Main;
import com.example.codingweek.data.Offer;
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
import java.util.*;

public class AllOffersController implements Initializable {
    @FXML
    public HBox menuBar;
    @FXML
    public ChoiceBox<String> type;
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
    public TextField priceMin;
    public TextField priceMax;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
        type.getItems().addAll("Service", "Loan");
        offers.getChildren().clear();

        DataBase db = DataBase.getInstance();
        ArrayList<ArrayList<Object>> list = db.fetchAll("select title, description, imagePath, price, user, id from Offers");
        for (ArrayList<Object> o : list) {
            String imagePath = (o.get(2) != null) ? o.get(2).toString() : "default.png";

            try {
                loadOffersFromDatabase(
                        o.get(0).toString(),   // title
                        o.get(1).toString(),   // description
                        imagePath,             // imagePath
                        o.get(3).toString(),   // price
                        o.get(4).toString(),    // user
                        o.get(5).toString()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void loadOffersFromDatabase(String title, String description, String imagePath, String price, String user, String id) throws IOException {
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/offerView.fxml");
            FXMLLoader loader = new FXMLLoader(xmlUrl);
            loader.setLocation(xmlUrl);
            VBox offer = loader.load();
            OfferViewController offerViewController = loader.getController();
            offerViewController.initOfferView(title, description, imagePath, price, user, id);
            offers.getChildren().add(offer);
    }


    @FXML
    public void saveFilters(MouseEvent mouseEvent) throws IOException {
        String chosenType = type.getValue();

        ArrayList<String> chosenCategory = new ArrayList<>();
        chosenCategory.add("Jardinage");
        chosenCategory.add("Jardin");

        String chosenZipCode = location.getText();

        String chosenPriceMax = "1000000";
        if (priceMax.getText() != "") {
            chosenPriceMax = priceMax.getText();
        }

        String chosenPriceMin = "0";
        if (priceMin.getText() != "") {
            chosenPriceMin = priceMin.getText();
        }



        OfferDAO offerDAO = new OfferDAO();

        Map<String, Object> map = new HashMap<>();
        if (chosenType != null) map.put("type",chosenType);
        if (chosenCategory != null) map.put("category", chosenCategory);
        if (!chosenZipCode.equals("")) map.put("zipCode", chosenZipCode);
        if (!chosenPriceMax.equals("")) map.put("priceMax", chosenPriceMax);
        if (!chosenPriceMin.equals("")) map.put("priceMin", chosenPriceMin);

        ArrayList<Offer> offers = offerDAO.getOffersWithFilters(map);

        //affichage
        updateVBoxContent(offers);
    }

    public void updateVBoxContent(ArrayList<Offer> offers) throws IOException {
        this.offers.getChildren().clear();
        for (Offer offer : offers) {
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/offerView.fxml");
            FXMLLoader loader = new FXMLLoader(xmlUrl);
            loader.setLocation(xmlUrl);
            VBox offerVbox = loader.load();
            OfferViewController offerViewController = loader.getController();
            offerViewController.initOfferView(offer.getTitle(), offer.getDescription(), offer.getImagePath(), offer.getPrice().toString(), offer.getUser(), offer.getId().toString());
            this.offers.getChildren().add(offerVbox);
        }
    }
}

