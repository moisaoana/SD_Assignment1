package sample.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import sample.Main;
import sample.model.Destination;
import sample.model.Package;
import sample.model.Status;
import sample.model.Warning;
import sample.service.TravellingAgencyService;


import java.time.LocalDate;


public class AgencyController {
    public ObservableList<Package> packageObservableList;
    private Main main;
    private Scene newPackageScene;
    private Scene startScene;
    private TravellingAgencyService travellingAgencyService=new TravellingAgencyService();
    private  NewPackageController newPackageController;
    public void setStartScene(Scene scene){
        this.startScene=scene;
    }
    public void setNewPackageController(NewPackageController controller){
        this.newPackageController=controller;
    }
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
    public TableView<Package> packageTableView;

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
    private Label deleteLocationLabel;

    @FXML
    private ComboBox<String> destCombobox;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    public  ObservableList<String> mapList;

    public static void addDataToTable(TableColumn<Package, String> nameColumn,TableColumn<Package, String> destinationColumn,TableColumn<Package, Double> priceColumn,TableColumn<Package, String> detailsColumn,TableColumn<Package, Integer> maxCapacityColumn,TableColumn<Package, Integer> currCapacityColumn,TableColumn<Package, Status> statusColumn,TableColumn<Package, LocalDate> startDateColumn,TableColumn<Package, LocalDate> endDateColumn,TableView<Package> packageTableView,ObservableList<Package> packageObservableList){
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
       addDataToTable(nameColumn,destinationColumn,priceColumn,detailsColumn,maxCapacityColumn,currCapacityColumn,statusColumn,startDateColumn,endDateColumn,packageTableView,packageObservableList);
    }

    @FXML
    void init(){
        packageObservableList=travellingAgencyService.getAllPackages();
        populateTable();
        addButtonsRemove(packageTableView,packageObservableList);
        addButtonsEdit(packageTableView,packageObservableList);
    }
    void init1(){
        packageObservableList=travellingAgencyService.getAllPackages();
        packageTableView.setItems(packageObservableList);
        packageTableView.refresh();
        //populateTable();
        //addButtonsRemove(packageTableView,packageObservableList);
        //addButtonsEdit(packageTableView,packageObservableList);
    }
    public void addButtonsRemove(TableView<Package> tableView,ObservableList<Package>observableList)
    {
        TableColumn<Package, Void> buttons = new TableColumn<>("Remove");
        buttons.setPrefWidth(60);
        Callback<TableColumn<Package, Void>, TableCell<Package, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Package, Void> call(final TableColumn<Package, Void> p) {
                return new TableCell<>() {
                    private final Button newButton = new Button("-");
                    {
                        styleButton(newButton);
                        newButton.setOnAction((ActionEvent event) -> {
                            Package packageToBeDeleted = getTableView().getItems().get(getIndex());
                            packageObservableList.remove(packageToBeDeleted);
                            packageTableView.setItems(packageObservableList);
                            packageTableView.refresh();
                            travellingAgencyService.deletePackage(packageToBeDeleted.getId());
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(newButton);
                        }
                    }
                };
            }
        };
        buttons.setCellFactory(cellFactory);
        tableView.getColumns().add(buttons);
    }

    public void addButtonsEdit(TableView<Package> tableView,ObservableList<Package>observableList)
    {
        TableColumn<Package, Void> buttons = new TableColumn<>("Edit");
        buttons.setPrefWidth(60);
        Callback<TableColumn<Package, Void>, TableCell<Package, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Package, Void> call(final TableColumn<Package, Void> p) {
                return new TableCell<>() {
                    private final Button newButton = new Button("E");
                    {
                        styleButton(newButton);
                        newButton.setOnAction((ActionEvent event) -> {
                            Package packageToBeEdited = getTableView().getItems().get(getIndex());
                            newPackageController.editPackage(packageToBeEdited);
                            newPackageController.setPackageToBeEdited(packageToBeEdited);
                            newPackageController.getAddButton().setDisable(true);
                            newPackageController.getEditButton().setDisable(false);
                            main.setScene(newPackageScene);

                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(newButton);
                        }
                    }
                };
            }
        };
        buttons.setCellFactory(cellFactory);
        tableView.getColumns().add(buttons);
    }


    public static void styleButton(Button button){
        button.setStyle("-fx-text-fill: #ffffff;-fx-background-color:  LIGHTSEAGREEN");
    }

    public  void updateTable(){
      packageObservableList=travellingAgencyService.getAllPackages();
      packageTableView.setItems(packageObservableList);
      packageTableView.refresh();
    }
    @FXML
    void clickBack(ActionEvent event) {
        clearFields();
        main.setScene(startScene);
    }

    @FXML
    void clickComboBox(MouseEvent event) {
        setComboBox();
    }

    @FXML
    void clickDeleteDest(ActionEvent event) {
        String destination=destCombobox.getValue();
        travellingAgencyService.deleteDestination(destination);
        updateTable();
    }
    public  void setComboBox(){
        mapList= FXCollections.observableArrayList(travellingAgencyService.getAllDestinations());
        destCombobox.setValue(mapList.get(0));
        destCombobox.setItems(mapList);
    }
    private void clearFields(){
        addDestinationTextfield.clear();
        warningLabel.setVisible(false);
    }

}
