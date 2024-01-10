package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.auth.User;
import com.example.codingweek.database.DataBase;
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

        User currentUser = User.getInstance();
        Integer remain = (currentUser.coins - cost);
        recap.setText("By clicking confirm you will order of \"" + title + "\" it is going to cost you " + cost + " florin. After the operation you will have " + remain + " florin remain.");
    }

    @FXML
    private void confirmOrder() throws IOException {
        errorLabel.setText("");

        String currentPass = (currentPW.getText() != null && !currentPW.getText().isEmpty()) ? currentPW.getText(): handleEmptyField("currentPass");

        if (!errorLabel.getText().isEmpty()) {
            return;
        }

        User currentUser = User.getInstance();
        DataBase db = DataBase.getInstance();

        ArrayList<Object> dataOffer = db.fetchOne("select * from Offers where id=?", offerId);
        ArrayList<String> dataSeller = db.fetchUser("select * from Users where userName=?", dataOffer.get(3));

        if (currentUser.password.equals(currentPass)) {
            currentUser.coins -= cost;
            Integer sellerCoins = Integer.parseInt(dataSeller.get(8)) + cost;
            UUID orderId = UUID.randomUUID();
            
            // update coins in db 
            db.exec("update Users set coins=? where userName=?", currentUser.coins, currentUser.userName);
            db.exec("update Users set coins=? where userName=?", sellerCoins.toString(), dataSeller.get(2));
            // create the order in db
            db.exec("insert into Orders (id, cost, buyer, seller) values (?,?,?,?)", orderId, cost, currentUser.userName, dataSeller.get(2));
            // modify availability
            db.exec("update Offers set availability=? where id=?", 0, offerId);

            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/valid.fxml");
            System.out.println(xmlUrl);
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            Stage modification = (Stage) valid.getScene().getWindow();
            modification.setScene(new Scene(root));
        } else {
            errorLabel.setText("Wrong password");
        }
    }


    private <T> T handleEmptyField(String fieldName) {
        return handleEmptyField(fieldName,"String");
    }

    private <T> T handleEmptyField(String fieldName, String type) {
        if (errorLabel.getText().isEmpty()){
            errorLabel.setText("Please fill all the fields, empty fields: " + fieldName);
        }else{
            errorLabel.setText(errorLabel.getText() + ", " + fieldName);
        }
        if (type.equals("String")){
            return (T) "";
        }else if (type.equals("int")){
            return (T)(Integer) 0;
        }
        return null;
    }

    private void handleEnterKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                confirmOrder();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
