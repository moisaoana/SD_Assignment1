package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.model.Warning;
import sample.service.TravellingAgencyService;


public class NewPackageController {
    public  ObservableList<String> mapList;
    private Main main;
    private Scene agencyScene;
    TravellingAgencyService travellingAgencyService=new TravellingAgencyService();
    public void setMain(Main main){
        this.main = main;
    }
    public void setAgencyScene(Scene scene){
        this.agencyScene=scene;
    }

    @FXML
    private Label titleLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label startDateLabel;

    @FXML
    private Label endDateLabel;

    @FXML
    private Label detailsLabel;

    @FXML
    private Label capacityLabel;

    @FXML
    private Label destLabel;

    @FXML
    private TextField nameTextfield;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField startDayTextfield;

    @FXML
    private TextField startMonthTextfield;

    @FXML
    private TextField startYearTextfield;

    @FXML
    private TextField endDayTextfield;

    @FXML
    private TextField endMonthTextfield;

    @FXML
    private TextField endYearTextfield;

    @FXML
    private TextField detailsTextfield;

    @FXML
    private TextField capacityTextfield;

    @FXML
    private Button addButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button backButton;


    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Label warningLabel;

    @FXML
    void clickAdd(ActionEvent event) {
        warningLabel.setVisible(false);
        String name=nameTextfield.getText();
        String price=priceTextField.getText();
        String destination=comboBox.getValue();
        String startDay=startDayTextfield.getText();
        String startMonth=startMonthTextfield.getText();
        String startYear=startYearTextfield.getText();
        String endDay=endDayTextfield.getText();
        String endMonth=endMonthTextfield.getText();
        String endYear=endYearTextfield.getText();
        String details=detailsTextfield.getText();
        String capacity=capacityTextfield.getText();
        Warning result=travellingAgencyService.addNewPackage(destination,name,price,startDay,startMonth,startYear,endDay,endMonth,endYear,details,capacity);
        System.out.println(result);
        switch(result){
            case EMPTY_FIELDS: {
                warningLabel.setText("Empty fields!");
                warningLabel.setVisible(true);
                break;
            }
            case INVALID_PRICE:{
                warningLabel.setText("Invalid price!");
                warningLabel.setVisible(true);
                break;
            }
            case INVALID_DATE:{
                warningLabel.setText("Invalid date!");
                warningLabel.setVisible(true);
                break;
            }
            case INVALID_YEAR:{
                warningLabel.setText("Invalid year!");
                warningLabel.setVisible(true);
                break;
            }
            case INVALID_MONTH:{
                warningLabel.setText("Invalid month!");
                warningLabel.setVisible(true);
                break;
            }
            case INVALID_DAY:{
                warningLabel.setText("Invalid day!");
                warningLabel.setVisible(true);
                break;
            }
            case INVALID_CAPACITY:{
                warningLabel.setText("Invalid capacity!");
                warningLabel.setVisible(true);
                break;
            }
            case DUPLICATE:{
                warningLabel.setText("Package already exists!");
                warningLabel.setVisible(true);
                break;
            }
            case SUCCESS:{
               clearAllFields();
               warningLabel.setVisible(false);
               main.setScene(agencyScene);
               break;
            }
        }
    }

    @FXML
    void clickBack(ActionEvent event) {
        main.setScene(agencyScene);
    }

    @FXML
    void clickClear(ActionEvent event) {
        clearAllFields();

    }
    private void clearAllFields(){
        nameTextfield.clear();
        priceTextField.clear();
        startDayTextfield.clear();
        startMonthTextfield.clear();
        startYearTextfield.clear();
        endDayTextfield.clear();
        endMonthTextfield.clear();
        endYearTextfield.clear();
        detailsTextfield.clear();
        capacityTextfield.clear();
    }

    public  void setComboBox(){
        mapList= FXCollections.observableArrayList(travellingAgencyService.getAllDestinations());
        comboBox.setValue(mapList.get(0));
        comboBox.setItems(mapList);
    }

    @FXML
    void clickComboBox(MouseEvent event) {
        setComboBox();
    }

}
