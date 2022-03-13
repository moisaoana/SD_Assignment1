package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controller.*;
import sample.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.net.URL;

public class Main extends Application {
    Stage window;
    private UserProfileController userProfileController;
    private AgencyController agencyController;
    @Override
    public void start(Stage primaryStage) throws Exception{

        URL urlStartPage=new File("src/main/java/sample/controller/fxml/StartPage.fxml").toURI().toURL();
        URL urlLoginPage=new File("src/main/java/sample/controller/fxml/Login.fxml").toURI().toURL();
        URL urlRegisterPage=new File("src/main/java/sample/controller/fxml/Register.fxml").toURI().toURL();
        URL urlUserProfilePage=new File("src/main/java/sample/controller/fxml/UserProfile.fxml").toURI().toURL();
        URL urlAgency=new File("src/main/java/sample/controller/fxml/Agency.fxml").toURI().toURL();
        URL urlNewPackage=new File("src/main/java/sample/controller/fxml/NewPackage.fxml").toURI().toURL();

        window=primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(urlStartPage);
        Parent rootStartPage=loader.load();
        StartPageController startPageController = loader.getController();

        loader = new FXMLLoader();
        loader.setLocation(urlLoginPage);
        Parent rootLogin=loader.load();
        LoginController loginController = loader.getController();

        loader = new FXMLLoader();
        loader.setLocation(urlRegisterPage);
        Parent rootRegister=loader.load();
        RegisterController registerController = loader.getController();

        loader = new FXMLLoader();
        loader.setLocation(urlUserProfilePage);
        Parent rootUserProfile=loader.load();
         userProfileController = loader.getController();

        loader = new FXMLLoader();
        loader.setLocation(urlAgency);
        Parent rootAgency=loader.load();
        agencyController = loader.getController();

        loader = new FXMLLoader();
        loader.setLocation(urlNewPackage);
        Parent rootNewPackage=loader.load();
        NewPackageController newPackageController = loader.getController();

        Scene startScene = new Scene(rootStartPage, 650, 550);
        Scene loginScene = new Scene(rootLogin, 650, 550);
        Scene registerScene=new Scene(rootRegister,650,550);
        Scene userProfileScene=new Scene(rootUserProfile,1100,650);
        Scene agencyScene=new Scene(rootAgency,1100,550);
        Scene newPackageScene=new Scene(rootNewPackage,400,600);

        loginController.setMain(this);
        loginController.setStartScene(startScene);
        loginController.setUserProfileScene(userProfileScene);

        registerController.setMain(this);
        registerController.setStartScene(startScene);

        startPageController.setMain(this);
        startPageController.setLoginScene(loginScene);
        startPageController.setRegisterScene(registerScene);
        startPageController.setAgencyScene(agencyScene);

        userProfileController.setMain(this);
        userProfileController.setStartScene(startScene);

        newPackageController.setMain(this);
        newPackageController.setAgencyScene(agencyScene);

        agencyController.setMain(this);
        agencyController.setNewPackageScene(newPackageScene);
        agencyController.setStartScene(startScene);

        newPackageController.setAgencyController(agencyController);
        agencyController.setNewPackageController(newPackageController);

        window.setScene(startScene);
        window.setTitle("Travel Booker");
        window.show();

    }
    public void setScene(Scene scene){
        window.setScene(scene);
    }

    public UserProfileController getUserProfileController() {
        return userProfileController;
    }
    public AgencyController getAgencyController(){
        return agencyController;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
