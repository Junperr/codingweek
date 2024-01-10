package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.auth.User;
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
    private Button modifyUserName, modifyPassword, modifyLastName, modifyFirstName, modifyEmail, modifyAddress, modifyZip, modifyCity;
    @FXML
    private Label userNameBig, balance, userName, password, lastName, firstName, email, address, zip, city;

    @FXML
    private void initialize() {
        User currentUser = User.getInstance();
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
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getClassLoader().getResource("static/fxml/modifyProfile.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setTitle("Change username");
        modification.setScene(new Scene(root));

        ModifyProfileController modifyProfileController = loader.getController();
        modifyProfileController.initData("username");
        modifyProfileController.setStage(modification);
        modifyProfileController.setMainController(this);
        modification.showAndWait();
    }

    @FXML
    private void setPassword() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getClassLoader().getResource("static/fxml/modifyProfile.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setTitle("Change password");
        modification.setScene(new Scene(root));

        ModifyProfileController modifyProfileController = loader.getController();
        modifyProfileController.initData("password");
        modifyProfileController.setStage(modification);
        modifyProfileController.setMainController(this);
        modification.showAndWait();
    }

    @FXML
    private void setLastName() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getClassLoader().getResource("static/fxml/modifyProfile.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setTitle("Change last name");
        modification.setScene(new Scene(root));

        ModifyProfileController modifyProfileController = loader.getController();
        modifyProfileController.initData("last name");
        modifyProfileController.setStage(modification);
        modifyProfileController.setMainController(this);
        modification.showAndWait();
    }

    @FXML
    private void setFirstName() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getClassLoader().getResource("static/fxml/modifyProfile.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setTitle("Change first name");
        modification.setScene(new Scene(root));

        ModifyProfileController modifyProfileController = loader.getController();
        modifyProfileController.initData("first name");
        modifyProfileController.setStage(modification);
        modifyProfileController.setMainController(this);
        modification.showAndWait();
    }

    @FXML
    private void setEmail() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getClassLoader().getResource("static/fxml/modifyProfile.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setTitle("Change email");
        modification.setScene(new Scene(root));

        ModifyProfileController modifyProfileController = loader.getController();
        modifyProfileController.initData("email");
        modifyProfileController.setStage(modification);
        modifyProfileController.setMainController(this);
        modification.showAndWait();
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

        ModifyProfileController modifyProfileController = loader.getController();
        modifyProfileController.initData("address");
        modifyProfileController.setStage(modification);
        modifyProfileController.setMainController(this);
        modification.showAndWait();
    }
}
