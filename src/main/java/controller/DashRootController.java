package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashRootController {
    private AnchorPane root;

    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {

       Parent load = FXMLLoader.load(getClass().getResource("../view/dashBoard.fxml"));
       assert root != null;

       root.getChildren().clear();
       root.getChildren().add(load);

    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {

     Parent load = FXMLLoader.load(getClass().getResource("../view/customer.fxml"));
     assert root != null;

     root.getChildren().clear();
     root.getChildren().add(load);

    }

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {

      Parent load = FXMLLoader.load(getClass().getResource("../view/item.fxml"));
      assert root != null;

      root.getChildren().clear();
      root.getChildren().add(load);

    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {

      Parent load = FXMLLoader.load(getClass().getResource("../veiw/order.fxml"));
      assert root != null;

      root.getChildren().clear();
      root.getChildren().add(load);

    }

    public void btnAboutOnAction(ActionEvent actionEvent) throws IOException {

      Parent load = FXMLLoader.load(getClass().getResource("../view/about.fxml"));
      assert root != null;

      root.getChildren().clear();
      root.getChildren().add(load);


    }


}
