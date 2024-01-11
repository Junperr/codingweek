package com.example.codingweek.controller;

import com.example.codingweek.DAO.OfferDAO;
import com.example.codingweek.Main;
import com.example.codingweek.Offer.Offer;
import com.example.codingweek.auth.User;
import com.example.codingweek.database.DataBase;
import com.example.codingweek.facade.OfferFacade;
import com.example.codingweek.javafxComponent.ComboPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class NewOfferController {

    public TextArea desc;
    @FXML
    private TextField titleTextField, priceTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private ChoiceBox<String> type;

    @FXML
    private ComboPanel themeComboPanel;

    @FXML
    private Button newOfferButton, newOfferToOffersButton, addImageButton;
    @FXML
    private ImageView imageArea;
    @FXML
    private ImageView crossImage;
    @FXML
    private Pane background;
    @FXML
    private ImageView florainImage;

    @FXML
    public void initialize() {
        // Initialize your controller here
        type.getItems().addAll("Offer", "Service");// Offer types
        florainImage.setImage(new Image("static/images/florain.png"));
        // Event handler for newOfferButton
        newOfferButton.setOnAction(event -> {

        });

    }

    @FXML
    public void submit() throws IOException{

        errorLabel.setText("");

        // Retrieve values from the controls
        String title = (titleTextField.getText() != null && !titleTextField.getText().isEmpty()) ? titleTextField.getText(): handleEmptyField("tittle");
        String description = (desc.getText() != null && !desc.getText().isEmpty()) ? desc.getText(): handleEmptyField("description");
        String price = (priceTextField.getText() != null && !priceTextField.getText().isEmpty()) ? priceTextField.getText(): handleEmptyField("price");
        String selectedType = (type.getValue() != null && !type.getValue().isEmpty()) ? type.getValue(): handleEmptyField("type");

        if (!errorLabel.getText().isEmpty()){
            return ;
        }

        ArrayList<String> themes = new ArrayList<>();
        for (String theme : themeComboPanel.getSelectedThemes()) {
            themes.add(theme);
        }

        OfferFacade offerFacade = new OfferFacade();
        // image is not implemented yet so by default we put null for the path
        Offer offer = offerFacade.createNewOffer(title, description, null, Integer.parseInt(price), selectedType, themes);

    }

    @FXML
    private void goHome() throws IOException{
        // method to go back to the home page
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/allOffers.fxml");
        loader.setLocation(xmlUrl);
        Parent root = null;
        root = loader.load();
        Stage modification = (Stage) newOfferToOffersButton.getScene().getWindow();
        modification.setScene(new Scene(root));
    }

    private <T> T handleEmptyField(String fieldName) {
        return handleEmptyField(fieldName, "String");
    }

    private <T> T handleEmptyField(String fieldName, String type) {
        if (errorLabel.getText().isEmpty()) {
            errorLabel.setText("Please fill all the fields, empty fields: " + fieldName);
        } else {
            errorLabel.setText(errorLabel.getText() + ", " + fieldName);
        }
        if (type.equals("String")) {
            return (T) "";
        } else if (type.equals("int")) {
            return (T) (Integer) 0;
        }
        return null;
    }

    @FXML
    public void addImage() throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(addImageButton.getScene().getWindow());

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toURL().toString());


            imageArea.setImage(image);
            //rendre le background de couleur #f8edeb
            background.setStyle("-fx-background-color: #f8edeb");
            background.setPrefHeight(imageArea.getFitHeight());
            background.setPrefWidth(imageArea.getFitWidth());

            //crossImage.setImage(new Image("static/images/cross.png"));
            //crossImage.setFitHeight(imageArea.getFitHeight());
            rafraichirInterfaceUtilisateur();
        }
    }

    private void rafraichirInterfaceUtilisateur() {

    }

    @FXML
    public void suppImage() throws IOException{
        imageArea.setImage(null);
        rafraichirInterfaceUtilisateur();
        background.setStyle("-fx-background-color: #ffffff");
        //crossImage.setImage(null);
    }


}
