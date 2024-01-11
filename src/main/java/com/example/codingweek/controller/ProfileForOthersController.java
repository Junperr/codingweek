package com.example.codingweek.controller;

import com.example.codingweek.data.User;
import javafx.scene.control.Label;

public class ProfileForOthersController {

    public Label userName;
    public Label email;
    public Label zip;
    public Label city;
    public Label marks;

    public void initUserPageForOthers(User user){
        this.userName.setText(user.userName);
        this.email.setText(user.email);
        this.city.setText(user.city);
        this.zip.setText(user.zipCode);
        this.marks.setText(user.averageMarks);
    }
}
