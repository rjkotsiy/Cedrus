package com.cedrus.main.controller;

import com.cedrus.db.DataBaseManager;
import com.cedrus.models.Customer;
import com.cedrus.ui.resources.ResourceManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, String> customerName;

    @FXML
    private TableColumn<Customer, String> phone;

    @FXML
    private TableColumn<Customer, String> lastExamitationDate;

    @FXML
    private TableColumn<Customer, String> nextExaminationDate;

    @FXML
    private TableColumn<Customer, ImageView> customerMenu;

    private DataBaseManager dbManager = new DataBaseManager();

    private ObservableList<Customer> customers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(location);
    }


    public void setup() {
        dbManager = new DataBaseManager();
        dbManager.connect();
        createTableCellValueFactorys();
        customers = FXCollections.observableArrayList(dbManager.getCustomerList());
        customersTable.setItems(customers);
        customersTable.refresh();
    }

    private void createTableCellValueFactorys() {
        customerName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        phone.setCellValueFactory(cellData -> cellData.getValue().getPhoneProperty());
//        lastExamitationDate.setCellValueFactory(cellData -> cellData.getValue().getManagerProperty());
//        nextExaminationDate.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        customerMenu.setCellValueFactory(cellData -> new SimpleObjectProperty<ImageView>(new ImageView(ResourceManager.EDIT_PERIOD)));
    }


}
