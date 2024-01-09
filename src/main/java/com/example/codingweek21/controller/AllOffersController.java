package com.example.codingweek21.controller;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class AllOffersController {
    public HBox menuBar;
    public ChoiceBox type;
    public ChoiceBox category;
    public ChoiceBox RadiusOrPostcode;
    public ScrollPane scrollPane;

    public void saveFilters(MouseEvent mouseEvent) {
        System.out.println("save");
    }
}
