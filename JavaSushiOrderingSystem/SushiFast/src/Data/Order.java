package Data;

import java.util.ArrayList;

public class Order {

    private int orderID;
    private int tableID;
    private int paymentID;
    private String status;
    private ArrayList<Item> Items;

    public Order(int startTableID, String startStatus, ArrayList<Item> orderItems) {
        this.orderID = 0;
        this.tableID = startTableID;
        this.paymentID = 0;
        this.status = startStatus;
        this.Items = orderItems;
    }

    public Order(int orderID, int startTableID, String startStatus, ArrayList<Item> orderItems) {
        this.orderID = orderID;
        this.tableID = startTableID;
        this.status = startStatus;
        this.Items = orderItems;
    }


    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    public int getTableID() {
        return tableID;
    }
    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public ArrayList<Item> getItems() {
        return Items;
    }
    public void setItems(ArrayList<Item> items) {
        this.Items = items;
    }
    public void setStatus(String orderStatus) {
        this.status = orderStatus;
    }

    public String getStatus() {
        return status;
    }

    public void saveOrder() {

        this.orderID = DatabaseConnector.storeOrder(this.tableID, this.status, this.Items);
    }

    public void updateOrderStatus(String newStatus){
        this.status = newStatus;
        DatabaseConnector.updateOrderStatus(this.orderID, newStatus);
    }

}
