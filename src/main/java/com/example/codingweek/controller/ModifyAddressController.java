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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ModifyAddressController {
    @FXML
    private Button saveButton;
    @FXML
    private TextField newAddressTextField, newZipCodeTextField, newCityTextField;
    @FXML
    private PasswordField currentPW;
    @FXML
    private Label errorLabel;
    private Stage stage;
    private MyProfileController myProfileController;
    private String index;
    
    @FXML
    private void initialize() {
        currentPW.setOnKeyPressed(this::handleEnterKeyPress);
    }
    
    public void setStage(Stage stage) {this.stage = stage;}

    public void setMainController(MyProfileController myProfileController) {this.myProfileController = myProfileController;}


    private void submit() throws IOException {
        errorLabel.setText("");

        String newAddress = (newAddressTextField.getText() != null && !newAddressTextField.getText().isEmpty()) ? newAddressTextField.getText() : handleEmptyField("new address");
        String newZipCode = (newZipCodeTextField.getText() != null && !newZipCodeTextField.getText().isEmpty()) ? newZipCodeTextField.getText() : handleEmptyField("new zip code");
        String newCity = (newCityTextField.getText() != null && !newCityTextField.getText().isEmpty()) ? newCityTextField.getText() : handleEmptyField("new city");
        String currentPass = (currentPW.getText() != null && !currentPW.getText().isEmpty()) ? currentPW.getText() : handleEmptyField("currentPass");

        if (!errorLabel.getText().isEmpty()) {
            return;
        }

        User currentUser = User.getInstance();
        DataBase db = DataBase.getInstance();

        if (currentUser.password.equals(currentPass)) {
            if (newAddress.length() > 200) {
                errorLabel.setText("Your address must be less than 200 character long");
                return;
            }
            if (newCity.length() > 100) {
                errorLabel.setText("Your city name must be less than 200 character long");
                return;
            }
            if (newZipCode.length() == 5) {
                errorLabel.setText("Your zipcode must be of length 5");
                return;
            }

            currentUser.address = newAddress;
            currentUser.zipCode = newZipCode;
            currentUser.city = newCity;
            db.exec("update Users set address=?, zipCode=?, city=? where userName=?", newAddress, newZipCode, newCity, currentUser.userName);

        } else {
            errorLabel.setText("Wrong password");
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/valid.fxml");
        System.out.println(xmlUrl);
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Stage modification = (Stage) saveButton.getScene().getWindow();
        modification.setScene(new Scene(root));
    }

    private <T> T handleEmptyField(String fieldName) {
        return handleEmptyField(fieldName,"String");
    }
    
    private <T> T handleEmptyField(String fieldName, String type) {
        if (errorLabel.getText().isEmpty()){
            errorLabel.setText("Please fill all the fields, empty fields: " + fieldName);
        }else{
            errorLabel.setText(errorLabel.getText() + ", " + fieldName);
        }
        if (type.equals("String")){
            return (T) "";
        }else if (type.equals("int")){
            return (T)(Integer) 0;
        }
        return null;
    }

    private void handleEnterKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                submit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
