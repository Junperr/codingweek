package com.example.codingweek21.controller;

import com.example.codingweek21.javafxComponent.ComboPanel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewOfferController {



    @FXML
    private TextField titleTextField, descriptionTextArea, priceTextField;

    @FXML
    private ChoiceBox<String> type;

    @FXML
    private ComboPanel themeComboPanel;
    @FXML
    private Button newOfferButton, newOfferToOffersButton;



}
