package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.data.Offer;
import com.example.codingweek.data.ImageFile;
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
    private String imageName;

    @FXML
    private ImageView crossImage;
    @FXML
    private Pane background;
    @FXML
    private ImageView florainImage;

    private ImageFile selectedFile;

    private final ChangeScene changeScene = new ChangeScene();

    @FXML
    public void initialize(){
        type.getItems().addAll("Offer", "Service");// Offer types
        florainImage.setImage(new Image("static/images/florain.png"));
        //changer la couleur de bord du combo panel
        themeComboPanel.setStyle("-fx-border-color: #f8edeb");
        themeComboPanel.setStyle("-fx-background-color: #ffffff");
        newOfferButton.setOnAction(event -> {

        });
        titleTextField.setOnKeyPressed(event -> {
            try {
                handleEnterKeyPress(event);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        priceTextField.setOnKeyPressed(event -> {
            try {
                handleEnterKeyPress(event);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    @FXML
    public void submit() throws Exception{

        BigFacade bf = new BigFacade();

        if (this.selectedFile != null){
            this.selectedFile.directory = "offers/";
        }


        ArrayList<String> themes = new ArrayList<>();

        themeComboPanel.getSelectedThemes().forEach(theme -> themes.add(theme));

        bf.createNewOffer(titleTextField.getText(), desc.getText(), selectedFile, Integer.parseInt(priceTextField.getText()), type.getValue(), themes);

        changeScene.changeSameSceneButton("static/fxml/allOffers.fxml", newOfferButton);

    }

    @FXML
    private void goHome() throws IOException{
        changeScene.changeSameSceneButton("static/fxml/allOffers.fxml", newOfferToOffersButton);
    }

    @FXML
    public void addImage() throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(addImageButton.getScene().getWindow());

        this.selectedFile = new ImageFile(selectedFile.getPath(), "offers/");

        if (this.selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toURL().toString());

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
    }

    private void handleEnterKeyPress(KeyEvent event) throws Exception{
        if (event.getCode() == KeyCode.ENTER) {
            try {
                submit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
