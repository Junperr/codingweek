package com.example.codingweek.controller;

import com.example.codingweek.Main;
import com.example.codingweek.data.Offer;
import com.example.codingweek.data.Order;
import com.example.codingweek.data.User;
import com.example.codingweek.facade.BigFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;

public class ProfileForOthersController {
    @FXML
    public Label userName, zip, city, marks, profileHeader;

    public VBox orderToAddOther;
    public VBox offerToAddOther;


    public void initUserPageForOthers(User user){
        this.profileHeader.setText(user.userName + "'s profile");
        this.userName.setText(user.userName);
        this.city.setText(user.city);
        this.zip.setText(user.zipCode);
        System.out.println(user.userName +"'s reviews: " + user.avgEval);
        if (user.avgEval.equals(-1)){this.marks.setText("No reviews");}
        else this.marks.setText(user.avgEval.toString());

        BigFacade bf = new BigFacade();
        ArrayList<Offer> listOffer = bf.getOwnOffers(userName.getText());
        ArrayList<Order> listOrder = bf.getOwnOrdersOther(userName.getText());
        for (Offer offer : listOffer) {
            loadOffers(offer);
        }

        for (Order order : listOrder) {
            loadOrders(order);
        }
    }

    public void loadOrders(Order order){
        //orders by visitor to user page
        try {
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/orderView.fxml");
            FXMLLoader loader = new FXMLLoader(xmlUrl);
            loader.setLocation(xmlUrl);
            VBox orderVBox = loader.load();
            OrderViewController orderViewController = loader.getController();
            orderViewController.initOrderView(order);
            orderToAddOther.getChildren().add(orderVBox);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadOffers(Offer offer){
        //orders by visitor to user page
        try {
            URL xmlUrl = Main.class.getClassLoader().getResource("static/fxml/offerView.fxml");
            FXMLLoader loader = new FXMLLoader(xmlUrl);
            loader.setLocation(xmlUrl);
            VBox offerVBox = loader.load();
            OfferViewController offerViewController = loader.getController();
            offerViewController.initOfferView(offer);
            offerToAddOther.getChildren().add(offerVBox);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
