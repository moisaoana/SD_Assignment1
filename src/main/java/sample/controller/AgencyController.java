package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.Main;
import sample.model.Warning;
import sample.service.TravellingAgencyService;

public class AgencyController {
    private Main main;
    private TravellingAgencyService travellingAgencyService=new TravellingAgencyService();
    public void setMain(Main main){
        this.main = main;
    }
    @FXML
    private Label titleLabel;

    @FXML
    private TextField addDestinationTextfield;

    @FXML
    private Label addDestinationLabel;

    @FXML
    private Button addDestinationButton;

    @FXML
    private Label warningLabel;

    @FXML
    void clickAddDestination(ActionEvent event) {
        warningLabel.setVisible(false);
        String name= addDestinationTextfield.getText();
        Warning result= travellingAgencyService.addNewDestination(name);
        if(result==Warning.EMPTY_FIELDS){
            warningLabel.setText("Empty field!");
            warningLabel.setVisible(true);
        }else if (result==Warning.DUPLICATE){
            warningLabel.setText("Destination already exists!");
            warningLabel.setVisible(true);
        }else{
            travellingAgencyService.addNewDestination(name);
            addDestinationTextfield.clear();
        }
    }

}