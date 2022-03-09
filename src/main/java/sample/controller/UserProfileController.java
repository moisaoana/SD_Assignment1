package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import sample.Main;

public class UserProfileController {
    private Main main;
    @FXML
    private Label usernameLabel;

    @FXML
    private Label profileLabel;

    public void setMain(Main main){
        this.main = main;
    }
    public void setUsernameLabel(String text){
        usernameLabel.setText(text);
    }

}
