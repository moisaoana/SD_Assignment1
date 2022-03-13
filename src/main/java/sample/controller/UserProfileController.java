package sample.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import sample.Main;
import sample.model.Package;
import sample.model.Status;
import sample.model.User;
import sample.service.TravellingAgencyService;

import java.time.LocalDate;
import java.util.Map;

public class UserProfileController {
    private ObservableList<Package> packageObservableList;
    private ObservableList<Package> bookedObservableList=FXCollections.observableArrayList();
    private Main main;
    private TravellingAgencyService travellingAgencyService=new TravellingAgencyService();
    private User user;
    private Scene startPage;
    public void setStartScene(Scene scene){
        this.startPage=scene;
    }
    @FXML
    private Label usernameLabel;

    @FXML
    private Label profileLabel;

    public void setMain(Main main){
        this.main = main;
        init();
    }
    public void setUser(User user){
        this.user=user;
    }
    public void setUsernameLabel(String text){
        usernameLabel.setText(text);
    }

    @FXML
    private Label availablePackagesLabel;

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
    private TextField destTF;

    @FXML
    private TextField priceTF;

    @FXML
    private TextField startDateTF;

    @FXML
    private TextField endDateTF;

    @FXML
    private Label bookedPackagesLabel;

    @FXML
    private TableView<Package> bookTableView;

    @FXML
    private TableColumn<Package, String> nameColumnB;

    @FXML
    private TableColumn<Package, String> destinationColumnB;

    @FXML
    private TableColumn<Package, Double> priceColumnB;

    @FXML
    private TableColumn<Package, String> detailsColumnB;

    @FXML
    private TableColumn<Package, Integer> maxCapacityColumnB;

    @FXML
    private TableColumn<Package, Integer> currCapacityColumnB;

    @FXML
    private TableColumn<Package, Status> statusColumnB;

    @FXML
    private TableColumn<Package, LocalDate> startDateColumnB;

    @FXML
    private TableColumn<Package, LocalDate> endDateColumnB;

    @FXML
    private Button backButton;

    @FXML
    void clickBack(ActionEvent event) {
        destTF.clear();
        priceTF.clear();
        startDateTF.clear();
        endDateTF.clear();
        bookedObservableList.clear();
        main.setScene(startPage);
    }
/*
    void init1(){
        packageObservableList=travellingAgencyService.getAvailablePackages();
        AgencyController.addDataToTable(nameColumn,destinationColumn,priceColumn,detailsColumn,maxCapacityColumn,currCapacityColumn,statusColumn,startDateColumn,endDateColumn,packageTableView,packageObservableList);
        AgencyController.addDataToTable(nameColumnB,destinationColumnB,priceColumnB,detailsColumnB,maxCapacityColumnB,currCapacityColumnB,statusColumnB,startDateColumnB,endDateColumnB,bookTableView,bookedObservableList);
        //addButtonBook(packageTableView,bookedObservableList);
        filterTable();
        packageTableView.refresh();
    }
*/
@FXML
void init1(){
    packageObservableList=travellingAgencyService.getAvailablePackages();
    AgencyController.addDataToTable(nameColumn,destinationColumn,priceColumn,detailsColumn,maxCapacityColumn,currCapacityColumn,statusColumn,startDateColumn,endDateColumn,packageTableView,packageObservableList);
    AgencyController.addDataToTable(nameColumnB,destinationColumnB,priceColumnB,detailsColumnB,maxCapacityColumnB,currCapacityColumnB,statusColumnB,startDateColumnB,endDateColumnB,bookTableView,bookedObservableList);
    //addButtonBook(packageTableView,bookedObservableList);
    filterTable();
    packageTableView.refresh();

}
    @FXML
    void init(){
        packageObservableList=travellingAgencyService.getAvailablePackages();
        AgencyController.addDataToTable(nameColumn,destinationColumn,priceColumn,detailsColumn,maxCapacityColumn,currCapacityColumn,statusColumn,startDateColumn,endDateColumn,packageTableView,packageObservableList);
        AgencyController.addDataToTable(nameColumnB,destinationColumnB,priceColumnB,detailsColumnB,maxCapacityColumnB,currCapacityColumnB,statusColumnB,startDateColumnB,endDateColumnB,bookTableView,bookedObservableList);
        addButtonBook(packageTableView,bookedObservableList);
        filterTable();
        packageTableView.refresh();

    }
    public void addButtonBook(TableView<Package> tableView,ObservableList<Package>observableList)
    {
        TableColumn<Package, Void> buttons = new TableColumn<>("Book");
        buttons.setPrefWidth(60);
        Callback<TableColumn<Package, Void>, TableCell<Package, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Package, Void> call(final TableColumn<Package, Void> p) {
                return new TableCell<>() {
                    private final Button newButton = new Button("+");
                    {
                        AgencyController.styleButton(newButton);
                        newButton.setOnAction((ActionEvent event) -> {
                            Package packageToBeAdded = getTableView().getItems().get(getIndex());
                            boolean result=travellingAgencyService.bookPackage(user,packageToBeAdded);
                            if(result) {
                                observableList.add(packageToBeAdded);
                                bookTableView.setItems(observableList);
                                bookTableView.refresh();
                                packageObservableList = travellingAgencyService.getAvailablePackages();
                                packageTableView.setItems(packageObservableList);
                                packageTableView.refresh();
                            }
                            //init();

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
    public void filterTable(){

        FilteredList filteredList = new FilteredList<>(packageObservableList);
        filteredList.setPredicate(p -> true);

        packageTableView.itemsProperty().set(filteredList);
        showSearchedItems(destTF,packageObservableList,packageTableView);
        showSearchedItems(priceTF,packageObservableList,packageTableView);
        showSearchedItems(startDateTF,packageObservableList,packageTableView);
        showSearchedItems(endDateTF,packageObservableList,packageTableView);

    }
    public void showSearchedItems(TextField textField, ObservableList<Package> items, TableView<Package> table)
    {

        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                table.setItems(items);
            }
            String value = newValue.toLowerCase();
            ObservableList<Package> subentries = FXCollections.observableArrayList();
            long count = table.getColumns().stream().count();
            for (int i = 0; i < table.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + table.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(table.getItems().get(i));
                        break;
                    }
                }
            }
            table.setItems(subentries);
        });
    }

    public ObservableList<Package> getBookedObservableList() {
        return bookedObservableList;
    }
}

