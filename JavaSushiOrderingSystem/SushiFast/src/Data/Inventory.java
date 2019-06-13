package Data;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    // arraylists of all types of items
    private static List<Item> appetizers = DatabaseConnector.getItems("appetizer");
    private static List<Item> mains = DatabaseConnector.getItems("main");
    private static List<Item> desserts = DatabaseConnector.getItems("dessert");


    // tries to find an item in the inventory based on the ID and returns the item if found
    // returns null if not found
    public Item findItem(int itemID){
        Item curItem = null;
        for (int i=0; i<appetizers.size(); i++){
            curItem = appetizers.get(i);
            if (curItem.getItemID() == itemID) return curItem;
        }
        for (int i=0; i<mains.size(); i++){
            curItem = mains.get(i);
            if (curItem.getItemID() == itemID) return curItem;
        }
        for (int i=0; i<desserts.size(); i++){
            curItem = desserts.get(i);
            if (curItem.getItemID() == itemID) return curItem;
        }
        return null;
    }

    public static List<Item> getAppetizers() {return appetizers;}

    public static List<Item> getMains() {return mains;}

    public static List<Item> getDesserts() {return desserts;}

}
