package com.example.codingweek21.javafxComponent;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class ComboPanel extends StackPane {

    private FullCombo comboBox;
    private ListView<String> selectedOptionsListView;

    public ComboPanel() {
        initComboBox();
        initSelectedOptionsListView();
        initLayout();
    }

    private void initComboBox() {
        comboBox = new FullCombo();

        comboBox.setOnAction(event -> {
            String selectedValue = comboBox.getSelectionModel().getSelectedItem();
            if (selectedValue == null || selectedValue.length() < 3) {
                return;
            }
            // Use Platform.runLater to modify the ListView items on the JavaFX Application Thread
            Platform.runLater(() -> {
                if (!selectedOptionsListView.getItems().contains(selectedValue)) {
                    selectedOptionsListView.getItems().add(selectedValue);
                    resetComboBox();
                }
            });
        });
    }

    private void resetComboBox() {
        comboBox.getEditor().clear();
        comboBox.getSelectionModel().clearSelection();
        comboBox.hide();
    }

    private void initSelectedOptionsListView() {
        selectedOptionsListView = new ListView<>();
        selectedOptionsListView.setPrefHeight(50);
        selectedOptionsListView.setCellFactory(param -> new ListCell<String>() {
            private final Button deleteButton = new Button("✖");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    HBox hbox = new HBox(new Label(item), deleteButton);
                    setGraphic(hbox);

                    deleteButton.setOnAction(event -> {
                        getListView().getItems().remove(item);
                        resetComboBox();
                    });
                }
            }
        });
    }

    private void initLayout() {
        HBox hbox = new HBox(comboBox, selectedOptionsListView);
        getChildren().add(hbox);
    }
}
