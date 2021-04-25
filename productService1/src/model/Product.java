package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class Product {
	
	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/product_service", "root", "");
	 }
	 catch (Exception e)
	 {e.printStackTrace();}
	 return con;
	 }
	
	
	
	public String insertProducts(String pCode, String pName, String Price, String description, String category, String quantity, String researcher)
	{
		String output = "";
		
		try {
			 Connection con = connect();
			 if (con == null)
			 {
				 
				 return "Error while connecting to the database for inserting."; 
			 
			 }
			 
			// create a prepared statement
			 String query = " insert into products(`productId`,`productCode`,`productName`,`price`,`description`,`category`,`quantity`, `researcherName`)"
			 + " values (?, ?, ?, ?, ?, ?, ?, ?)";
			 
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, pCode);
			 preparedStmt.setString(3, pName);
			 preparedStmt.setDouble(4, Double.parseDouble(Price));
			 preparedStmt.setString(5, description);
			 preparedStmt.setString(6, category);
			 preparedStmt.setString(7, quantity);
			 preparedStmt.setString(8, researcher);
			 
			// execute the statement
			 
			 preparedStmt.execute();
			 con.close();
			 output = "Product Inserted Successfully!!!!";
		}catch (Exception e) {
			
			// If error occurred while while inserting the product to system.
			 output = "Error!!! while inserting the product to system.";
			 System.err.println(e.getMessage());
		}
			 
			 
			 
		 return output; 
			 
		
	}
	public String readProducts() {
		
		 String output = "";
		 try {
			 
			 Connection con = connect();
			 if (con == null) {
				// If error occurred while while connecting to the database
				 return "Error while connecting to the database for reading.";
			 }
			 
			 
			 output = "<table border='1'><tr><th> Product Code</th><th> Product Name</th>" + "<th> Price</th>" +
					 "<th> Description</th>" + "<th> Category</th>" + "<th> Quantity</th>" + "<th> Researcher</th>" + 
					 "<th>Update</th><th>Remove</th></tr>";
			 
			 
			 String query = "select * from products";
			 
			 
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 // iterate through the rows in the result set
			 
			 
			 while (rs.next())
			 {
				 String productID = Integer.toString(rs.getInt("productId"));
				 String productCode = rs.getString("productCode");
				 String productName = rs.getString("productName");
				 String Price = Double.toString(rs.getDouble("price"));
				 String description = rs.getString("description");
				 String category = rs.getString("category");
				 String quantity = rs.getString("quantity");
				 String researcher = rs.getString("researcherName");
				 
				// Add into the html table
				 
				 output += "<tr><td>" + productCode + "</td>";
				 output += "<td>" + productName + "</td>";
				 output += "<td>" +Price + "</td>";
				 output += "<td>" + description + "</td>";
				 output += "<td>" + category + "</td>";
				 output += "<td>" + quantity + "</td>";
				 output += "<td>" + researcher + "</td>";
				
				 
				 // buttons in the table
				 
				 output += "<td><input name='btnUpdate' "
						 + " type='button' value='Update'></td>"
						 + "<td><form method='post' action='products.jsp'>"
						 + "<input name='btnRemove' "
						 + " type='submit' value='Remove'>"
						 + "<input name='productID' type='hidden' "
						 + " value='" + productID  + "'>" + "</form></td></tr>";
				 
				 
		}
			 
			 con.close();
			 // Complete the html table
			 output += "</table>";
		 }catch (Exception e) {
			// If error occurred while reading the Products table
			 output = "Error!!!!! while reading the Products list.";
			 System.err.println(e.getMessage());
		 }
		 return output;
		
	}
	
	public String updateProducts(String productId , String productCode, String productName, String price, String Description,String category,String quantity, String researcher)
	{
		 String output = "";
		 try {
			 Connection con = connect();
			 if (con == null) {
				 
				 return "Error while connecting to the database for updating."; 
			 }
			 // create a prepared statement
			 String query = "UPDATE products SET productCode=?,productName=?,price=?,description=? ,category=? ,quantity=? ,researcherName=?  WHERE productId =?";
		
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 
			 
			 preparedStmt.setString(1, productCode);
			 preparedStmt.setString(2, productName);
			 preparedStmt.setDouble(3, Double.parseDouble(price));
			 preparedStmt.setString(4, Description);
			 preparedStmt.setString(5, category);
			 preparedStmt.setString(6, quantity);
			 preparedStmt.setString(7, researcher);
			 preparedStmt.setInt(8, Integer.parseInt(productId));
			 preparedStmt.execute();
			 con.close();
			 output = "Product Updated Successfully!!!!!!";
			 
			 
		 } catch (Exception e) {
			 // If error occurred while updating the Product
			 output = "Error!!!! while updating the Product. Please check";
			 System.err.println(e.getMessage());
		 }
		 
		 return output;
	}
	
	
	
	public String deleteProducts(String productId) {
		
		String output = "";
		 try {
			 
			 Connection con = connect();
			 if (con == null) {
				 
				 return "Error!!!!!! while connecting to the database for rempving products.";
			 }
			 
			 // create a prepared statement
			 String query = "delete from products where productId=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			// binding values
			 preparedStmt.setInt(1, Integer.parseInt(productId));
			 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = "Product Deleted from DB successfully";
	} catch (Exception e) {
		 
		 output = "Error!!!! while deleting the Product.";
		 System.err.println(e.getMessage());
		 
	 }
	 return output;
}

	
}
