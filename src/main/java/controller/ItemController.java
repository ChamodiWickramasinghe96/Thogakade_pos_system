package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Item;
import service.ServiceFactory;
import service.custom.ItemService;
import util.ServiceEnum;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemController implements Initializable {


    public TableView tblItem;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public JFXTextField txtCode;
    public JFXTextField txtQty;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public AnchorPane itemContext;
    Alert alert;
    ItemService service = ServiceFactory.getInstance().getFactory(ServiceEnum.ITEM);
    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            boolean add = service.save(new Item(txtCode.getText(),
                    txtDescription.getText(),
                    Integer.parseInt(txtQty.getText()),
                    Double.parseDouble(txtUnitPrice.getText()))
            );

            alert=null;
            if (add){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Process Success !");
                alert.setContentText("Item : "+txtDescription.getText()+" ( ID : "+txtCode.getText()+" ) added successfully !");
            }else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fail");
                alert.setHeaderText("Process Failed !");
                alert.setContentText("There is an issue with adding customer : "+txtDescription.getText()+" !");
            }
            alert.show();

            clearField();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        loadTable();

    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        loadTable();

    }
    ObservableList<Item> itemList;
    private void loadTable(){

        try {
            itemList = FXCollections.observableArrayList(service.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tblItem.setItems(itemList);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            Item item = new Item(txtCode.getText(),
                    txtDescription.getText(),
                    Integer.parseInt(txtQty.getText()),
                    Double.parseDouble(txtUnitPrice.getText()));
            boolean update = service.update(new Item(txtCode.getText(),
                    txtDescription.getText(),
                    Integer.parseInt(txtQty.getText()),
                    Double.parseDouble(txtUnitPrice.getText())));

            alert=null;
            if (update){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Process Success !");
                alert.setContentText("Item : "+txtDescription.getText()+" ( ID : "+txtCode.getText()+" ) updated successfully !");
            }else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fail");
                alert.setHeaderText("Process Failed !");
                alert.setContentText("There is an issue with updating customer : "+txtDescription.getText()+" !");
            }
            alert.show();

            clearField();
            loadTable();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            boolean delete = service.delete(txtCode.getText());

            alert=null;
            if (delete){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Process Success !");
                alert.setContentText("Item : "+txtDescription.getText()+" ( ID : "+txtCode.getText()+" ) Delete successfully !");
            }else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fail");
                alert.setHeaderText("Process Failed !");
                alert.setContentText("There is an issue with delete customer : "+txtDescription.getText()+" !");
            }
            alert.show();

            clearField();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        loadTable();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        try {
            Item item = service.search(txtCode.getText());
            txtCode.setText(item.getCode());
            txtDescription.setText(item.getDescription());
            txtQty.setText(item.getQty().toString());
            txtUnitPrice.setText(item.getUnitPrice().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnBacktoHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) itemContext .getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/dashRoot.fxml"))));
        stage.centerOnScreen();
    }

    public void clearField(){
        txtCode.clear();
        txtDescription.clear();
        txtQty.clear();
        txtUnitPrice.clear();
    }
}
