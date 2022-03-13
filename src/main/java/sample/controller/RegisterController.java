package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.model.Warning;
import sample.service.UserService;

public class RegisterController {
    private Main main;
    private Scene startScene;
    public void setMain(Main main){
        this.main = main;
    }
    public void setStartScene(Scene scene){
        this.startScene=scene;
    }
    private String username,password,firstName,lastName;
    private UserService userService=new UserService();

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
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private TextField firstNameTextfield;

    @FXML
    private TextField lastNameTextfield;

    @FXML
    void clickBack(ActionEvent event) {
        clearTextFields();
        disableWarnings();
        main.setScene(startScene);
    }

    @FXML
    void clickClear(ActionEvent event) {
        clearTextFields();
        disableWarnings();
    }

    @FXML
    void clickRegister(ActionEvent event) {
        disableWarnings();
        username=usernameTextfield.getText();
        password=passwordTextfield.getText();
        firstName=firstNameTextfield.getText();
        lastName=lastNameTextfield.getText();
        Warning result=userService.registerUser(username,password,firstName,lastName);
        if(result==Warning.DUPLICATE){
            usernameExistsLabel.setVisible(true);
        }else if(result==Warning.EMPTY_FIELDS){
            emptyFieldsLabel.setVisible(true);
        }else{
            disableWarnings();
            clearTextFields();
            main.setScene(startScene);
        }

    }
    private void disableWarnings(){
        usernameExistsLabel.setVisible(false);
        emptyFieldsLabel.setVisible(false);
    }
    private void clearTextFields(){
        usernameTextfield.clear();
        passwordTextfield.clear();
        firstNameTextfield.clear();
        lastNameTextfield.clear();
    }
}
