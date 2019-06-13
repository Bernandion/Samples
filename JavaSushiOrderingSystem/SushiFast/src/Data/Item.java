package Data;

public class Item implements Comparable<Item> {

    private int itemID;
    private String name;
    private String description;
    private String imgPath;
    private double price;

    public Item(String startName, String startDescription, String startImgPath, double price){
        this.name = startName;
        this.description = startDescription;
        this.imgPath = startImgPath;
        this.price = price;
    }

    public Item(int startID, String startName, String startDescription, String startImgPath, double price) {
        this.itemID = startID;
        this.name = startName;
        this.description = startDescription;
        this.imgPath = startImgPath;
        this.price = price;
    }

    // allows for items to be sorted by their name in ascending order
    @Override
    public int compareTo(Item compareItem) {
        return this.name.compareTo(compareItem.getName());
    }

    public int getItemID() {
        return itemID;
    }
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImgPath() {
        return imgPath;
    }
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }


}
