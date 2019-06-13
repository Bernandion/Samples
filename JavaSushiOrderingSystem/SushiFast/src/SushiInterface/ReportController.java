package SushiInterface;

import Data.DatabaseConnector;
import Data.Item;
import Data.Order;
import Data.Payment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportController {

    @FXML
    private TextArea report;

    @FXML
    private VBox reportBox;

    /**
     * Shows the report window. Called by the kitchen interface.
     *
     */
    public void openReport(){

        // create stage for report screen and set FXML scene
        try {
            Stage thisStage = (Stage) this.reportBox.getScene().getWindow();
            thisStage.show();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("REPORT INTERFACE INSTANTIATION PROBLEM");
        }

    }

    /**
     * Shows all Item information in the Inventory.
     *
     */
    public void printItemReport(){


        // populate text area with Item information
        List<Item> allAppetizers = DatabaseConnector.getItems("appetizer");
        List<Item> allMains = DatabaseConnector.getItems("main");
        List<Item> allDesserts = DatabaseConnector.getItems("dessert");

        this.report.clear();
        this.report.appendText("----------- Appetizers -----------\n\n");
        for (int i=0; i<allAppetizers.size(); i++){
            Item curItem = allAppetizers.get(i);
            this.report.appendText("Item ID: " + Integer.toString(curItem.getItemID())
                    + "         Name: " + curItem.getName()
                    + "         Price: $" + Double.toString(curItem.getPrice())
                    + "         Image Path: " + curItem.getImgPath() + "\n"
                    + "Description:     " + curItem.getDescription() + "\n\n");
        }
        this.report.appendText("----------- Mains -----------\n\n");
        for (int i=0; i<allMains.size(); i++){
            Item curItem = allMains.get(i);
            this.report.appendText("Item ID: " + Integer.toString(curItem.getItemID())
                    + "         Name: " + curItem.getName()
                    + "         Price: " + Double.toString(curItem.getPrice())
                    + "         Image Path: " + curItem.getImgPath() + "\n"
                    + "Description:     " + curItem.getDescription() + "\n\n");
        }
        this.report.appendText("----------- Desserts -----------\n\n");
        for (int i=0; i<allDesserts.size(); i++){
            Item curItem = allDesserts.get(i);
            this.report.appendText("Item ID: " + Integer.toString(curItem.getItemID())
                    + "         Name: " + curItem.getName()
                    + "         Price: " + Double.toString(curItem.getPrice())
                    + "         Image Path: " + curItem.getImgPath() + "\n"
                    + "Description:     " + curItem.getDescription() + "\n\n");
        }

    }

    /**
     * Shows all Order information in the Inventory.
     *
     */
    public void printOrderReport(){

        // use for formatting total and tax with 2 decimal places
        String pattern = "#0.00";
        DecimalFormat formatter = new DecimalFormat(pattern);
        // populate text area with Order information
        List<Order> allOrders = DatabaseConnector.getOrders();
        this.report.clear();
        for (int i=0; i<allOrders.size(); i++){
            Order order = allOrders.get(i);
            this.report.appendText("Order ID: " + Integer.toString(order.getOrderID()) + "    Table ID: " + Integer.toString(order.getTableID()) + "    Status: " + order.getStatus() + "\n");
            ArrayList<Item> itemlist = order.getItems();
            // sort the order so can be displayed by quantity
            Collections.sort(itemlist);
            // iterate through items and display the items by quantity
            int quantracker = 1;
            for(int j=0; j<itemlist.size(); j++) {
                // next item is the same, increment quantity tracker
                if(j<itemlist.size()-1 && itemlist.get(j+1).getName().equals(itemlist.get(j).getName())){
                    quantracker++;
                }
                // else display the item by quantity ordered and reset quantity tracker
                else {
                    this.report.appendText(Integer.toString(quantracker) + "x - " + "Item ID: " + Integer.toString(itemlist.get(j).getItemID()) + "      Name: " + itemlist.get(j).getName()
                            + "      Status: " + itemlist.get(j).getDescription() + "      Image: " + itemlist.get(j).getImgPath()
                            + "      Price: $" + formatter.format(itemlist.get(j).getPrice()) + "\n");
                    quantracker = 1;
                }
            }
            this.report.appendText("\n\n");
        }

    }

    /**
     * Shows all Payment information in the Inventory.
     *
     */
    public void printPaymentReport(){

        // use for formatting total and tax with 2 decimal places
        String pattern = "#0.00";
        DecimalFormat formatter = new DecimalFormat(pattern);
        // populate text area with Payment information
        List<Payment> allPayments = DatabaseConnector.getPayments();
        // track number of approved payments
        int approvedPayments = 0;
        // track number of visa payments, mastercard payments, american express payments
        int visaPayments = 0, masterPayments = 0, americanPayments = 0;
        // track total payment amount total from all payments
        double totalPayment = 0;
        this.report.clear();
        for (int i=0; i<allPayments.size(); i++) {
            String paymentStatus = "Declined";
            // set string for approved if boolean is true and increment tracker
            if (allPayments.get(i).getStatusFlag()){
                paymentStatus = "Approved";
                approvedPayments++;
            }
            // increment payment type tracker depending on payment type
            switch (allPayments.get(i).getPaymentType()){
                case "Visa": visaPayments++; break;
                case "MasterCard": masterPayments++; break;
                default: americanPayments++; break;
            }
            // increase total payment tracker
            totalPayment += allPayments.get(i).getOrderTotal();
            // print payment information
            this.report.appendText("Payment ID: " + Integer.toString(allPayments.get(i).getPaymentID())
                    + "         Order ID: " + Integer.toString(allPayments.get(i).getOrderID())
                    + "         Order Total: $" + formatter.format(allPayments.get(i).getOrderTotal())
                    + "\nPayment Type: " + allPayments.get(i).getPaymentType()
                    + "         Return Code: " + allPayments.get(i).getReturnCode()
                    + "         Status: " + paymentStatus + "\n\n"
            );
        }
        // get percentage of approved payments
        double approvedPercent = Math.round((approvedPayments / (double)allPayments.size()) * 100.0);
        // get percentage of each payment type
        double visaPercent = Math.round((visaPayments / (double)allPayments.size()) * 100.0);
        double masterPercent = Math.round((masterPayments / (double)allPayments.size()) * 100.0);
        double americanPercent = Math.round((americanPayments / (double)allPayments.size()) * 100.0);
        // get average order total for all payments
        double totalAverage = Math.round(totalPayment / (double)allPayments.size());
        BigDecimal bd = new BigDecimal(totalAverage);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        // show all text again with information at the top
        this.report.setText("\n% of Approved Payments: " + Double.toString(approvedPercent)
                + "   -   % of Visa: " + Double.toString(visaPercent)
                + "   % of MasterCard: " + Double.toString(masterPercent)
                + "   % of American Express: " + Double.toString(americanPercent)
                + "   -   Average Order Total: $" + formatter.format(bd.doubleValue()) + "\n\n"
                + this.report.getText());
    }

}
