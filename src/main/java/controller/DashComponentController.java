package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;


public class DashComponentController {
       public Rectangle dashComponentContext;

    public void btnBacktoHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) dashComponentContext .getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/dashRoot.fxml"))));
        stage.centerOnScreen();
    }


}
