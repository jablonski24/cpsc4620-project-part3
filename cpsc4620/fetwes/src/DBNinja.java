import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

/*
 * This file is where most of your code changes will occur You will write the code to retrieve
 * information from the database, or save information to the database
 * 
 * The class has several hard coded static variables used for the connection, you will need to
 * change those to your connection information
 * 
 * This class also has static string variables for pickup, delivery and dine-in. If your database
 * stores the strings differently (i.e "pick-up" vs "pickup") changing these static variables will
 * ensure that the comparison is checking for the right string in other places in the program. You
 * will also need to use these strings if you store this as boolean fields or an integer.
 * 
 * 
 */

/**
 * A utility class to help add and retrieve information from the database
 */

public final class DBNinja {
	private static Connection conn;

	// Change these variables to however you record dine-in, pick-up and delivery, and sizes and crusts
	// public final static String pickup = "pickup";
	// public final static String delivery = "delivery";
	// public final static String dine_in = "dinein";

	public final static String pickup = "PICKUP";
	public final static String delivery = "DELIVERY";
	public final static String dine_in = "DINEIN";

	public final static String size_s = "Small";
	public final static String size_m = "Medium";
	public final static String size_l = "Large";
	public final static String size_xl = "XLarge";

	public final static String crust_thin = "Thin";
	public final static String crust_orig = "Original";
	public final static String crust_pan = "Pan";
	public final static String crust_gf = "Gluten-Free";



	
	private static boolean connect_to_db() throws SQLException, IOException {

		try {
			conn = DBConnector.make_connection();
			return true;
		} catch (SQLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

	}

	
	public static void addOrder(Order o) throws SQLException, IOException 
	{
		connect_to_db();
		/*
		 * add code to add the order to the DB. Remember that we're not just
		 * adding the order to the order DB table, but we're also recording
		 * the necessary data for the delivery, dinein, and pickup tables
		 * 
		 */
	

		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	public static void addPizza(Pizza p) throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Add the code needed to insert the pizza into into the database.
		 * Keep in mind adding pizza discounts and toppings associated with the pizza,
		 * there are other methods below that may help with that process.
		 * 
		 */
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	
	public static void useTopping(Pizza p, Topping t, boolean isDoubled) throws SQLException, IOException //this method will update toppings inventory in SQL and add entities to the Pizzatops table. Pass in the p pizza that is using t topping
	{
		connect_to_db();
		/*
		 * This method should do 2 two things.
		 * - update the topping inventory every time we use t topping (accounting for extra toppings as well)
		 * - connect the topping to the pizza
		 *   What that means will be specific to your implementatinon.
		 * 
		 * Ideally, you should't let toppings go negative....but this should be dealt with BEFORE calling this method.
		 * 
		 */
		
		
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	
	public static void usePizzaDiscount(Pizza p, Discount d) throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * This method connects a discount with a Pizza in the database.
		 * 
		 * What that means will be specific to your implementatinon.
		 */
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	public static void useOrderDiscount(Order o, Discount d) throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * This method connects a discount with an order in the database
		 * 
		 * You might use this, you might not depending on where / how to want to update
		 * this information in the dabast
		 */
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	public static void addCustomer(Customer c) throws SQLException, IOException {
		connect_to_db();
		/*
		 * This method adds a new customer to the database.
		 * 
		 */

		if (conn == null) {
			System.out.println("null");
		}

		PreparedStatement os;
		String query;
		if (c.getAddress() == null) {
			query = "INSERT INTO customer (CustomerFirstName, CustomerLastName, CustomerPhone) VALUES (?,?,?)";
		}
		else {
			query = "INSERT INTO customer (CustomerFirstName, CustomerLastName, CustomerPhone, CustomerCity) VALUES (?,?,?,?)";
		}
		System.out.println(query);
		os = conn.prepareStatement(query);
		os.setString(1, c.getFName());
		os.setString(2, c.getLName());
		os.setString(3, c.getPhone());
		if (c.getAddress() != null) {
			os.setString(4, c.getAddress());
		}
		os.executeUpdate();

		conn.close();
		return; 
	}

	public static void completeOrder(Order o) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Find the specifed order in the database and mark that order as complete in the database.
		 * 
		 */
		


		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}


	public static ArrayList<Order> getOrders(boolean openOnly) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Return an arraylist of all of the orders.
		 * 	openOnly == true => only return a list of open (ie orders that have not been marked as completed)
		 *           == false => return a list of all the orders in the database
		 * Remember that in Java, we account for supertypes and subtypes
		 * which means that when we create an arrayList of orders, that really
		 * means we have an arrayList of dineinOrders, deliveryOrders, and pickupOrders.
		 * 
		 * Don't forget to order the data coming from the database appropriately.
		 * 
		 */

		ArrayList<Order> returnList = new ArrayList<>();

		PreparedStatement os;
		ResultSet rset;
		String query;
		query = "SELECT \n" +
				"    o.OrderID,\n" +
				"    o.OrderType,\n" +
				"    o.OrderTimestamp,\n" +
				"    o.OrderStatus,\n" +
				"    o.OrderPrice,\n" +
				"    o.OrderCost,\n" +
				"    CASE \n" +
				"        WHEN o.OrderType IN ('DELIVERY', 'PICKUP') THEN c.CustomerID\n" +
				"        ELSE '0' -- Placeholder for DineIn orders\n" +
				"    END AS CustomerID,\n" +
				"    CASE \n" +
				"        WHEN o.OrderType = 'DELIVERY' THEN c.CustomerState\n" +
				"        ELSE NULL\n" +
				"    END AS CustomerState,\n" +
				"    CASE \n" +
				"        WHEN o.OrderType = 'DELIVERY' THEN c.CustomerCity\n" +
				"        ELSE NULL\n" +
				"    END AS CustomerCity,\n" +
				"    CASE \n" +
				"        WHEN o.OrderType = 'DELIVERY' THEN c.CustomerStreet\n" +
				"        ELSE NULL\n" +
				"    END AS CustomerStreet,\n" +
				"    CASE \n" +
				"        WHEN o.OrderType = 'DELIVERY' THEN c.CustomerZip\n" +
				"        ELSE NULL\n" +
				"    END AS CustomerZip\n" +
				"FROM\n" +
				"    `order` o\n" +
				"LEFT JOIN\n" +
				"    (SELECT\n" +
				"         PickUpOrderID AS OrderID,\n" +
				"         PickUpCustomerID AS CustomerID\n" +
				"     FROM\n" +
				"         pickup\n" +
				"     UNION ALL\n" +
				"     SELECT\n" +
				"         DeliveryOrderID AS OrderID,\n" +
				"         DeliveryCustomerID AS CustomerID\n" +
				"     FROM\n" +
				"         delivery\n" +
				"     UNION ALL\n" +
				"     SELECT\n" +
				"         DineInOrderID AS OrderID,\n" +
				"         NULL AS CustomerID -- No customer ID for DineIn\n" +
				"     FROM\n" +
				"         dinein) AS ord_cust ON ord_cust.OrderID = o.OrderID\n" +
				"LEFT JOIN\n" +
				"    customer c ON ord_cust.CustomerID = c.CustomerID\n" +
				"ORDER BY o.OrderTimestamp DESC;";

		os = conn.prepareStatement(query);
		rset = os.executeQuery();
		while(rset.next())
		{
			int orderStatus = (rset.getString("OrderStatus").equals("COMPLETED")) ? 1 : 0;
			if (openOnly) {
				if (orderStatus == 1) {
					continue;
				}
			}

			Order currOrder;
			if (rset.getString("OrderType").equals("PICKUP")) {
				currOrder = new PickupOrder(rset.getInt("OrderID"), rset.getInt("CustomerID"), rset.getString("OrderTimeStamp"), rset.getDouble("OrderPrice"), rset.getDouble("OrderCost"), 1, orderStatus);
			}
			else if (rset.getString("OrderType").equals("DELIVERY")) {
				String addr = "";
				addr += rset.getString("CustomerStreet") + " ";
				addr += rset.getString("CustomerCity") + " ";
				addr += rset.getString("CustomerState") + " ";
				addr += rset.getString("CustomerZip");
				currOrder = new DeliveryOrder(rset.getInt("OrderID"), rset.getInt("CustomerID"), rset.getString("OrderTimeStamp"), rset.getDouble("OrderPrice"), rset.getDouble("OrderCost"), orderStatus, addr);
			}
			else { //Dine in
				currOrder = new DineinOrder(rset.getInt("OrderID"), 0, rset.getString("OrderTimeStamp"), rset.getDouble("OrderPrice"), rset.getDouble("OrderCost"), orderStatus, 1);
			}
			returnList.add(currOrder);
		}

		conn.close();

		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return returnList;
	}
	
	public static Order getLastOrder(){
		/*
		 * Query the database for the LAST order added
		 * then return an Order object for that order.
		 * NOTE...there should ALWAYS be a "last order"!
		 */
		try {
			connect_to_db();
		}
		catch (IOException | SQLException e){
			System.out.println(e);
		}
		PreparedStatement os;
		ResultSet rset;
		String query;
		query = "SELECT \n" +
				"    o.OrderID,\n" +
				"    o.OrderType,\n" +
				"    o.OrderTimestamp,\n" +
				"    o.OrderStatus,\n" +
				"    o.OrderPrice,\n" +
				"    o.OrderCost,\n" +
				"    CASE \n" +
				"        WHEN o.OrderType IN ('DELIVERY', 'PICKUP') THEN c.CustomerID\n" +
				"        ELSE '0' -- Placeholder for DineIn orders\n" +
				"    END AS CustomerID,\n" +
				"    CASE \n" +
				"        WHEN o.OrderType = 'DELIVERY' THEN c.CustomerState\n" +
				"        ELSE NULL\n" +
				"    END AS CustomerState,\n" +
				"    CASE \n" +
				"        WHEN o.OrderType = 'DELIVERY' THEN c.CustomerCity\n" +
				"        ELSE NULL\n" +
				"    END AS CustomerCity,\n" +
				"    CASE \n" +
				"        WHEN o.OrderType = 'DELIVERY' THEN c.CustomerStreet\n" +
				"        ELSE NULL\n" +
				"    END AS CustomerStreet,\n" +
				"    CASE \n" +
				"        WHEN o.OrderType = 'DELIVERY' THEN c.CustomerZip\n" +
				"        ELSE NULL\n" +
				"    END AS CustomerZip\n" +
				"FROM\n" +
				"    `order` o\n" +
				"LEFT JOIN\n" +
				"    (SELECT\n" +
				"         PickUpOrderID AS OrderID,\n" +
				"         PickUpCustomerID AS CustomerID\n" +
				"     FROM\n" +
				"         pickup\n" +
				"     UNION ALL\n" +
				"     SELECT\n" +
				"         DeliveryOrderID AS OrderID,\n" +
				"         DeliveryCustomerID AS CustomerID\n" +
				"     FROM\n" +
				"         delivery\n" +
				"     UNION ALL\n" +
				"     SELECT\n" +
				"         DineInOrderID AS OrderID,\n" +
				"         NULL AS CustomerID -- No customer ID for DineIn\n" +
				"     FROM\n" +
				"         dinein) AS ord_cust ON ord_cust.OrderID = o.OrderID\n" +
				"LEFT JOIN\n" +
				"    customer c ON ord_cust.CustomerID = c.CustomerID\n" +
				"ORDER BY o.OrderTimestamp DESC\n" +
				"LIMIT 1;";

		try {
			os = conn.prepareStatement(query);
			rset = os.executeQuery();
			rset.next();

			int orderStatus = (rset.getString("OrderStatus").equals("COMPLETED")) ? 1 : 0;
			Order currOrder;
			if (rset.getString("OrderType").equals("PICKUP")) {
				currOrder = new PickupOrder(rset.getInt("OrderID"), rset.getInt("CustomerID"), rset.getString("OrderTimeStamp"), rset.getDouble("OrderPrice"), rset.getDouble("OrderCost"), 1, orderStatus);
			}
			else if (rset.getString("OrderType").equals("DELIVERY")) {
				String addr = "";
				addr += rset.getString("CustomerStreet") + " ";
				addr += rset.getString("CustomerCity") + " ";
				addr += rset.getString("CustomerState") + " ";
				addr += rset.getString("CustomerZip");
				currOrder = new DeliveryOrder(rset.getInt("OrderID"), rset.getInt("CustomerID"), rset.getString("OrderTimeStamp"), rset.getDouble("OrderPrice"), rset.getDouble("OrderCost"), orderStatus, addr);
			}
			else { //Dine in
				currOrder = new DineinOrder(rset.getInt("OrderID"), 0, rset.getString("OrderTimeStamp"), rset.getDouble("OrderPrice"), rset.getDouble("OrderCost"), orderStatus, 1);
			}

			conn.close();
			return currOrder;
		}
		catch (SQLException e){
			System.out.println(e);
		}

		 return null;
	}

	public static ArrayList<Order> getOrdersByDate(String date){
		/*
		 * Query the database for ALL the orders placed on a specific date
		 * and return a list of those orders.
		 *  
		 */
		try {
			connect_to_db();
		}
		catch (IOException | SQLException e){
			System.out.println(e);
		}
		PreparedStatement os;
		ResultSet rset;
		String query;
		query = "SELECT \n" +
				"    o.OrderID,\n" +
				"    o.OrderType,\n" +
				"    o.OrderTimestamp,\n" +
				"    o.OrderStatus,\n" +
				"    o.OrderPrice,\n" +
				"    o.OrderCost,\n" +
				"    CASE \n" +
				"        WHEN o.OrderType IN ('DELIVERY', 'PICKUP') THEN c.CustomerID\n" +
				"        ELSE '0' -- Placeholder for DineIn orders\n" +
				"    END AS CustomerID,\n" +
				"    CASE \n" +
				"        WHEN o.OrderType = 'DELIVERY' THEN c.CustomerState\n" +
				"        ELSE NULL\n" +
				"    END AS CustomerState,\n" +
				"    CASE \n" +
				"        WHEN o.OrderType = 'DELIVERY' THEN c.CustomerCity\n" +
				"        ELSE NULL\n" +
				"    END AS CustomerCity,\n" +
				"    CASE \n" +
				"        WHEN o.OrderType = 'DELIVERY' THEN c.CustomerStreet\n" +
				"        ELSE NULL\n" +
				"    END AS CustomerStreet,\n" +
				"    CASE \n" +
				"        WHEN o.OrderType = 'DELIVERY' THEN c.CustomerZip\n" +
				"        ELSE NULL\n" +
				"    END AS CustomerZip\n" +
				"FROM\n" +
				"    `order` o\n" +
				"LEFT JOIN\n" +
				"    (SELECT\n" +
				"         PickUpOrderID AS OrderID,\n" +
				"         PickUpCustomerID AS CustomerID\n" +
				"     FROM\n" +
				"         pickup\n" +
				"     UNION ALL\n" +
				"     SELECT\n" +
				"         DeliveryOrderID AS OrderID,\n" +
				"         DeliveryCustomerID AS CustomerID\n" +
				"     FROM\n" +
				"         delivery\n" +
				"     UNION ALL\n" +
				"     SELECT\n" +
				"         DineInOrderID AS OrderID,\n" +
				"         NULL AS CustomerID -- No customer ID for DineIn\n" +
				"     FROM\n" +
				"         dinein) AS ord_cust ON ord_cust.OrderID = o.OrderID\n" +
				"LEFT JOIN\n" +
				"    customer c ON ord_cust.CustomerID = c.CustomerID\n" +
				"WHERE DATE(o.OrderTimeStamp) = ?;";

		try {
			os = conn.prepareStatement(query);
			os.setString(1, date);
			rset = os.executeQuery();

			ArrayList<Order> returnList = new ArrayList<>();
			while(rset.next())
			{
				int orderStatus = (rset.getString("OrderStatus").equals("COMPLETED")) ? 1 : 0;

				Order currOrder;
				if (rset.getString("OrderType").equals("PICKUP")) {
					currOrder = new PickupOrder(rset.getInt("OrderID"), rset.getInt("CustomerID"), rset.getString("OrderTimeStamp"), rset.getDouble("OrderPrice"), rset.getDouble("OrderCost"), 1, orderStatus);
				}
				else if (rset.getString("OrderType").equals("DELIVERY")) {
					String addr = "";
					addr += rset.getString("CustomerStreet") + " ";
					addr += rset.getString("CustomerCity") + " ";
					addr += rset.getString("CustomerState") + " ";
					addr += rset.getString("CustomerZip");
					currOrder = new DeliveryOrder(rset.getInt("OrderID"), rset.getInt("CustomerID"), rset.getString("OrderTimeStamp"), rset.getDouble("OrderPrice"), rset.getDouble("OrderCost"), orderStatus, addr);
				}
				else { //Dine in
					currOrder = new DineinOrder(rset.getInt("OrderID"), 0, rset.getString("OrderTimeStamp"), rset.getDouble("OrderPrice"), rset.getDouble("OrderCost"), orderStatus, 1);
				}
				returnList.add(currOrder);
			}


			conn.close();
			return returnList;

		}
		catch (SQLException e){
			System.out.println(e);
		}

		 return null;
	}
		
	public static ArrayList<Discount> getDiscountList() throws SQLException, IOException {
		connect_to_db();
		/* 
		 * Query the database for all the available discounts and 
		 * return them in an arrayList of discounts.
		 * 
		*/
		
		
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return null;
	}

	public static Discount findDiscountByName(String name){
		/*
		 * Query the database for a discount using it's name.
		 * If found, then return an OrderDiscount object for the discount.
		 * If it's not found....then return null
		 *  
		 */




		 return null;
	}


	public static ArrayList<Customer> getCustomerList() throws SQLException, IOException {
		connect_to_db();
		/*
		 * Query the data for all the customers and return an arrayList of all the customers. 
		 * Don't forget to order the data coming from the database appropriately.
		 * 
		*/


		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return null;
	}

	public static Customer findCustomerByPhone(String phoneNumber){
		/*
		 * Query the database for a customer using a phone number.
		 * If found, then return a Customer object for the customer.
		 * If it's not found....then return null
		 *  
		 */
		




		 return null;
	}


	public static ArrayList<Topping> getToppingList() throws SQLException, IOException {
		connect_to_db();
		/*
		 * Query the database for the aviable toppings and 
		 * return an arrayList of all the available toppings. 
		 * Don't forget to order the data coming from the database appropriately.
		 * 
		 */

		

		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return null;
	}

	public static Topping findToppingByName(String name){
		/*
		 * Query the database for the topping using it's name.
		 * If found, then return a Topping object for the topping.
		 * If it's not found....then return null
		 *  
		 */
		




		 return null;
	}


	public static void addToInventory(Topping t, double quantity) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Updates the quantity of the topping in the database by the amount specified.
		 * 
		 * */


		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	public static double getBaseCustPrice(String size, String crust) throws SQLException, IOException {
		connect_to_db();
		/* 
		 * Query the database fro the base customer price for that size and crust pizza.
		 * 
		*/
		
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return 0.0;
	}

	public static double getBaseBusPrice(String size, String crust) throws SQLException, IOException {
		connect_to_db();
		/* 
		 * Query the database fro the base business price for that size and crust pizza.
		 * 
		*/
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return 0.0;
	}

	public static void printInventory() throws SQLException, IOException {
		connect_to_db();
		/*
		 * Queries the database and prints the current topping list with quantities.
		 *  
		 * The result should be readable and sorted as indicated in the prompt.
		 * 
		 */


		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION


	}
	
	public static void printToppingPopReport() throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Prints the ToppingPopularity view. Remember that this view
		 * needs to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 * 
		 * The result should be readable and sorted as indicated in the prompt.
		 * 
		 */


		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	public static void printProfitByPizzaReport() throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Prints the ProfitByPizza view. Remember that this view
		 * needs to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 * 
		 * The result should be readable and sorted as indicated in the prompt.
		 * 
		 */
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	public static void printProfitByOrderType() throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Prints the ProfitByOrderType view. Remember that this view
		 * needs to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 * 
		 * The result should be readable and sorted as indicated in the prompt.
		 * 
		 */
		
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION	
	}
	
	
	
