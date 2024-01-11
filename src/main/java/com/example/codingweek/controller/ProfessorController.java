package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.auth.User;
import com.example.codingweek.database.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class ProfessorController {
    @FXML
    private TextField markTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private PasswordField PW;
    @FXML
    private Label errorLabel;
    @FXML
    private Button saveButton;
    private UUID order_id;
    private Stage stage;
    private OrderViewController orderViewController;

    public void setStage(Stage stage) {this.stage = stage;}

    public void setMainController(OrderViewController orderViewController) {this.orderViewController = orderViewController;}


    void orderData(UUID order_id) {
        this.order_id = order_id;
    }

    @FXML
    private void submit() throws IOException {
        errorLabel.setText("");
        
        Integer mark = (markTextField.getText() != null && !markTextField.getText().isEmpty()) ? Integer.valueOf(markTextField.getText()) : handleEmptyField("mark", "int");
        String pw = (PW.getText() != null && !PW.getText().isEmpty()) ? PW.getText(): handleEmptyField("password");
        String description = descriptionTextArea.getText();
        UUID id = UUID.randomUUID();

        if (!errorLabel.getText().isEmpty()) {
            return;
        }

        if (mark > 5) {
            errorLabel.setText("The mark must be between 0 and 5");
            return;
        }

        if (description.length() > 500) {
            errorLabel.setText("Description is tooo long ");
            return;
        }

        User currentUser = User.getInstance();
        DataBase db = DataBase.getInstance();

        if (currentUser.password.equals(pw)) {
            db.exec("insert into Marks (id , mark , order, description) values (?,?,?,?)", id, mark, order_id, description);

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
}
