package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.facade.BigFacade;
import com.example.codingweek.javafxSceneHandler.ChangeScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class LogInController {

    @FXML
    private Button loginButton, registerButton;
    @FXML
    private TextField userNameTextField, visibleTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView eyeImageView;
//    @FXML
//    private Pane centeredPane;
    @FXML
    private VBox centeredVBox;

    private boolean passwordVisible = false;
    private final ChangeScene changeScene = new ChangeScene();

    @FXML
    private void initialize() {

        visibleTextField.setVisible(false);

        updateEyeImage();

        passwordTextField.setOnKeyPressed(this::handleEnterKeyPress);
        userNameTextField.setOnKeyPressed(this::handleEnterKeyPress);

        //centeredPane.widthProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        //centerVBox();
    }

    public void login() throws IOException {

        BigFacade bf = new BigFacade();
        try {
            bf.logUser(userNameTextField.getText(), passwordTextField.getText());

            changeScene.changeSameSceneButton("static/fxml/allOffers.fxml", loginButton);

        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }

    }

    public void register() throws IOException {
        changeScene.changeSameSceneButton("static/fxml/form-new-account.fxml", registerButton);
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

//    private void centerVBox() {
//        double xOffset = (centeredPane.getWidth() - centeredVBox.getWidth()) / 2;
//        double yOffset = (centeredPane.getHeight() - centeredVBox.getHeight()) / 2;
//
//        centeredVBox.setLayoutX(xOffset);
//        centeredVBox.setLayoutY(yOffset);
//    }

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

