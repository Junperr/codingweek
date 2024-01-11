package com.example.codingweek.javafxSceneHandler;

import com.example.codingweek.Main;
import com.example.codingweek.controller.ModifyProfileController;
import com.example.codingweek.controller.MyProfileController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ChangeScene {
    private FXMLLoader loader(String ressourcePath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource(ressourcePath);
        loader.setLocation(xmlUrl);

        return loader;
    }

    public void changeSameSceneButton(String pathToFXML, Button buttonName) throws IOException {
        Stage modification = (Stage) buttonName.getScene().getWindow();
        modification.setScene(new Scene(loader(pathToFXML).load()));
    }

    public void changeSameSceneLabel(String pathToFXML, Label labelName) throws IOException {
        Stage modification = (Stage) labelName.getScene().getWindow();
        modification.setScene(new Scene(loader(pathToFXML).load()));
    }

    public void changeSceneModifyProfile(String setTitle, MyProfileController mainController) throws IOException {
        FXMLLoader loader = loader("static/fxml/modifyProfile.fxml");
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setTitle("Change " + setTitle);
        modification.setScene(new Scene(root));

        ModifyProfileController modifyProfileController = loader.getController();
        modifyProfileController.initData(setTitle);
        modifyProfileController.setStage(modification);
        modifyProfileController.setMainController(mainController);
        modification.showAndWait();
    }
}
