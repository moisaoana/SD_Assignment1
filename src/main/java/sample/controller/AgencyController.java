package sample.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Main;
import sample.model.Destination;
import sample.model.Package;
import sample.model.Status;
import sample.model.Warning;
import sample.service.TravellingAgencyService;

import java.time.LocalDate;

public class AgencyController {
    private ObservableList<Package> packageObservableList;
    private Main main;
    private Scene newPackageScene;
    private TravellingAgencyService travellingAgencyService=new TravellingAgencyService();
    public void setMain(Main main){
        this.main = main;
        init();
    }
    public void setNewPackageScene(Scene scene){
        this.newPackageScene=scene;
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
    private Label addPackageLabel;
    @FXML
    private Button addPackageButton;

    @FXML
    private TableView<Package> packageTableView;

    @FXML
    private TableColumn<Package, String> nameColumn;

    @FXML
    private TableColumn<Package, String> destinationColumn;

    @FXML
    private TableColumn<Package, Double> priceColumn;

    @FXML
    private TableColumn<Package, String> detailsColumn;

    @FXML
    private TableColumn<Package, Integer> maxCapacityColumn;

    @FXML
    private TableColumn<Package, Integer> currCapacityColumn;

    @FXML
    private TableColumn<Package, Status> statusColumn;
    @FXML
    private TableColumn<Package, LocalDate> startDateColumn;

    @FXML
    private TableColumn<Package, LocalDate> endDateColumn;


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
    @FXML
    void clickAddPackage(ActionEvent event) {
        warningLabel.setVisible(false);
        main.setScene(newPackageScene);
    }
    public void populateTable(){
        nameColumn.setCellValueFactory((new PropertyValueFactory<>("name")));
        destinationColumn.setCellValueFactory(c ->
                new ReadOnlyStringWrapper( String.valueOf( c.getValue().getDestination().getName() ) ) );
        priceColumn.setCellValueFactory((new PropertyValueFactory<>("price")));
        detailsColumn.setCellValueFactory((new PropertyValueFactory<>("details")));
        maxCapacityColumn.setCellValueFactory((new PropertyValueFactory<>("maxCapacity")));
        currCapacityColumn.setCellValueFactory((new PropertyValueFactory<>("currentCapacity")));
        statusColumn.setCellValueFactory((new PropertyValueFactory<>("status")));
        startDateColumn.setCellValueFactory((new PropertyValueFactory<>("startDate")));
        endDateColumn.setCellValueFactory((new PropertyValueFactory<>("endDate")));
        packageTableView.setItems(packageObservableList);
    }
    @FXML
    void init(){
        this.packageObservableList=travellingAgencyService.getAllPackages();
        populateTable();
    }

}
