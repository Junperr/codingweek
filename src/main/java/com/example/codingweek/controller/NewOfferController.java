package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.data.Offer;
import com.example.codingweek.facade.BigFacade;
import com.example.codingweek.javafxComponent.ComboPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

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
    public void initialize() {
        // Initialize your controller here
        type.getItems().addAll("Offer", "Service"); // Offer types

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

        BigFacade bigFacade = new BigFacade();
        // image is not implemented yet so by default we put null for the path
        Offer offer = bigFacade.createNewOffer(title, description, null, Integer.parseInt(price), selectedType, themes);

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
            System.out.println("Chemin de l'image ajoutée : " + selectedFile.toURI().toURL().toString());
            imageArea.setImage(image);
            rafraichirInterfaceUtilisateur();
        }
    }

    private void rafraichirInterfaceUtilisateur() {

    }

    @FXML
    public void suppImage() throws IOException{
        imageArea.setImage(null);
        rafraichirInterfaceUtilisateur();
    }


}
