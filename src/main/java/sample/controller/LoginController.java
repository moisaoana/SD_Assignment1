package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.Main;

public class LoginController {
    private Main main;
    private Scene startScene;
    private Scene userProfileScene;
    public void setMain(Main main){
        this.main = main;
    }
    public void setStartScene(Scene scene){
        this.startScene=scene;
    }
    public void setUserProfileScene(Scene scene){
        this.userProfileScene=scene;
    }
    @FXML
    private Label titleLabel;

    @FXML
    private TextField usernameTextfield;

    @FXML
    private TextField passwordTextfield;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button backButton;
    @FXML
    private Label incorrectUsernameLabel;

    @FXML
    private Label incorrectPasswordLabel;

    @FXML
    private Label emptyFieldsLabel;

    @FXML
    void clickBack(ActionEvent event) {
        main.setScene(startScene);
        disableWarnings();
    }

    @FXML
    void clickClear(ActionEvent event) {
        usernameTextfield.clear();
        passwordTextfield.clear();
        disableWarnings();
    }

    @FXML
    void clickLogin(ActionEvent event) {
        main.setScene(userProfileScene);
    }
    private void disableWarnings(){
        incorrectUsernameLabel.setVisible(false);
        incorrectPasswordLabel.setVisible(false);
        emptyFieldsLabel.setVisible(false);
    }
}
