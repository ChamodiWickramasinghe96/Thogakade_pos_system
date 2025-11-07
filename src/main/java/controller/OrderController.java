package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import service.ServiceFactory;
import service.custom.CustomerService;
import service.custom.ItemService;
import service.custom.OrderService;
import util.ServiceEnum;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class OrderController implements Initializable {


   
    public AnchorPane orderContext;
    public TableView tblOrder;
    public TableColumn colOrerId;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTotal;
    public JFXComboBox combCustomer;
    public JFXTextField txtCusName;
    public JFXTextField txtAddress;
    public JFXTextField txtSalary;
    public JFXComboBox combItem;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQuantity;
    public Label lblNetTotal;
    public Label lblOrderId;
    public JFXButton lblAddToCart;
    public JFXButton lblClear;
    public TableColumn colItemCode;
    public JFXButton btnClear;

    CustomerService service = ServiceFactory.getInstance().getFactory(ServiceEnum.CUSTOMER);
    ItemService itmService = ServiceFactory.getInstance().getFactory(ServiceEnum.ITEM);
    OrderService orderService = ServiceFactory.getInstance().getFactory(ServiceEnum.ORDER);
    //CustomerService service =  new CustomerServiceImpl();
    ArrayList<OrderTM> cartList = new ArrayList<>();

    @FXML
    void btnAddOrderOnAction(ActionEvent event) {

        Integer qty = Integer.parseInt(txtQuantity.getText());
        Double unitPrice =  Double.parseDouble(txtUnitPrice.getText());
        Double total = qty*unitPrice;
        cartList.add(new OrderTM(combItem.getValue().toString(),
                txtDescription.getText(),
                qty,
                unitPrice,
                total));
        tblOrder.setItems(FXCollections.observableArrayList(cartList));
        calNetTotal();

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderIds = lblOrderId.getText();
        Date orderDate = new Date();
        String customerId = combCustomer.getValue().toString();

        ArrayList<OrderDetails> orderDetails = new ArrayList<>();

        cartList.forEach(orders->{
            orderDetails.add(new OrderDetails(orderIds,
                    orders.getCode(),
                    orders.getQty(),
                    orders.getUnitPrice())
            );
        });

        Order order = new Order(orderIds,orderDate,customerId,orderDetails);
        System.out.println(order);
        orderService.addVouchers(order.getCustomerId(),Double.parseDouble(lblNetTotal.getText()),order.getOrderId());


    }




    void setCustomerCombValue(){
        try {
            combCustomer.setItems(FXCollections.observableArrayList(service.getCustomerIds()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblOrderId.setText(orderService.generateOrderId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        btnClear.setVisible(false);

        setCustomerCombValue();
        setItemComboValue();
        combCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println(oldValue);
            System.out.println(newValue);
            if (newValue!=null){
                setTextToValuesCustomer((String) newValue);
            }
        });

        combItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue!=null){
                setItemTextValue((String) newValue);
            }
        });
        colOrerId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

    }

    private void setItemTextValue(String newValue) {
        try {
            Item itm = itmService.search(newValue);
            txtDescription.setText(itm.getDescription());
            txtUnitPrice.setText(itm.getUnitPrice().toString());
            txtQtyOnHand.setText(itm.getQty().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setItemComboValue() {
        try {
            combItem.setItems(FXCollections.observableArrayList(itmService.getItemCmbValues()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void setTextToValuesCustomer(String newValue){

        try {
            Customer customer = service.search(newValue);
            txtCusName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(customer.getSalary().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getSelectValue(MouseEvent mouseEvent) {

    }

    void getCustomerId(MouseEvent mouseEvent){

    }
    void calNetTotal(){
        Double netTotal = 0.0;
        for (OrderTM orderTM:cartList){
            netTotal += orderTM.getTotal();
        }
        lblNetTotal.setText(netTotal.toString());
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        OrderTM removeOrder;
        for(int i = 0;i<cartList.size();i++){
            if (cartList.get(i).getCode()==selectedItem.getCode()){
                cartList.remove(cartList.get(i));
            }
        }
        tblOrder.setItems(FXCollections.observableArrayList(cartList));
        btnClear.setVisible(false);
    }
    OrderTM selectedItem;
    public void selectRow(MouseEvent mouseEvent) {
        btnClear.setVisible(true);
        selectedItem =(OrderTM) tblOrder.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem);


    }

    public void btnBacktoHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) orderContext .getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/dashRoot.fxml"))));
        stage.centerOnScreen();
    }
}

