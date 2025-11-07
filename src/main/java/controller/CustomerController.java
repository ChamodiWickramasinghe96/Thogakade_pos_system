package controller;


import com.jfoenix.controls.JFXTextField;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import service.ServiceFactory;
import service.ServiceFactory1;
import service.custom.CustomerService;
import service.custom.impl.CustomerServiceImpl;
import util.ServiceEnum;
import util.serviceType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {


    public TableView tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public JFXTextField txtId;
    public JFXTextField txtAddress;
    public JFXTextField txtName;
    public JFXTextField txtSalary;

    public AnchorPane customerContext;

    Alert alert;
    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            boolean add = service.save(new Customer(txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())));


            alert=null;
            if (add){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Process Success !");
                alert.setContentText("Customer : "+txtName.getText()+" ( ID : "+txtId.getText()+" ) added successfully !");
            }else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fail");
                alert.setHeaderText("Process Failed !");
                alert.setContentText("There is an issue with adding customer : "+txtName.getText()+" !");
            }
            alert.show();


            clearFields();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            boolean delete = service.delete(txtId.getText());

            alert=null;
            if (delete){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Process Success !");
                alert.setContentText("Customer : "+txtName.getText()+" ( ID : "+txtId.getText()+" ) delete successfully !");
            }else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fail");
                alert.setHeaderText("Process Failed !");
                alert.setContentText("There is an issue with delete customer : "+txtName.getText()+" !");
            }
            alert.show();

            clearFields();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        try {
            Customer customer = service.search(txtId.getText());
            txtId.setText(customer.getId());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(customer.getSalary().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            boolean update = service.update(new Customer(txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())));

            alert=null;
            if (update){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Process Success !");
                alert.setContentText("Customer : "+txtName.getText()+" ( ID : "+txtId.getText()+" ) updated successfully !");
            }else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fail");
                alert.setHeaderText("Process Failed !");
                alert.setContentText("There is an issue with updating customer : "+txtName.getText()+" !");
            }
            alert.show();

            clearFields();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }
    
    CustomerService service = ServiceFactory.getInstance().getFactory(ServiceEnum.CUSTOMER);
    ObservableList<Customer> customerObservableList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        loadTable();
    }

    public void loadTable(){
        try {
            customerObservableList = FXCollections.observableArrayList(service.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tblCustomer.setItems(customerObservableList);
    }

    public void mousePressed(MouseEvent mouseEvent) {

    }

    public void selectRow(MouseEvent mouseEvent) {
        Customer selectedItem =(Customer) tblCustomer.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem);
    }

    public void btnBacktoHomeOnAction(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) customerContext .getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/dashRoot.fxml"))));
        stage.centerOnScreen();
    }

    public void btnReloadOnAction(ActionEvent actionEvent) {
        loadTable();
    }

    public void clearFields(){
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
    }
}
