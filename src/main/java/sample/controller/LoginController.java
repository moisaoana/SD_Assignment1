package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Main;
import sample.model.User;
import sample.model.Warning;
import sample.service.UserService;

public class LoginController {
    private Main main;
    private Scene startScene;
    private Scene userProfileScene;
    private String username,password;
    private UserService userService=new UserService();
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
    private PasswordField passwordTextfield;

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
    void clickLogin(ActionEvent event) {
        disableWarnings();
        this.username=usernameTextfield.getText();
        this.password=passwordTextfield.getText();
        Warning result=userService.loginUser(this.username,this.password);
        User user=userService.findUserByName(username);
        if(result==Warning.NOT_FOUND){
            incorrectUsernameLabel.setVisible(true);
        }else if(result==Warning.WRONG_PASS){
            incorrectPasswordLabel.setVisible(true);
        }else if(result==Warning.EMPTY_FIELDS){
            emptyFieldsLabel.setVisible(true);
        }else{
            UserProfileController userProfileController=main.getUserProfileController();
            userProfileController.setUsernameLabel(username);
            userProfileController.setUser(user);
            userProfileController.getBookedObservableList().addAll(user.getPackages());
            userProfileController.init1();
            clearTextFields();
            main.setScene(userProfileScene);
        }

    }
    private void disableWarnings(){
        incorrectUsernameLabel.setVisible(false);
        incorrectPasswordLabel.setVisible(false);
        emptyFieldsLabel.setVisible(false);
    }
    private void clearTextFields(){
        usernameTextfield.clear();
        passwordTextfield.clear();
    }
}
