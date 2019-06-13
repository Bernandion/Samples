package SushiInterface;

import Data.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class KitchenController {


    //                                              *** KITCHEN INTERFACE PROPERTIES ***


    // Queue for tracking multiple orders
    private List<Order> orderQueue = new ArrayList<>();



    @FXML
    private TextArea kitchenOrder, report;

    // report stage for opening and closing report window
    private ReportController reportController;



    //                                               *** KITCHEN INTERFACE METHODS ***

    /**
     * Set the report controller to be used to open and close report window.
     *
     * @param reportController - the controller for the report window
     */
    public void setReportController(ReportController reportController){
        this.reportController = reportController;
    }


    /**
     * Displays the order sent by the customer on the kitchen interface.
     * Gets called from customer interface when an order is sent.
     *
     * @param curOrder - the order sent by the customer interface
     */
    public void displayKitchenOrder(Order curOrder){
        // reset order text area
        kitchenOrder.clear();
        // add new order to order queue
        orderQueue.add(curOrder);
        // show orders in queue in text area 3 at a time
        for (int i=0; i<orderQueue.size(); i++){
            Order order = orderQueue.get(i);
            // only show order if status is approved
            if (order.getStatus().equals("Approved")){
                kitchenOrder.appendText("Table: " + Integer.toString(order.getTableID()) + "\n");
                kitchenOrder.appendText("Order Status: " + order.getStatus() + "\n");
                // show items for this order
                ArrayList<Item> orderitems = order.getItems();
                // sort the order so can be displayed by quantity
                Collections.sort(orderitems);
                // iterate through items and display the items by quantity
                int quantracker = 1;
                for (int j=0; j<orderitems.size(); j++){
                    Item curItem = orderitems.get(j);
                    // if next item is same as this one to be displayed, just increment quantity tracker
                    if(j<orderitems.size()-1 && orderitems.get(j+1).getName().equals(curItem.getName())){
                        quantracker++;
                    }
                    // else display the item by quantity ordered and reset quantity tracker
                    else {
                        kitchenOrder.appendText(Integer.toString(quantracker) + "x " + curItem.getName() + "\n");
                        quantracker = 1;
                    }
                }
                kitchenOrder.appendText("\n\n");
            }
        }

    }

    /**
     * Completes the oldest order in the order queue and resets the screen.
     *
     * @param event - event created from button press
     */
    @FXML
    private void completeOrder(ActionEvent event){
        // only do if queue isnt empty
        if (orderQueue.size() > 0){
            // set order status to complete for the oldest order
            orderQueue.get(0).updateOrderStatus("Complete");
            // remove from the queue
            orderQueue.remove(0);
            // reset screen text
            kitchenOrder.clear();
            // reprint order screen
            // show orders in queue in text area 3 at a time
            for (int i=0; i<orderQueue.size(); i++){
                Order order = orderQueue.get(i);
                // only show order if status is approved
                if (order.getStatus().equals("Approved")){
                    kitchenOrder.appendText("Table: " + Integer.toString(order.getTableID()) + "\n");
                    kitchenOrder.appendText("Order Status: " + order.getStatus() + "\n");
                    // show items for this order
                    ArrayList<Item> orderitems = order.getItems();
                    // sort the order so can be displayed by quantity
                    Collections.sort(orderitems);
                    // iterate through items and display the items by quantity
                    int quantracker = 1;
                    for (int j=0; j<orderitems.size(); j++){
                        Item curItem = orderitems.get(j);
                        // if next item is same as this one to be displayed, just increment quantity tracker
                        if(j<orderitems.size()-1 && orderitems.get(j+1).getName().equals(curItem.getName())){
                            quantracker++;
                        }
                        // else display the item by quantity ordered and reset quantity tracker
                        else {
                            kitchenOrder.appendText(Integer.toString(quantracker) + "x " + curItem.getName() + "\n");
                            quantracker = 1;
                        }
                    }
                    kitchenOrder.appendText("\n\n");
                }
            }
            // alert order is completed
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Order completed.");
            alert.showAndWait();
        }
        else {
            // alert order queue is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Order queue is empty.");
            alert.showAndWait();
        }

    }

    /**
     * Opens the report window to show item information
     *
     * @param event - event created from button press
     */
    @FXML
    private void openItemReport(ActionEvent event){

        // open config window
        try {
            this.reportController.openReport();
            this.reportController.printItemReport();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("REPORT INTERFACE INSTANTIATION PROBLEM");
        }

    }

    /**
     * Opens the report window to show order information
     *
     * @param event - event created from button press
     */
    @FXML
    private void openOrderReport(ActionEvent event){

        // open config window
        try {
            this.reportController.openReport();
            this.reportController.printOrderReport();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("REPORT INTERFACE INSTANTIATION PROBLEM");
        }

    }

    /**
     * Opens the report window to show payment information
     *
     * @param event - event created from button press
     */
    @FXML
    private void openPaymentReport(ActionEvent event){

        // open config window
        try {
            this.reportController.openReport();
            this.reportController.printPaymentReport();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("REPORT INTERFACE INSTANTIATION PROBLEM");
        }

    }








}
