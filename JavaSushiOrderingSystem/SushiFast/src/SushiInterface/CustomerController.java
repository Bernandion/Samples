package SushiInterface;

import Data.*;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 *  Handles orders and transfer of data between customer and kitchen interfaces
 *
 */
public class CustomerController implements Initializable {




    //                                              *** CUSTOMER INTERFACE PROPERTIES ***

    // constant for if configuration button to open the config interface should be shown
    private final boolean SHOW_CONFIG = true;


    // kitchen controller reference
    private KitchenController kitchenController;

    // config controller reference
    private ConfigController configController;

    // inventory of all items
    private Inventory itemInventory = new Inventory();

    // list of children in stackpane to switch between panes
    private ObservableList<Node> children;

    // config interface opening button
    @FXML
    private Button configButton;

    // container to switch between panes
    @FXML
    private StackPane stack;

    // dynamically created item and review panes
    @FXML
    private FlowPane item;

    // text areas on review pane for writing order information
    @FXML
    private TextArea items, prices;

    // text fields for credit card input areas
    @FXML
    private TextField cardExp, cardCVC, cardNum;

    // choice box for review pane card type
    @FXML
    private ChoiceBox<String> cardChoices;

    // panes for holding menu items
    @FXML
    private FlowPane appetizer, main, dessert;

    // item flowpane container
    @FXML
    private Pane itemcont;



    //                                              *** CUSTOMER INTERFACE METHODS ***

    /**
     * Get kitchen controller to send order information. Used to call methods located in the kitchenController.
     *
     * @param kitchenController - the kitchen window controller
     */
    public void setKitchenController(KitchenController kitchenController){
        this.kitchenController = kitchenController;
    }

    /**
     * Get config controller to send order information. Used to call methods located in the configController.
     *
     * @param configController - the configuration window controller
     */
    public void setConfigController(ConfigController configController){
        this.configController = configController;
    }

