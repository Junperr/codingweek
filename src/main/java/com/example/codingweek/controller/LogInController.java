package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.auth.User;
import com.example.codingweek.database.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class LogInController {

    @FXML
    private Button loginButton, registerButton;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField visibleTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView eyeImageView;
    @FXML
    private Pane centeredPane;

    @FXML
    private VBox centeredVBox;
    private boolean passwordVisible = false;

    @FXML
    private void initialize() {

        visibleTextField.setVisible(false);

        updateEyeImage();

        passwordTextField.setOnKeyPressed(this::handleEnterKeyPress);

        centeredPane.widthProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        centerVBox();
    }

    public void login() throws IOException {
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();

        DataBase db = DataBase.getInstance();
        ArrayList<String> userInfo = db.fetchUser("select * from Users where userName=?", userName);

        if (userInfo == null || !password.equals(userInfo.get(7))) {
            errorLabel.setText("Incorrect username or password");
        } else {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/allOffers.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            Stage modification = (Stage) loginButton.getScene().getWindow();
            modification.setScene(new Scene(root));

            User.makeInstance(userInfo.get(0), userInfo.get(1),userInfo.get(2),userInfo.get(3),userInfo.get(7),userInfo.get(4),userInfo.get(6),userInfo.get(5), Integer.parseInt(userInfo.get(8)));
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

    @FXML
    public void togglePasswordVisibility(MouseEvent e) {
        passwordVisible = !passwordVisible;

        if (passwordVisible) {
            visibleTextField.setText(passwordTextField.getText());
            passwordTextField.setVisible(false);
            visibleTextField.setVisible(true);
        } else {
            passwordTextField.setText(visibleTextField.getText());
            passwordTextField.setVisible(true);
            visibleTextField.setVisible(false);
        }

        updateEyeImage();
    }


    private void updateEyeImage() {
        String imagePath = passwordVisible ? "static/images/eye.png" : "static/images/closedeye.png";
        Image image = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource(imagePath).toExternalForm()));
        eyeImageView.setImage(image);
    }

    private void centerVBox() {
        double xOffset = (centeredPane.getWidth() - centeredVBox.getWidth()) / 2;
        double yOffset = (centeredPane.getHeight() - centeredVBox.getHeight()) / 2;

        centeredVBox.setLayoutX(xOffset);
        centeredVBox.setLayoutY(yOffset);
    }

    private void handleEnterKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                login();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

