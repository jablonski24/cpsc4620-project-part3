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

	/*OrderID = orderID;
	CustID = custID;
	OrderType = orderType;
	Date = date;
	CustPrice = custPrice;
	BusPrice = busPrice;
	this.isComplete = iscomplete;
	PizzaList = new ArrayList<Pizza>();
	DiscountList = new ArrayList<Discount>();*/

	
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
		 *   What that means will be specific to your yimplementatinon.
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
		PreparedStatement os;
		String query;
		if (c.getAddress() == null) {
			query = "INSERT INTO customer (CustID, FName, LName, Phone) VALUES (?,?,?,?)";
		}
		else {
			query = "INSERT INTO customer (CustID, FName, LName, Phone, Address) VALUES (?,?,?,?,?)";
		}
		os = conn.prepareStatement(query);
		os.setInt(1, c.getCustID());
		os.setString(2, c.getFName());
		os.setString(3, c.getLName());
		os.setString(4, c.getPhone());
		if (c.getAddress() != null) {
			os.setString(5, c.getAddress());
		}

		os.executeUpdate();

		conn.close();
		return; 
	}


	// Works
	public static void completeOrder(Order o) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Find the specifed order in the database and mark that order as complete in the database.
		 * 
		 */

		try {
			String query = "UPDATE `order` SET OrderStatus = ? WHERE OrderID = ?";
			PreparedStatement db = conn.prepareStatement(query);
			db.setString(1, "COMPLETED"); // Set OrderStatus to COMPLETED
			db.setInt(2, o.getOrderID()); // Assuming Order has a getOrderID() method that retrieves the order ID

			int updatedRows = db.executeUpdate();
			if (updatedRows > 0) {
				System.out.println("Order #" + o.getOrderID() + " has been marked as completed.");
			} else {
				System.out.println("No order found with ID: " + o.getOrderID());
			}
		} catch (SQLException e) {
			System.out.println("Error updating order status: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
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

		 if(openOnly == true){
			String query = "SELECT * FROM Orders WHERE OrderStatus=\"PENDING\"";
			PreparedStatement db = conn.prepareStatement(query);
			ResultSet returned_arr = db.executeQuery();

			ArrayList<Order> returned_list;

		 }
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return null;
	}
	
	public static Order getLastOrder(){
		/*
		 * Query the database for the LAST order added
		 * then return an Order object for that order.
		 * NOTE...there should ALWAYS be a "last order"!
		 */
		




		 return null;
	}

	public static ArrayList<Order> getOrdersByDate(String date){
		/*
		 * Query the database for ALL the orders placed on a specific date
		 * and return a list of those orders.
		 *  
		 */
		




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

		ArrayList<Topping> toppings = new ArrayList<>(); // Prepare the list to store Topping objects.

		try {
			// SQL query to fetch the ID, Name, and Inventory of each topping, ordered by ID
			//String query = "SELECT * FROM topping WHERE ToppingInventory > 0 ORDER BY ToppingName";
			String query = "SELECT * FROM topping ORDER BY ToppingName";
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			// Loop through each result row and create a Topping object
			while (rs.next()) {
				int toppingId = rs.getInt("ToppingID");
				String toppingName = rs.getString("ToppingName");
				double BusPricePerUnit = rs.getDouble("ToppingPricePerUnit");
				double CustCostPerUnit = rs.getDouble("ToppingCostPerUnit");
				int toppingInventory = rs.getInt("ToppingInventory");
				int minimumInventory = rs.getInt("ToppingMinimum");
				double small = rs.getDouble("ToppingSmall");
				double medium = rs.getDouble("ToppingMedium");
				double large = rs.getDouble("ToppingLarge");
				double xlarge = rs.getDouble("ToppingXLarge");

				// Create a new Topping object and add it to the list
				Topping topping = new Topping(toppingId, toppingName, small, medium, large, xlarge, CustCostPerUnit, BusPricePerUnit, minimumInventory, toppingInventory);
				toppings.add(topping);

			}
		} catch (SQLException ex) {
			System.out.println("SQL Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			// Ensure the database connection is closed
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					System.out.println("Error closing connection: " + ex.getMessage());
				}
			}
		}

		return toppings;

	}

	public static Topping findToppingByName(String name){
		ArrayList<Topping> toppings;
		try {
			toppings = getToppingList();
		} catch (SQLException | IOException e) {
			// Handle the error appropriately
			System.err.println("Error occurred while obtaining topping list: " + e.getMessage());
			// Depending on your error handling, you might want to return null or rethrow a different exception
			return null;
		}

		for (Topping topping : toppings) {
			if (topping.getTopName().equalsIgnoreCase(name)) {
				return topping;
			}
		}

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
		 * Query the database for the base customer price for that size and crust pizza.
		 * 
		*/

		double basePrice = 0.0; // Initialize base price to 0.

		try {
			// Prepare SQL query to get the base customer price for the specified size and crust.
			String query = "SELECT CrustSizePriceCrust FROM crustsize WHERE CrustSizeSize = ? AND CrustSizeCrust = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);

			pstmt.setString(1, size); // Set the size parameter in the query.
			pstmt.setString(2, crust); // Set the crust parameter in the query.

			ResultSet rs = pstmt.executeQuery();

			// If a result is found, set basePrice to the retrieved value.
			if (rs.next()) {
				basePrice = rs.getDouble("CrustSizePriceCrust");
			}
		} catch (SQLException ex) {
			System.out.println("SQL Error: " + ex.getMessage());
			ex.printStackTrace();
			throw ex; // Rethrow the exception to allow calling methods to handle.
		} finally {
			// Ensure the database connection is closed.
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					System.out.println("Error closing connection: " + ex.getMessage());
				}
			}
		}

		return basePrice; // Return the base customer price.

	}

	public static double getBaseBusPrice(String size, String crust) throws SQLException, IOException {
		connect_to_db();
		/* 
		 * Query the database for the base business price for that size and crust pizza.
		 * 
		*/

		double basePrice = 0.0; // Initialize base price to 0.

		try {
			// Prepare SQL query to get the base customer price for the specified size and crust.
			String query = "SELECT CrustSizeCostBus FROM crustsize WHERE CrustSizeSize = ? AND CrustSizeCrust = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);

			pstmt.setString(1, size); // Set the size parameter in the query.
			pstmt.setString(2, crust); // Set the crust parameter in the query.

			ResultSet rs = pstmt.executeQuery();

			// If a result is found, set basePrice to the retrieved value.
			if (rs.next()) {
				basePrice = rs.getDouble("CrustSizeCostBus");
			}
		} catch (SQLException ex) {
			System.out.println("SQL Error: " + ex.getMessage());
			ex.printStackTrace();
			throw ex; // Rethrow the exception to allow calling methods to handle.
		} finally {
			// Ensure the database connection is closed.
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					System.out.println("Error closing connection: " + ex.getMessage());
				}
			}
		}
		return basePrice; // Return the base customer price.

	}

	public static void printInventory() throws SQLException, IOException {
		connect_to_db();
		/*
		 * Queries the database and prints the current topping list with quantities.
		 *
		 * The result should be readable and sorted as indicated in the prompt.
		 */

		// ArrayList grabs toppings that are not out of stock
		ArrayList<Topping> toppingList = getToppingList();

		// Check if the toppings list is not empty
		if (!toppingList.isEmpty()) {
			// Print the header
			System.out.printf("%-10s %-25s %-15s%n", "ID", "Name", "CurINVT");

			// Print each topping's details
			for (Topping topping : toppingList) {
				System.out.printf("%-10d %-25s %-15d%n",
						topping.getTopID(),
						topping.getTopName(),
						topping.getCurINVT());
			}
		} else {
			System.out.println("No toppings available.");
		}

		// Remember to close your connection
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				System.out.println("Error closing connection: " + ex.getMessage());
			}
		}


		
		
		
		
		
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
		String query = "Select FName, LName From customer WHERE CustID=" + CustID + ";";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		
		while(rset.next())
		{
			cname1 = rset.getString(1) + " " + rset.getString(2); 
		}

		/* 
		* an example of the same query using a prepared statement...
		* 
		*/
		String cname2 = "";
		PreparedStatement os;
		ResultSet rset2;
		String query2;
		query2 = "Select FName, LName From customer WHERE CustID=?;";
		os = conn.prepareStatement(query2);
		os.setInt(1, CustID);
		rset2 = os.executeQuery();
		while(rset2.next())
		{
			cname2 = rset2.getString("FName") + " " + rset2.getString("LName"); // note the use of field names in the getSting methods
		}

		conn.close();
		return cname1; // OR cname2
	}

	/*
	 * The next 3 private methods help get the individual components of a SQL datetime object. 
	 * You're welcome to keep them or remove them.
	 */
	private static int getYear(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(0,4));
	}
	private static int getMonth(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(5, 7));
	}
	private static int getDay(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
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