    // shows detailed information of a specific item in a new paned
    @FXML
    private void showItem(ActionEvent event){

        // use for formatting prices with 2 decimal places
        String pattern = "#0.00";
        DecimalFormat formatter = new DecimalFormat(pattern);
        // create back button in top left of page
        Button backButton = new Button();
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                backToMenu(event);
            }
        });
        backButton.setPrefWidth(100);
        backButton.setPrefHeight(60);
        backButton.setText("Back to Menu");
        item.getChildren().add(backButton);
        // Populate pane with specific information based on the selected item
        Button source = (Button)event.getSource();
        String butitem = source.getId();
        // find the specific menu item based on the button's id
        Item infoitem = itemInventory.findItem(Integer.parseInt(butitem));
        // Populate pane with item information
        // set the image for the items page
        ImageView itemimg = new ImageView();
        itemimg.setImage(new Image(infoitem.getImgPath()));
        itemimg.setFitWidth(450);
        itemimg.setPreserveRatio(true);
        itemimg.setSmooth(true);
        item.getChildren().add(itemimg);
        // set the name for the item page
        Text itemname = new Text();
        itemname.setText(infoitem.getName());
        itemname.setFont(Font.font(35));
        item.getChildren().add(itemname);
        // set the price for the item
        Text itemprice = new Text();
        itemprice.setText("$" + formatter.format(infoitem.getPrice()));
        itemprice.setFont(Font.font("System", FontWeight.BOLD, 30));
        item.getChildren().add(itemprice);
        // set the item description
        Text itemdesc = new Text();
        itemdesc.wrappingWidthProperty().bind(item.widthProperty());
        itemdesc.setText("\n" + infoitem.getDescription() + "\n");
        itemdesc.setFont(Font.font("System",16));
        item.getChildren().add(itemdesc);
        // add button at bottom to add item to list of items for order
        Button addButton = new Button();
        Item finalInfoitem = infoitem;
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ControllerData.orderitems.add(finalInfoitem);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Item added to order!");
                alert.showAndWait();
            }
        });
        addButton.setPrefWidth(100);
        addButton.setPrefHeight(100);
        addButton.setText("Add To Order");
        item.getChildren().add(addButton);

        // move the menu pane to the back to show the item info
        Node topNode = children.get(children.size()-1);
        topNode.toBack();
    }

    /**
     * Returns to the menu pane depending on current pane.
     *
     * @param event - event created from button press
     */
    @FXML
    private void backToMenu(ActionEvent event){

        Node topNode = children.get(children.size()-1);

        if (topNode.getId().equals("itemcont")){
            // remove everything in the item pane
            item.getChildren().clear();
            // switch back to menu if on item pane
            Node bottomNode = children.get(0);
            bottomNode.toFront();
        }
        else {
            // clear text areas on review pane
            items.clear();
            prices.clear();
            // switch back to menu if on review pane
            topNode.toBack();
        }

    }

    /**
     * Resets the customers current order list of items so they can start selection over.
     *
     * @param event - event created from button press
     */
    @FXML
    private void resetOrder(ActionEvent event){
        ControllerData.orderitems = new ArrayList<>();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Order items reset.");
        alert.showAndWait();
    }

    /**
     * Opens the order review pane and lists the items in the customers order with the prices.
     *
     * @param event - event created from button press
     */
    @FXML
    private void reviewOrder(ActionEvent event){

        // reset total order price tracker
        ControllerData.orderTotal = 0;
        // use for formatting total and tax with 2 decimal places
        String pattern = "#0.00";
        DecimalFormat formatter = new DecimalFormat(pattern);
        // sort the order so can be displayed by quantity
        Collections.sort(ControllerData.orderitems);
        // iterate through items and display the items by quantity
        int quantracker = 1;
        double sameItemTotal = 0;
        for (int i=0; i<ControllerData.orderitems.size(); i++){
            Item curItem = ControllerData.orderitems.get(i);
            // if next item is same as this one to be displayed, just increment quantity tracker
            // add total price for multiple of same item
            if(i<ControllerData.orderitems.size()-1 && ControllerData.orderitems.get(i+1).getName().equals(curItem.getName())){
                quantracker++;
                sameItemTotal += curItem.getPrice();
            }
            // else display the item by quantity ordered and reset quantity tracker
            else {
                // get price for item if it has multiple of same
                sameItemTotal += curItem.getPrice();
                // write order information into text areas on review pane
                items.appendText(Integer.toString(quantracker) + "x " + curItem.getName() + "\n");
                // add to total price tracker
                ControllerData.orderTotal += sameItemTotal;
                prices.appendText("$" + formatter.format(sameItemTotal) + "\n");
                quantracker = 1;
                sameItemTotal = 0;
            }
        }

        // show tax rounded to two decimal places
        items.appendText("\nTax:");
        // get tax on order
        double orderTax = ControllerData.tax * ControllerData.orderTotal;
        BigDecimal bd = new BigDecimal(orderTax);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        prices.appendText("\n" + "$" + formatter.format(bd.doubleValue()));
        // show total with tax rounded to two decimal places
        items.appendText("\nTotal:");
        bd = new BigDecimal(ControllerData.orderTotal + orderTax);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        prices.appendText("\n" + "$" + formatter.format(bd.doubleValue()));
        // move the review pane to the front to show the order info
        Node bottomNode = children.get(0);
        bottomNode.toFront();

    }

    /**
     * Sends the order to the kitchen/server interface and saves the order to the database.
     *
     * @param event - event created from button press
     */
    @FXML
    private void placeOrder(ActionEvent event){

        // get payment information from text fields
        String cardType = cardChoices.getSelectionModel().getSelectedItem();
        String cardNum = this.cardNum.getText();
        String cardExp = this.cardExp.getText();
        String cardCVV = this.cardCVC.getText();
       
        if (ControllerData.orderitems.size() == 0) {
        	Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You must add items to your order before you can pay.");
            alert.showAndWait();
        }
        else{
	        // create order and payment objects
	        Order order = new Order(ControllerData.tableNum, "Verification", (ArrayList<Item>) ControllerData.orderitems);
	        order.saveOrder();
	        Payment orderPayment = new Payment(order.getOrderID(), cardType, cardNum, cardExp, cardCVV, ControllerData.orderTotal);
	        // process payment
	        orderPayment.processPayment();
	        // if payment successful, sends the order to the interface
	        if (orderPayment.getStatusFlag()){
                order.updateOrderStatus("Approved");
	            // save the order to the database            
	            // display order information on kitchen interface
                try{
                    this.kitchenController.displayKitchenOrder(order);

                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println("KITCHEN displayOrder CALL ERROR");
                }
	            // show message saying order is sent
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Payment successful. Order sent! \nTransaction ID: " + orderPayment.getReturnCode());
	            alert.showAndWait();
                // clear text areas on review pane
                this.items.clear();
                this.prices.clear();
                this.cardChoices.getSelectionModel().selectFirst();
                this.cardNum.clear();
                this.cardExp.clear();
                this.cardCVC.clear();
                // reset order items
                ControllerData.orderitems = new ArrayList<>();
                // switch back to menu if on review pane
                children.get(children.size()-1).toBack();
	        }
	            // if payment unsuccessful, gives a message
	        else {
                order.updateOrderStatus("Declined");
	            Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Payment failed. Please try again. \nTransaction ID: " + orderPayment.getReturnCode());
	            alert.showAndWait();
	            
	        }
        }
    }

    /**
     * Opens the config window for system data to be changed
     *
     * @param event - event created from button press
     */
    @FXML
    private void openConfig(ActionEvent event){

        // open config window
        try {
            this.configController.openConfig();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("CONFIG INTERFACE INSTANTIATION PROBLEM");
        }

    }


    // initializing method
    @Override
    public void initialize(URL url, ResourceBundle rb){

        // use for formatting prices with 2 decimal places
        String pattern = "#0.00";
        DecimalFormat formatter = new DecimalFormat(pattern);

        // populate the appetizers section on the menu interface
        List<Item> apps = itemInventory.getAppetizers();
        for (int i=0; i<apps.size(); i++){
            // add vbox container to grid
            VBox vbox = new VBox();
            vbox.setPrefWidth(197);
            vbox.setPrefHeight(200);
            appetizer.getChildren().add(vbox);
            // add button to grid that has a picture of item
            Button button = new Button();
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    showItem(event);
                }
            });
            button.setPrefWidth(100);
            button.setPrefHeight(100);
            String imgPath = apps.get(i).getImgPath();
            ImageView img = new ImageView(new Image(imgPath));
            img.setFitWidth(100);
            img.setFitHeight(100);
            button.setGraphic(img);
            // set the button ID to the item's ID
            button.setId(Integer.toString(apps.get(i).getItemID()));
            vbox.getChildren().add(button);
            // add text for name of item
            Text name = new Text();
            name.setText(apps.get(i).getName());
            vbox.getChildren().add(name);
            // add price for the item
            Text price = new Text();
            price.setText("$" + formatter.format(apps.get(i).getPrice()));
            vbox.getChildren().add(price);
        }

        // populate the mains section on the menu interface
        List<Item> mains = itemInventory.getMains();
        for (int i=0; i<mains.size(); i++){
            // add vbox container to grid
            VBox vbox = new VBox();
            vbox.setPrefWidth(197);
            vbox.setPrefHeight(200);
            main.getChildren().add(vbox);
            // add button to grid that has a picture of item
            Button button = new Button();
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    showItem(event);
                }
            });
            button.setPrefWidth(100);
            button.setPrefHeight(100);
            String imgPath = mains.get(i).getImgPath();
            ImageView img = new ImageView(new Image(imgPath));
            img.setFitWidth(100);
            img.setFitHeight(100);
            button.setGraphic(img);
            // set the button ID to the item's ID
            button.setId(Integer.toString(mains.get(i).getItemID()));
            vbox.getChildren().add(button);
            // add text for name of item
            Text name = new Text();
            name.setText(mains.get(i).getName());
            vbox.getChildren().add(name);
            // add price for the item
            Text price = new Text();
            price.setText("$" + formatter.format(mains.get(i).getPrice()));
            vbox.getChildren().add(price);
        }

        // populate the desserts section on the menu interface
        List<Item> desserts = itemInventory.getDesserts();
        for (int i=0; i<desserts.size(); i++){
            // add vbox container to grid
            VBox vbox = new VBox();
            vbox.setPrefWidth(197);
            vbox.setPrefHeight(200);
            dessert.getChildren().add(vbox);
            // add button to grid that has a picture of item
            Button button = new Button();
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    showItem(event);
                }
            });
            button.setPrefWidth(100);
            button.setPrefHeight(100);
            String imgPath = desserts.get(i).getImgPath();
            ImageView img = new ImageView(new Image(imgPath));
            img.setFitWidth(100);
            img.setFitHeight(100);
            button.setGraphic(img);
            // set the button ID to the item's ID
            button.setId(Integer.toString(desserts.get(i).getItemID()));
            vbox.getChildren().add(button);
            // add text for name of item
            Text name = new Text();
            name.setText(desserts.get(i).getName());
            vbox.getChildren().add(name);
            // add price for the item
            Text price = new Text();
            price.setText("$" + formatter.format(desserts.get(i).getPrice()));
            vbox.getChildren().add(price);
        }

        // populate review pane choice box
        cardChoices.getItems().add("Visa");
        cardChoices.getItems().add("MasterCard");
        cardChoices.getItems().add("American Express");
        // set default choice
        cardChoices.getSelectionModel().selectFirst();

        // show config button or not
        if (!SHOW_CONFIG){
            this.configButton.setDisable(true);
            this.configButton.setOpacity(0);
        }

        // get stack pane children to switch between panes
        children = stack.getChildren();
    }


}

