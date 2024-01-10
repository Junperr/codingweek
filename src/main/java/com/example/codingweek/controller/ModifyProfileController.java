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
import java.util.Objects;


public class ModifyProfileController {
    @FXML
    private Button saveButton;
    @FXML
    private TextField newWhat;
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

    public void initData(String toChange) {
        this.index = toChange;
        newWhat.setPromptText("New " + toChange);
    }

    public void setStage(Stage stage) {this.stage = stage;}

    public void setMainController(MyProfileController myProfileController) {this.myProfileController = myProfileController;}

    @FXML
    private void submit() throws IOException {
        errorLabel.setText("");

        String newData = (newWhat.getText() != null && !newWhat.getText().isEmpty()) ? newWhat.getText(): handleEmptyField("newData");
        String currentPass = (currentPW.getText() != null && !currentPW.getText().isEmpty()) ? currentPW.getText(): handleEmptyField("currentPass");

        if (!errorLabel.getText().isEmpty()) {
            return;
        }

        User currentUser = User.getInstance();
        DataBase db = DataBase.getInstance();

        if (currentUser.password.equals(currentPass)) {
            switch (index) {
                case "username":
                    ArrayList<String> alHere = db.fetchUser("select * from Users where userName=?", currentUser.userName);

                    if (alHere == null) {
                        db.exec("update Users set userName=? where userName=?", newData, currentUser.userName);
                        currentUser.userName = newData;
                    } else {
                        errorLabel.setText("This username is already used, try another one");
                    }
                    break;
                case "password":
                    currentUser.password = newData;
                    db.exec("update Users set password=? where userName=?", newData, currentUser.userName);
                    break;
                case "last name":
                    currentUser.lastName = newData;
                    db.exec("update Users set lastName=? where userName=?", newData, currentUser.userName);
                    break;
                case "first name":
                    currentUser.firstName = newData;
                    db.exec("update Users set firstName=? where userName=?", newData, currentUser.userName);
                    break;
                case "email":
                    currentUser.email = newData;
                    db.exec("update Users set email=? where userName=?", newData, currentUser.userName);
                    break;
                case "address":
                    currentUser.address = newData;
                    db.exec("update Users set address=? where userName=?", newData, currentUser.userName);
                    break;
                case "zipcode":
                    currentUser.zipCode = newData;
                    db.exec("update Users set zipCode=? where userName=?", newData, currentUser.userName);
                    break;
                case "city":
                    currentUser.city = newData;
                    db.exec("update Users set city=? where userName=?", newData, currentUser.userName);
                    break;
            }

            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/valid.fxml");
            System.out.println(xmlUrl);
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            Stage modification = (Stage) saveButton.getScene().getWindow();
            modification.setScene(new Scene(root));
        } else {
            errorLabel.setText("Wrong current password");
        }
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
