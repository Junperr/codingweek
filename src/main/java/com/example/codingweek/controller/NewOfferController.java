package com.example.codingweek.controller;

import com.example.codingweek.javafxComponent.ComboPanel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewOfferController {

    public TextArea desc;
    @FXML
    private TextField titleTextField, priceTextField;

    @FXML
    private ChoiceBox<String> type;

    @FXML
    private ComboPanel themeComboPanel;

    @FXML
    private Button newOfferButton, newOfferToOffersButton;

    @FXML
    public void initialize() {
        // Initialize your controller here
        type.getItems().addAll("Type1", "Type2", "Type3"); // Add your types

        // Sample event handler for newOfferButton
        newOfferButton.setOnAction(event -> {
            // Retrieve values from the controls
            String title = titleTextField.getText();
            String description = desc.getText();
            String price = priceTextField.getText();
            String selectedType = type.getValue();
            String selectedTheme = themeComboPanel.getSelectedTheme();

            // Now you can use these values as needed
            System.out.println("Title: " + title);
            System.out.println("Description: " + description);
            System.out.println("Price: " + price);
            System.out.println("Selected Type: " + selectedType);
            System.out.println("Selected Theme: " + selectedTheme);
        });

        // Sample event handler for newOfferToOffersButton
        newOfferToOffersButton.setOnAction(event -> {
            // Handle the action for newOfferToOffersButton
        });
    }
}
