package com.example.codingweek21.controller;

import com.example.codingweek21.Main;
import com.example.codingweek21.auth.User;
import com.example.codingweek21.database.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class LogInController {
    @FXML
    private Button loginButton, registerButton;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView eyeImageView;

    private void initialize() {
        String imagePath = getClass().getResource("static/images/eye.png").toExternalForm();
        javafx.scene.image.Image image = new javafx.scene.image.Image(imagePath);
        eyeImageView.setImage(new javafx.scene.image.Image(imagePath, 15, 15, true, true));
    }

    public void login() throws IOException {
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();

        DataBase db = DataBase.getInstance();
        ArrayList<String> userInfo = db.fetchUser("select * from Users where userName=?", userName);

        if (userInfo == null || !password.equals(userInfo.get(7))){
            errorLabel.setText("Incorrect username or password");
        } else {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/allOffers.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            Stage modification = (Stage) loginButton.getScene().getWindow();
            modification.setScene(new Scene(root));

            User currentUser = User.makeInstance(userInfo.get(0), userInfo.get(1),userInfo.get(2),userInfo.get(3),userInfo.get(7),userInfo.get(4),userInfo.get(6),userInfo.get(5), Integer.parseInt(userInfo.get(8)));
        }
    }

    public void register() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/form-new-account.fxml");
        System.out.println(xmlUrl);
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) registerButton.getScene().getWindow();
        modification.setScene(new Scene(root));
    }
}
