package SushiInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.rmi.server.ExportException;
import java.util.ArrayList;

import Data.Inventory;
import Data.Item;
import Data.Order;
import Data.Payment;
import javafx.stage.StageStyle;

public class SushiMain extends Application {

    private CustomerController customerController ;
    private KitchenController kitchenController ;
    private ConfigController configController;
    private ReportController reportController;

    @Override
    public void start(Stage primaryStage) {
        firstStage();
        secondStage();
        thirdStage();
        fourthStage();

        // allow for communication between controllers methods
        customerController.setKitchenController(kitchenController);
        customerController.setConfigController(configController);
        kitchenController.setReportController(reportController);

        // ...
    }

    public void firstStage() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("SushiFXMLCustomer.fxml"));
            // Load root layout from fxml file.
            Parent root = (StackPane) loader.load();

            this.customerController = loader.getController();

            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void secondStage() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SushiFXMLKitchen.fxml"));
            // Load root layout from fxml file.
            Parent root = (VBox) loader.load();

            this.kitchenController = loader.getController();

            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void thirdStage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SushiFXMLConfig.fxml"));
            // Load root layout from fxml file.
            Parent root = (VBox) loader.load();

            this.configController = loader.getController();

            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fourthStage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReportInterface.fxml"));
            // Load root layout from fxml file.
            Parent root = (VBox) loader.load();

            this.reportController = loader.getController();

            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        launch(args);
    }


}
