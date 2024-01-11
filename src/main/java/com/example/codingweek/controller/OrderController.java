package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.User;
import com.example.codingweek.database.DataBase;
import com.example.codingweek.facade.BigFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class OrderController {
    @FXML
    private Button valid;
    @FXML
    private Label errorLabel, recap;
    @FXML
    private PasswordField currentPW;
    private Stage stage;
    private OfferController offerController;
    private UUID offerId;
    private Integer cost;

    @FXML
    private void initialize() {
        currentPW.setOnKeyPressed(this::handleEnterKeyPress);
    }

    public void setStage(Stage stage) {this.stage = stage;}

    public void setMainController(OfferController offerController) {this.offerController = offerController;}

    public void initData(UUID offerId, Integer cost, String title) {
        this.offerId = offerId;
        this.cost = cost;

        recap.setAlignment(javafx.geometry.Pos.CENTER);
        recap.setText("By clicking Confirm you will order \"" + title + "\". \n It is going to cost you " + cost + " florin.\n After the operation you will have " + CurrentUser.getUser().remainAfterBuy(cost) + " florin remaining.");

    }

    @FXML
    private void confirmOrder() throws Exception {
        BigFacade bf = new BigFacade();
        try{
            bf.passOrder(offerId,currentPW.getText(),CurrentUser.getUser());

            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/valid.fxml");
            System.out.println(xmlUrl);
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            Stage modification = (Stage) valid.getScene().getWindow();
            modification.setScene(new Scene(root));
        } catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    private void handleEnterKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                confirmOrder();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
