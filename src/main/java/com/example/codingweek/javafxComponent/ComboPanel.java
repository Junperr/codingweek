package com.example.codingweek.javafxComponent;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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

    public String getSelectedTheme() {
        return comboBox.getSelectionModel().getSelectedItem();
    }

    public void resetComboPanel() {
        resetComboBox();
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
            private final Label deleteLabel = new Label("✖");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    HBox hbox = new HBox(new Label(item), deleteLabel);
                    setGraphic(hbox);

                    deleteLabel.setOnMouseClicked(this::handleDeleteLabelClick);
                }
            }

            private void handleDeleteLabelClick(MouseEvent event) {
                String item = getItem();
                if (item != null) {
                    getListView().getItems().remove(item);
                    resetComboBox();
                }
            }
        });
    }

    private void initLayout() {
        HBox hbox = new HBox(comboBox, selectedOptionsListView);
        getChildren().add(hbox);
    }
}
