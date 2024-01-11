package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.data.Offer;
import com.example.codingweek.facade.BigFacade;
import com.example.codingweek.javafxComponent.ComboPanel;
import com.example.codingweek.javafxSceneHandler.ChangeScene;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
    private ImageView crossImage;
    @FXML
    private Pane background;
    @FXML
    private ImageView florainImage;
    private String relativeImagePath;
    private String imageName;

    private final ChangeScene changeScene = new ChangeScene();

    @FXML
    public void initialize() {
        type.getItems().addAll("Offer", "Service");// Offer types
        florainImage.setImage(new Image("static/images/florain.png"));
        //changer la couleur de bord du combo panel
        themeComboPanel.setStyle("-fx-border-color: #f8edeb");
        themeComboPanel.setStyle("-fx-background-color: #ffffff");
        newOfferButton.setOnAction(event -> {

        });
        titleTextField.setOnKeyPressed(this::handleEnterKeyPress);
        priceTextField.setOnKeyPressed(this::handleEnterKeyPress);

    }

    @FXML
    public void submit() throws IOException{

        errorLabel.setText("");

        // Retrieve values from the controls
        String title = (titleTextField.getText() != null && !titleTextField.getText().isEmpty()) ? titleTextField.getText(): handleEmptyField("title");
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
        Offer offer = bigFacade.createNewOffer(title, description, imageName, Integer.parseInt(price), selectedType, themes);

    }

    @FXML
    private void goHome() throws IOException{
        changeScene.changeSameSceneButton("static/fxml/allOffers.fxml", newOfferToOffersButton);
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

            String destinationDirectory = "src/main/resources/static/images/offers";

            Path targetPath = Path.of(destinationDirectory, selectedFile.getName());
            Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            String relativeImagePath = "images/offers/" + selectedFile.getName();
            this.relativeImagePath = relativeImagePath;
            this.imageName = selectedFile.getName();

            System.out.println("Nom de l'image : " + imageName);

            imageArea.setImage(image);
            background.setStyle("-fx-background-color: #f8edeb");
            background.setPrefHeight(imageArea.getFitHeight());
            background.setPrefWidth(imageArea.getFitWidth());

            crossImage.setImage(new Image("static/images/cross.png"));
            crossImage.setFitHeight(imageArea.getFitHeight());
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
        crossImage.setImage(null);
        //supprimer l'image du répertoire des images des offres
        Files.delete(Path.of("src/main/resources/static/" + relativeImagePath));

    }

    private void handleEnterKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                submit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
