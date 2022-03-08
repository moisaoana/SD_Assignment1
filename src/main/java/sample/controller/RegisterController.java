package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.Main;

public class RegisterController {
    private Main main;
    private Scene startScene;
    public void setMain(Main main){
        this.main = main;
    }
    public void setStartScene(Scene scene){
        this.startScene=scene;
    }

    @FXML
    private TextField usernameTextfield;

    @FXML
    private TextField passwordTextfield;

    @FXML
    private Label titleLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button registerButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button backButton;
    @FXML
    private Label usernameExistsLabel;

    @FXML
    private Label emptyFieldsLabel;

    @FXML
    void clickBack(ActionEvent event) {
        disableWarnings();
        main.setScene(startScene);
    }

    @FXML
    void clickClear(ActionEvent event) {
        usernameTextfield.clear();
        passwordTextfield.clear();
        disableWarnings();
    }

    @FXML
    void clickRegister(ActionEvent event) {

    }
    private void disableWarnings(){
        usernameExistsLabel.setVisible(false);
        emptyFieldsLabel.setVisible(false);
    }
}
