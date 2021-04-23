
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
			output = "<table border='1'><tr><th>Product Name</th><th>Price</th>" + "<th>Quantity</th>"
					+ "<th>Product Description</th>" + "<th>Order Date</th>" + "<th>Update</th><th>Remove</th></tr>";

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
				output += "<tr><td>" + productName + "</td>";
				output += "<td>" + quantity + "</td>";
				output += "<td>" + price + "</td>";
				output += "<td>" + desc + "</td>";
				output += "<td>" + orderDate + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='orders.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
						+ "<input name='orderID' type='hidden' value='" + orderID + "'>" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
