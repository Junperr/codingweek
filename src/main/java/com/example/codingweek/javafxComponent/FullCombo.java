package com.example.codingweek.javafxComponent;

import com.example.codingweek.database.DataBase;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

public class FullCombo extends ComboBox<String> implements ChangeListener<String> {
    public FullCombo() {
        super();
        this.setEditable(true);
        this.getEditor().textProperty().addListener(this);

    }

    @Override
    @SuppressWarnings("unchecked")
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        DataBase db = DataBase.getInstance();
        ArrayList<ArrayList<Object>> objectList = db.fetchAll("Select category from Categories where category like ?", newValue + "%");
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