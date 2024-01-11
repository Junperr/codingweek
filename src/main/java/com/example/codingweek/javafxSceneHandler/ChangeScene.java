package com.example.codingweek.javafxSceneHandler;

import com.example.codingweek.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ChangeScene {
    public void changeSameSceneButton(String pathToFXML, Button buttonName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource(pathToFXML);
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) buttonName.getScene().getWindow();
        modification.setScene(new Scene(root));
    }

    public void changeSameSceneLabel(String pathToFXML, Label labelName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource(pathToFXML);
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) labelName.getScene().getWindow();
        modification.setScene(new Scene(root));
    }
}
