package com.example.codingweek.controller;

import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.User;
import com.example.codingweek.javafxSceneHandler.ChangeScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MyProfileController {
    @FXML
    private HBox menuBar;
    @FXML
    private Button modifyUserName, modifyPassword, modifyLastName, modifyFirstName, modifyEmail, modifyAddress;
    @FXML
    private Label userNameBig, balance, userName, password, lastName, firstName, email, address, zip, city;

    private final ChangeScene changeScene = new ChangeScene();

    @FXML
    public void initialize() {
        User currentUser = CurrentUser.getUser();
        userNameBig.setText(currentUser.userName);
        balance.setText(String.valueOf(currentUser.coins));
        userName.setText(currentUser.userName);
        password.setText(currentUser.password);
        lastName.setText(currentUser.lastName);
        firstName.setText(currentUser.firstName);
        email.setText(currentUser.email);
        address.setText(currentUser.address);
        zip.setText(currentUser.zipCode);
        city.setText(currentUser.city);
    }

    @FXML
    private void setUserName() throws IOException {
        changeScene.changeSceneModifyProfile("username", this);
    }

    @FXML
    private void setPassword() throws IOException {
        changeScene.changeSceneModifyProfile("password", this);
    }

    @FXML
    private void setLastName() throws IOException {
        changeScene.changeSceneModifyProfile("last name", this);
    }

    @FXML
    private void setFirstName() throws IOException {
        changeScene.changeSceneModifyProfile("first name", this);
    }

    @FXML
    private void setEmail() throws IOException {
        changeScene.changeSceneModifyProfile("email", this);
    }

    @FXML
    private void setAddress() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getClassLoader().getResource("static/fxml/modifyAddress.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setTitle("Change address");
        modification.setScene(new Scene(root));

        ModifyAddressController modifyAddressController = loader.getController();
        modifyAddressController.initData();
        modifyAddressController.setStage(modification);
        modifyAddressController.setMainController(this);
        modification.showAndWait();
    }
}
