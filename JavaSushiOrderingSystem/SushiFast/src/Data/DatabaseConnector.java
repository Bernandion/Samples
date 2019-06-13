package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.sql.Connection;

public class DatabaseConnector {
	// Database Configuration Settings
	private static String dbAddress = "localhost";
	private static String dbPort = "3306";
	private static String dbName = "sushifast";
	private static String dbUsername = "sushifast";
	private static String dbPassword = "nomnomnom";
	private static String dbType = "mariadb";
	private static ArrayList<Item> items;

	public static void addInventoryItem(Item newItem, String itemType) {
		Statement stmt = null;
		String sql = "INSERT INTO `inventory` (`name`, `description`, `image_path`, `price`, `type`) "
				+ "VALUES ('"+ newItem.getName() + "', '"+ newItem.getDescription() + "', '"+ newItem.getImgPath() + "', '" + newItem.getPrice()
				+ "', '"+ itemType +"');";

		try (Connection con = DriverManager.getConnection("jdbc:"+dbType+"://"+ dbAddress +":"+ dbPort +
				"/"+ dbName +"?user="+ dbUsername +"&password="+dbPassword)){
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public static void removeInventoryItem(int itemID) {
		Statement stmt = null;
		String sql = "DELETE FROM inventory WHERE item_ID="+itemID+";";

		try (Connection con = DriverManager.getConnection("jdbc:"+dbType+"://"+ dbAddress +":"+ dbPort +
				"/"+ dbName +"?user="+ dbUsername +"&password="+dbPassword)){
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	//Gets a list of inventory items based upon the item type (main, dessert ,appetizer)
	public static ArrayList<Item> getItems(String itemType) {
		ArrayList<Item> items= new ArrayList<Item>();
		Statement stmt = null;
		try (Connection con = DriverManager.getConnection("jdbc:"+dbType+"://"+ dbAddress +":"+ dbPort +
				"/"+ dbName +"?user="+ dbUsername +"&password="+dbPassword)){
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM inventory WHERE type='" + itemType + "'");
			while (rs.next()) {
				int itemID = rs.getInt("item_ID");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String imgPath = rs.getString("image_Path");
				// Round to nearest hundredth
				double price = rs.getDouble("price");
				price = Math.round(price * 100.0) / 100.0;
				Item itemElement = new Item(itemID, name, description, imgPath, price);
				//System.out.println(name + ":\n\n" + description + "\n\n" + imgPath + "\n\n$" + price +"\n\n\n");
				items.add(itemElement);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		if (items.size() == 0) {System.out.println("NO ITEMS OF TYPE "+itemType+" FOUND IN THE INVENTORY"); return null;}
		else return items;
	}

	public static ArrayList<Payment> getPayments() {
		ArrayList<Payment> payments= new ArrayList<Payment>();
		Statement stmt = null;
		try (Connection con = DriverManager.getConnection("jdbc:"+dbType+"://"+ dbAddress +":"+ dbPort +
				"/"+ dbName +"?user="+ dbUsername +"&password="+dbPassword)){
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM payment");
			while (rs.next()) {
				int paymentID = rs.getInt("Payment_ID");
				int orderID = rs.getInt("Order_ID");
				String paymentType = rs.getString("payment_Type");
				String returnCode = rs.getString("return_Code");
				boolean statusFlag = rs.getBoolean("status_Flag");
				// Round to nearest hundredth
				double orderTotal = rs.getDouble("order_Total");
				orderTotal = Math.round(orderTotal * 100.0) / 100.0;
				Payment paymentElement = new Payment(paymentID, orderID, paymentType, returnCode, orderTotal, statusFlag);
				//System.out.println(name + ":\n\n" + description + "\n\n" + imgPath + "\n\n$" + price +"\n\n\n");
				payments.add(paymentElement);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return payments;
	}

	public static ArrayList<Order> getOrders() {
		ArrayList<Item> items= new ArrayList<Item>();
		ArrayList<Order> orders= new ArrayList<Order>();
		Statement stmt = null;
		try (Connection con = DriverManager.getConnection("jdbc:"+dbType+"://"+ dbAddress +":"+ dbPort +
				"/"+ dbName +"?user="+ dbUsername +"&password="+dbPassword)){
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM orders ORDER BY order_ID DESC");
			while (rs.next()) {
				int orderID = rs.getInt("order_ID");
				int tableID = rs.getInt("table_ID");
				String status = rs.getString("status");
				ResultSet rs2 = stmt.executeQuery("SELECT inventory.item_id, inventory.name, inventory.description, inventory.image_Path, inventory.price "
						+ "FROM order_items "
						+ "RIGHT JOIN inventory ON order_items.item_ID = inventory.item_ID "
						+ "WHERE order_ID = "+orderID+"");
				while (rs2.next()) {
					int itemID = rs2.getInt("item_ID");
					String name = rs2.getString("name");
					String description = rs2.getString("description");
					String imgPath = rs2.getString("image_Path");
					// Round to nearest hundredth
					double price = rs2.getDouble("price");
					price = Math.round(price * 100.0) / 100.0;
					Item itemElement = new Item(itemID, name, description, imgPath, price);
					//System.out.println(name + ":\n\n" + description + "\n\n" + imgPath + "\n\n$" + price +"\n\n\n");
					items.add(itemElement);
				}
				Order order = new Order(orderID, tableID, status, items);
				orders.add(order);
                items = new ArrayList<>();
				//Test it

                    /*
    		        System.out.println("Order ID: "+ order.getOrderID() + "    Table ID: " + order.getTableID() + "    Status: " + order.getStatus());
    		        ArrayList<Item> itemlist = order.getItems();
    		        for(int i=0; i<itemlist.size(); i++) {
    		        	System.out.println("Item ID: "+ itemlist.get(i).getItemID() + "    Name: " + itemlist.get(i).getName()
    		        			+ "    Status: " + itemlist.get(i).getDescription() + "    Image: " + itemlist.get(i).getImgPath()
    		        			+ "    Price: " + itemlist.get(i).getPrice());
    		        }
                    */

			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return orders;
	}

	public static void storePayment(int paymentID, int orderID, double orderTotal, String paymentType, String returnCode, boolean statusFlag){
		Statement stmt = null;
		int orderStatus = statusFlag? 1 : 0;
		String sql = "INSERT INTO `payment` (`order_ID`, `order_Total`, `payment_Type`, `return_Code`, `status_Flag`) "
				+ "VALUES ('"+ orderID + "', '"+ orderTotal + "', '"+ paymentType + "', '" + returnCode
				+ "', '"+ orderStatus +"');";

		try (Connection con = DriverManager.getConnection("jdbc:"+dbType+"://"+ dbAddress +":"+ dbPort +
				"/"+ dbName +"?user="+ dbUsername +"&password="+dbPassword)){
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public static void updateOrderStatus(int orderID, String newStatus){
		Statement stmt = null;
		String sql = "UPDATE orders SET status='"+newStatus+"' WHERE Order_ID="+orderID+";";

		try (Connection con = DriverManager.getConnection("jdbc:"+dbType+"://"+ dbAddress +":"+ dbPort +
				"/"+ dbName +"?user="+ dbUsername +"&password="+dbPassword)){
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public static int storeOrder(int tableID, String status, ArrayList<Item> items){
		Statement stmt = null;
		int orderID = -1;
		String sql = "INSERT INTO `orders` (`table_ID`, `status`) "
				+ "VALUES ('"+ tableID + "', '"+ status +"');";
		// Add the order to the database
		try (Connection con = DriverManager.getConnection("jdbc:"+dbType+"://"+ dbAddress +":"+ dbPort +
				"/"+ dbName +"?user="+ dbUsername +"&password="+dbPassword)){
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		// Get the newly generated orderID
		try (Connection con = DriverManager.getConnection("jdbc:"+dbType+"://"+ dbAddress +":"+ dbPort +
				"/"+ dbName +"?user="+ dbUsername +"&password="+dbPassword)){
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM orders WHERE table_ID = '"+ tableID +"'  ORDER BY order_ID DESC LIMIT 1");
			rs.next();
			orderID = rs.getInt("order_ID");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}


		// save the items of the order
		try (Connection con = DriverManager.getConnection("jdbc:"+dbType+"://"+ dbAddress +":"+ dbPort +
				"/"+ dbName +"?user="+ dbUsername +"&password="+dbPassword)){
			for (int i = 0; i < items.size(); i++) {
				int itemID = items.get(i).getItemID();
				sql = "INSERT INTO `order_Items` (`order_ID`, `item_ID`) VALUES ('"+ orderID + "', '"+ itemID + "');";
				stmt = con.createStatement();
				stmt.executeUpdate(sql);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return orderID;

	}
}

