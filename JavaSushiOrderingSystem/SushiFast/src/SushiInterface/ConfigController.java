package SushiInterface;

import Data.ControllerData;
import Data.DatabaseConnector;
import Data.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class ConfigController {


    //                                              *** CONFIG INTERFACE PROPERTIES ***


    // text fields for changing system data
    @FXML
    private TextField configTableNum, configTax, itemType, itemName, itemPrice, itemPath, itemRemoveID;

    @FXML
    private TextArea itemDescription;

    @FXML
    private VBox configBox;

    //                                              *** CONFIG INTERFACE METHODS ***


    /**
     * Shows the config interface window. Called by the customer interface.
     *
     */
    public void openConfig(){

        // create stage for config screen and set FXML scene
        try {
            Stage thisStage = (Stage) this.configBox.getScene().getWindow();
            thisStage.show();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("CONFIG INTERFACE INSTANTIATION PROBLEM");
        }

    }

    /**
     * Closes the config window and resets any values in window.
     *
     * @param event - event created from button press
     */
    @FXML
    private void closeConfig(ActionEvent event){

        // close config stage
        try {
            Stage thisStage = (Stage) this.configBox.getScene().getWindow();
            thisStage.close();
            configTableNum.clear();
            configTax.clear();
            itemType.clear();
            itemName.clear();
            itemPrice.clear();
            itemPath.clear();
            itemRemoveID.clear();
            itemDescription.clear();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("CONFIG INTERFACE CLOSING PROBLEM");
        }

    }

    /**
     * Apply all changes in changed data fields to system data.
     *
     * @param event - event created from button press
     */
    @FXML
    private void applyChanges(ActionEvent event){
        // get strings for table number and tax value
        String tableNumString = this.configTableNum.getText();
        String taxString = this.configTax.getText();
        // if table num contains text, do changes
        if (tableNumString.length() > 0){
            // make sure string only contains numbers
            if (tableNumString.matches("[0-9]+")){
                int tabnum = Integer.parseInt(tableNumString);
                ControllerData.tableNum = tabnum;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Table Number Changed.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Table Number Incorrect.");
                alert.showAndWait();
            }
        }
        // if tax contains text, do changes
        if (taxString.length() > 0){
            // make sure string only contains correct tax format
            if (taxString.matches("^(0?[.][0-9]+)$")){
                double taxnum = Double.parseDouble(taxString);
                ControllerData.tax = taxnum;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Tax Changed.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Tax Incorrect.");
                alert.showAndWait();
            }
        }

    }

    /**
     * Adds an item to the database using Strings written in text areas in interface.
     *
     * @param event - event created from button press
     */
    @FXML
    private void addItem(ActionEvent event){

        // get input
        String itemType = this.itemType.getText();
        String itemName = this.itemName.getText();
        String itemDescription = this.itemDescription.getText();
        String itemPath = this.itemPath.getText();
        String itemPrice = this.itemPrice.getText();
        try {
            // ensure item price and item ID consists of numbers and type is appetizer main or dessert
            if (!itemPrice.matches("^[a-zA-Z]+$") && (itemType.equals("appetizer") || itemType.equals("main") || itemType.equals("dessert"))){
                // use default image if image file doesnt exist
                File f = new File(itemPath);
                Item addItem;
                if(f.exists() && !f.isDirectory()){
                    addItem = new Item(itemName, itemDescription, itemPath, Double.parseDouble(itemPrice));
                }
                else {
                    addItem = new Item(itemName, itemDescription, "images/noimage.jpg", Double.parseDouble(itemPrice));
                }

                DatabaseConnector.addInventoryItem(addItem, itemType);
                // tell item added
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Item added to Database.");
                alert.showAndWait();
                // clear fields
                this.itemType.clear();
                this.itemName.clear();
                this.itemPrice.clear();
                this.itemPath.clear();
                this.itemDescription.clear();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Item information invalid.");
                alert.showAndWait();
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("ITEM CREATION ERROR");
        }

    }

    /**
     * Removes an item from the database using the Item ID written in text field in interface.
     *
     * @param event - event created from button press
     */
    @FXML
    private void removeItem(ActionEvent event){

        // get input
        String itemRemoveID = this.itemRemoveID.getText();

        try {
            // ensure item ID consists of numbers
            if (!itemRemoveID.matches("^[a-zA-Z]+$")){
                DatabaseConnector.removeInventoryItem(Integer.parseInt(itemRemoveID));
                // tell item removed
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Item removed from Database.");
                alert.showAndWait();
                // clear field
                this.itemRemoveID.clear();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Item ID invalid.");
                alert.showAndWait();
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("ITEM DELETION ERROR");
        }

    }
}
