package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;


public class DashRootController {
    public AnchorPane dashBoardContext;
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        lblDate.setText(simpleDateFormat.format(date));


        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    LocalTime now = LocalTime.now();
                    lblTime.setText(now.getHour() + ":" + now.getMinute() + ":" + now.getSecond());
                }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }



    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {
        setUi("dashBoard");
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUi("customer");
    }

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        setUi("item");
    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        setUi("order");
    }

    public void btnAboutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("about");
    }

    private void setUi(String ui) throws IOException {
        Stage stage = (Stage) dashBoardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+ui+".fxml"))));
        stage.centerOnScreen();
    }


}
