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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class LogInController {
    @FXML
    private HBox passwordContainer;
    @FXML
    private Button loginButton, registerButton;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordTextField;
    private TextField visibleTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView eyeImageView;
    private boolean passwordVisible = false;

    @FXML
    private void initialize() {

        visibleTextField = new TextField();
        visibleTextField.setPromptText("Password");
        visibleTextField.setVisible(false);
        visibleTextField.setPrefWidth(0);
        passwordContainer.getChildren().add(visibleTextField);

        updateEyeImage();
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

            User currentUser = User.makeInstance(userInfo.get(0), userInfo.get(1), userInfo.get(2), userInfo.get(3), userInfo.get(7), userInfo.get(4), userInfo.get(6), userInfo.get(5), Integer.parseInt(userInfo.get(8)));
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
            visibleTextField.setPrefWidth(TextField.USE_COMPUTED_SIZE);
            passwordTextField.setPrefWidth(0);
        } else {
            passwordTextField.setText(visibleTextField.getText());
            passwordTextField.setVisible(true);
            visibleTextField.setVisible(false);
            visibleTextField.setPrefWidth(0);
            passwordTextField.setPrefWidth(TextField.USE_COMPUTED_SIZE);
        }

        updateEyeImage();
    }


    private void updateEyeImage() {
        String imagePath = passwordVisible ? "static/images/eye.png" : "static/images/closedeye.png";
        Image image = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource(imagePath).toExternalForm()));
        eyeImageView.setImage(image);
    }

}

