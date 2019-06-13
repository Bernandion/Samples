package Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing static data that needs to be shared between interface controllers in separate stages.
 *
 */
public class ControllerData {

    // integer indicating the table number
    public static int tableNum = 1;

    // double for tax percentage
    public static double tax = 0.05;

    // arraylist of items for the current order
    public static List<Item> orderitems = new ArrayList<>();

    // track total price for items being selected by customer
    public static double orderTotal = 0;


}