	public static String getCustomerName(int CustID) throws SQLException, IOException
	{
	/*
		 * This is a helper method to fetch and format the name of a customer
		 * based on a customer ID. This is an example of how to interact with 
		 * your database from Java.  It's used in the model solution for this project...so the code works!
		 * 
		 * OF COURSE....this code would only work in your application if the table & field names match!
		 *
		 */

		 connect_to_db();

		/* 
		 * an example query using a constructed string...
		 * remember, this style of query construction could be subject to sql injection attacks!
		 * 
		 */
		String cname1 = "";
		String query = "Select CustomerFirstName, CustomerLastName From customer WHERE CustomerID=" + CustID + ";";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);

		if (!rset.next()) {
			cname1 = "INSTORE customer";
		}
		else {
			do {
				cname1 = rset.getString(1) + " " + rset.getString(2);
			} while (rset.next());  // Continue if there are more rows
		}

		/* 
		* an example of the same query using a prepared statement...
		* 
		*/
//		String cname2 = "";
//		PreparedStatement os;
//		ResultSet rset2;
//		String query2;
//		query2 = "Select FName, LName From customer WHERE CustID=?;";
//		os = conn.prepareStatement(query2);
//		os.setInt(1, CustID);
//		rset2 = os.executeQuery();
//		while(rset2.next())
//		{
//			cname2 = rset2.getString("FName") + " " + rset2.getString("LName"); // note the use of field names in the getSting methods
//		}

		conn.close();
		return cname1; // OR cname2
	}

	/*
	 * The next 3 private methods help get the individual components of a SQL datetime object. 
	 * You're welcome to keep them or remove them.
	 */
	public static int getYear(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(0,4));
	}
	public static int getMonth(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(5, 7));
	}
	public static int getDay(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(8, 10));
	}

	public static boolean checkDate(int year, int month, int day, String dateOfOrder)
	{
		if(getYear(dateOfOrder) > year)
			return true;
		else if(getYear(dateOfOrder) < year)
			return false;
		else
		{
			if(getMonth(dateOfOrder) > month)
				return true;
			else if(getMonth(dateOfOrder) < month)
				return false;
			else
			{
				if(getDay(dateOfOrder) >= day)
					return true;
				else
					return false;
			}
		}
	}


}