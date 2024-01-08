package com.example.codingweek21;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;

public class LayoutController {

    public Menu myProfile;
    public Label offers;
    public MenuBar menuBar;
    public Label logout;


    public void goToMyProfilePage(MouseEvent actionEvent) {
        System.out.println("profile");
    }

    public void goToOffers(MouseEvent actionEvent) {
        System.out.println("offers");
    }

    public void logout(MouseEvent actionEvent) {
        System.out.println("logout");
    }
}
