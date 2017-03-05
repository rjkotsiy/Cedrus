package com.cedrus.main.controller;

import com.cedrus.db.DataBaseManager;
import com.cedrus.models.Customer;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataBaseManager dbManager = new DataBaseManager();
        dbManager.connect();
        List<Customer> customers = dbManager.getCustomerList();
    }
}
