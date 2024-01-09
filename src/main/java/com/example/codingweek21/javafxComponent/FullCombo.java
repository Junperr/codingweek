package com.example.codingweek21.javafxComponent;

import com.example.codingweek21.database.DataBase;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class FullCombo extends ComboBox<String> implements ChangeListener<String> {
    public FullCombo() {
        super();
        this.setEditable(true);
        this.getEditor().textProperty().addListener(this);

    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        DataBase db = DataBase.getInstance();
        List<List<Object>> objectList = db.fetchAll("Select category from Categories where category like ?", newValue + "%");
//        DataBase.getInstance().printData(objectList);
        ObservableList<String> categories = FXCollections.observableArrayList();
        for (List<Object> object : objectList) {
            categories.add((String) object.get(0));
        }
        Platform.runLater(() -> {
            if (!categories.isEmpty()) {
                this.setItems(categories);
                show();
            }
        });

    }
}