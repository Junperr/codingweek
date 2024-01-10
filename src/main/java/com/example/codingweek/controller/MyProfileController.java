package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.auth.User;
import com.example.codingweek.database.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

public class MyProfileController implements Initializable {
    @FXML
    private VBox offerToAdd, orderToAdd, offers, orders;
    @FXML
    private HBox menuBar;
    @FXML
    private Button modifyUserName, modifyPassword, modifyLastName, modifyFirstName, modifyEmail, modifyAddress, modifyZip, modifyCity;
    @FXML
    private Label userNameBig, balance, userName, password, lastName, firstName, email, address, zip, city;

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
        URL xmlUrl = getClass().getClassLoader().getResource("static/fxml/modifyProfile.fxml");
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

    @FXML
    private void setZip() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getClassLoader().getResource("static/fxml/modifyProfile.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setTitle("Change zipcode");
        modification.setScene(new Scene(root));

        ModifyProfileController modifyProfileController = loader.getController();
        modifyProfileController.initData("zipcode");
        modifyProfileController.setStage(modification);
        modifyProfileController.setMainController(this);
        modification.showAndWait();
    }

    @FXML
    private void setCity() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getClassLoader().getResource("static/fxml/modifyProfile.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage modification = new Stage();
        modification.setTitle("Change city");
        modification.setScene(new Scene(root));

        ModifyProfileController modifyProfileController = loader.getController();
        modifyProfileController.initData("city");
        modifyProfileController.setStage(modification);
        modifyProfileController.setMainController(this);
        modification.showAndWait();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
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

        offers.getChildren().clear();

        DataBase db = DataBase.getInstance();
        ArrayList<ArrayList<Object>> listOffer = db.fetchAll("select title, description, imagePath, price, user, id from Offers where user=?", currentUser.userName);
        ArrayList<ArrayList<Object>> listOrder = db.fetchAll("select id, cost, seller from Orders where buyer=?", currentUser.userName);

        for (ArrayList<Object> o : listOffer) {
            String imagePath = (o.get(2) != null) ? o.get(2).toString() : "default.png";

            loadOffersFromDatabase(
                    o.get(0).toString(),   // title
                    o.get(1).toString(),   // description
                    imagePath,             // imagePath
                    o.get(3).toString(),   // price
                    o.get(4).toString(),    // user
                    o.get(5).toString()
            );

        }

        for (ArrayList<Object> o : listOrder) {
            loadOrdersFromDatabase(UUID.fromString((String) o.get(0)), Integer.parseInt(o.get(1).toString()), o.get(2).toString());
        }
    }

    public void loadOffersFromDatabase(String title, String description, String imagePath, String price, String user, String id) {
        try {
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/offerView.fxml");
            FXMLLoader loader = new FXMLLoader(xmlUrl);
            loader.setLocation(xmlUrl);
            VBox offer = loader.load();


            OfferViewController offerViewController = loader.getController();
            offerViewController.initOfferView(title, description, imagePath, price, user, id);
            offers.getChildren().add(offer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadOrdersFromDatabase(UUID orderId, Integer cost, String seller) {
        try {
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/orderView.fxml");
            FXMLLoader loader = new FXMLLoader(xmlUrl);
            loader.setLocation(xmlUrl);
            VBox order = loader.load();


            OrderViewController orderViewController = loader.getController();
            orderViewController.initOrderView(orderId, cost, seller);
            orders.getChildren().add(order);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
