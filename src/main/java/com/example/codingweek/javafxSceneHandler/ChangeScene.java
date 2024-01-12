package com.example.codingweek.javafxSceneHandler;

import com.example.codingweek.Main;
import com.example.codingweek.controller.*;
import com.example.codingweek.data.Message;
import com.example.codingweek.data.Offer;
import com.example.codingweek.data.Order;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ChangeScene {
    private FXMLLoader loader(String ressourcePath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource(ressourcePath);
        loader.setLocation(xmlUrl);

        return loader;
    }

    public void changeSameSceneButton(String pathToFXML, Button buttonName) throws IOException {
        Stage modification = (Stage) buttonName.getScene().getWindow();
        modification.setResizable(false);
        modification.setScene(new Scene(loader(pathToFXML).load()));
    }

    public void changeSameSceneLabel(String pathToFXML, Label labelName) throws IOException {
        Stage modification = (Stage) labelName.getScene().getWindow();
        modification.setResizable(false);
        modification.setScene(new Scene(loader(pathToFXML).load()));
    }

    public void changeSceneModifyProfile(String setTitle, MyProfileController mainController) throws IOException {
        FXMLLoader loader = loader("static/fxml/modifyProfile.fxml");
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setResizable(false);
        modification.setTitle("Change " + setTitle);
        modification.setScene(new Scene(root));

        ModifyProfileController modifyProfileController = loader.getController();
        modifyProfileController.initData(setTitle);
        modifyProfileController.setStage(modification);
        modifyProfileController.setMainController(mainController);
        modification.showAndWait();
    }

    public void changeSceneToMessage(LayoutController layoutController) throws IOException {
        FXMLLoader loader = loader("static/fxml/allConvos.fxml");
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setScene(new Scene(root));

        AllConvController allConvController = loader.getController();
        allConvController.setStage(modification);
        allConvController.setMainController(layoutController);
        modification.showAndWait();
    }

    public void changeSceneToConv(MiniConvController miniConvController, String otherName) throws IOException {
        FXMLLoader loader = loader("static/fxml/convo.fxml");
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setScene(new Scene(root));

        ConvController convController = loader.getController();
        convController.setStage(modification);
        convController.setMainController(miniConvController);
        convController.initConv(otherName);
        modification.showAndWait();
    }

    public void changeSceneToConvOffer(OfferController offerController, String otherName) throws IOException {
        FXMLLoader loader = loader("static/fxml/convo.fxml");
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setScene(new Scene(root));

        ConvController convController = loader.getController();
        convController.setStage(modification);
        convController.setMainControllerOffer(offerController);
        convController.initConvOffer(otherName);
        modification.showAndWait();
    }

    // load from database

    public void loadOffersFromDatabase(Offer offer, VBox offers, String pathToFXML) {
        try {
            FXMLLoader loader = loader(pathToFXML);
            VBox offerVBox = loader.load();

            OfferViewController offerViewController = loader.getController();
            offerViewController.initOfferView(offer);
            offers.getChildren().add(offerVBox);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadOrdersFromDatabase(Order order, VBox orders, String pathToFXML) {
        try {
            FXMLLoader loader = loader(pathToFXML);
            VBox orderVBox = loader.load();

            OrderViewController orderViewController = loader.getController();
            orderViewController.initOrderView(order);
            orders.getChildren().add(orderVBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadConvPreviewFromDatabase(Message message, VBox messages, String pathToFXML) {
        try {
            FXMLLoader loader = loader(pathToFXML);
            VBox convVBox = loader.load();

            MiniConvController miniConvController = loader.getController();
            miniConvController.initMiniConv(message);
            messages.getChildren().add(convVBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadConvFromDatabase(Message conv, VBox message, String pathToFXML) {
        try {
            FXMLLoader loader = loader(pathToFXML);
            VBox messageVBox = loader.load();

            MessageViewController messageController = loader.getController();
            messageController.initMessage(conv);
            message.getChildren().add(messageVBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
