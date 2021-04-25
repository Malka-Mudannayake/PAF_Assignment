
package model;

import java.sql.*;

import com.google.gson.JsonElement;

public class Order {

	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	// add order to whishlist
	public String insertOrder(String productName, String quantity, String price, String desc, String orderDate) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into orders(`orderID`,`productName`,`quantity`,`price`,`prodDesc`,`orderDate`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, productName);
			preparedStmt.setString(3, quantity);
			preparedStmt.setDouble(4, Double.parseDouble(price));
			preparedStmt.setString(5, desc);
			preparedStmt.setString(6, orderDate);
			// execute the statement

			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the order.";
			System.err.println(e.getMessage());
		}
		return output;
	}

//read order details 
	public String readOrders() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th> Order ID </th> <th>Product Name</th><th>Quantity</th>"
					+ "<th> Price</th>" + "<th>Product Description</th>" + "<th>Order Date</th>"
					+ "<th>Update</th><th>Remove</th><th>Buy</th></tr>";

			String query = "select * from orders";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String orderID = Integer.toString(rs.getInt("orderID"));
				String productName = rs.getString("productName");
				String quantity = rs.getString("quantity");
				String price = Double.toString(rs.getDouble("price"));
				String desc = rs.getString("prodDesc");
				String orderDate = rs.getString("orderDate");

				// Add into the html table
				output += "<tr><td>" + orderID + "</td>";
				output += "<td>" + productName + "</td>";
				output += "<td>" + quantity + "</td>";
				output += "<td>" + price + "</td>";
				output += "<td>" + desc + "</td>";
				output += "<td>" + orderDate + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='orders.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'></td>"
						+ "<td><input name='btnBuy' type='submit' value='Buy'class='btn btn-danger'>"
						+ "<input name='orderID' type='hidden' value='" + orderID + "'>" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the orders.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// update order details
	public String updateOrder(String orderID, String productName, String quantity, String price, String prodDesc,
			String orderDate)

	{
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE orders SET productName=?,quantity=?,price=?,prodDesc=?, orderDate=? WHERE orderID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			
			
			preparedStmt.setString(1, productName);
			preparedStmt.setString(3, quantity);
			preparedStmt.setDouble(2, Double.parseDouble(price));
			preparedStmt.setString(4, prodDesc);
			preparedStmt.setString(5, orderDate);
			preparedStmt.setInt(6, Integer.parseInt(orderID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the order.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// delete order
	public String deleteOrder(String orderID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from orders where orderID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(orderID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the order.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// insert Payments
	public String insertPay(String orderID, String payMethod, String cardType, String cardNo, String SSN,
			String cardExpDate, String amount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into orderpay(`payID`,`orderID`,`payMethod`,`cardType`,`cardNo`,`SSN`,`cardExpDate`,`amount`)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setInt(2, Integer.parseInt(orderID));
			preparedStmt.setString(3, payMethod);
			preparedStmt.setString(4, cardType);
			preparedStmt.setInt(5, Integer.parseInt(cardNo));
			preparedStmt.setInt(6, Integer.parseInt(SSN));
			preparedStmt.setString(7, cardExpDate);
			preparedStmt.setString(8, amount);
			// execute the statement

			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the order payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// read order payements
	public String readOrderPay() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th> pay ID </th> <th>order ID</th><th>Product Name<th>Quantity</th>"
					+ "<th> Price</th>" + "<th>Product Description</th>" + "<th>Order Date</th>"
					+ "<th>Payment Method</th>"
					+ "<th>Card Type</th>" + "<th>Card No</th> <th> SSN </th>"
					+ "<th>Card ExpD Date</th> <th>Amount </th>" + "</tr>";

		
			String query = "select * from orderpay p , orders o where  o.orderID = p.orderID";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String payID = Integer.toString(rs.getInt("payID"));
				String orderID = Integer.toString(rs.getInt("orderID"));
				String payMethod = rs.getString("payMethod");
				String cardType = rs.getString("cardType");
				String cardNo = rs.getString("cardNo");
				String SSN = rs.getString("SSN");
				String cardExpDate = rs.getString("cardExpDate");
				String amount = rs.getString("amount");
				String productName = rs.getString("productName");
				String quantity = rs.getString("quantity");
				String price = Double.toString(rs.getDouble("price"));
				String desc = rs.getString("prodDesc");
				String orderDate = rs.getString("orderDate");

				// Add into the html table
				output += "<tr><td>" + payID + "</td>";
				output += "<td>" + orderID + "</td>";
				output += "<td>" + productName + "</td>";
				output += "<td>" + quantity + "</td>";
				output += "<td>" + price + "</td>";
				output += "<td>" + desc + "</td>";
				output += "<td>" + orderDate + "</td>";
				output += "<td>" + payMethod + "</td>";
				output += "<td>" + cardType + "</td>";
				output += "<td>" + cardNo + "</td>";
				output += "<td>" + SSN + "</td>";
				output += "<td>" + cardExpDate + "</td>";
				output += "<td>" + amount + "</td></tr>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the orders.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
