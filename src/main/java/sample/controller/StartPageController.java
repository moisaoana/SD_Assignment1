package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.Main;

public class StartPageController {
    private Main main;
    private Scene loginScene;
    private Scene registerScene;
    private Scene agencyScene;
    public void setMain(Main main){
        this.main = main;
    }
    public void setLoginScene(Scene scene){
        this.loginScene = scene;
    }
    public void setRegisterScene(Scene scene){
        this.registerScene = scene;
    }
    public void setAgencyScene(Scene scene){
        this.agencyScene = scene;
    }
    @FXML
    private Label titleLabel2;
    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button agencyButton;

    @FXML
    private Label titleLabel;

    @FXML
    void clickAgency(ActionEvent event) {
        AgencyController agencyController=main.getAgencyController();
        agencyController.init1();
        main.setScene(agencyScene);
    }

    @FXML
    void clickLogin(ActionEvent event) {
        main.setScene(loginScene);
    }

    @FXML
    void clickRegister(ActionEvent event) {
        main.setScene(registerScene);
    }

}
