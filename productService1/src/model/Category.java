package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Category {

	
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
	
	public String insertCategory(String categoryName, String Description)
	 {
		String output = "";
		
		try {
			 Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connecting to the database for inserting."; 
			 
			 }
			 
			// create a prepared statement
			 String query = " insert into category (`categoryID`,`categoryName`,`cDescription`)"
			 + " values (?, ?, ?)";
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, categoryName);
			 preparedStmt.setString(3, Description);
			 
			 preparedStmt.execute();
			 con.close();
			 output = "Category Inserted successfully";
		}catch (Exception e) {
			
			
			 output = "Error!!!! while inserting the Category.";
			 System.err.println(e.getMessage());
		}
		
		 return output;
	 }
	
	
	public String readCategory() {
		
		 String output = "";
		 try {
			 
			 Connection con = connect();
			 if (con == null) {
				 
				 return "Error!!!! while connecting to the database for reading.";
			 }
			 
			 output = "<table border='1'><tr><th>Category ID</th><th>Category Name</th>"  + "<th>Description</th>" +
					 "<th>Update</th><th>Remove</th></tr>";
			 
			 
			 String query = "select * from category";
			 
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 // iterate through the rows in the result set
			 
			 
			 while (rs.next())
			 {
				 String CategoryID = Integer.toString(rs.getInt("categoryID"));
				 String categoryName = rs.getString("categoryName");
				 String Description = rs.getString("cDescription");
				
				 // Add into the html table
				 
				 output += "<tr><td>" + CategoryID + "</td>";
				 output += "<td>" + categoryName + "</td>";
				 output += "<td>" +Description + "</td>";
			
				 
				 
				// buttons
				 
				 output += "<td><input name='btnUpdate' "
						 + " type='button' value='Update'></td>"
						 + "<td><form method='post' action='Category.jsp'>"
						 + "<input name='btnRemove' "
						 + " type='submit' value='Remove'>"
						 + "<input name='CategoryID' type='hidden' "
						 + " value='" + CategoryID  + "'>" + "</form></td></tr>";
			 }

			 
			 con.close();
			 // Complete the html table
			 output += "</table>";
		 }catch (Exception e) {
			 
			 output = "Error while reading the Category.";
			 System.err.println(e.getMessage());
		 }
		 return output;
	}
	
	
	
	
	public String updateCategory(String CategoryID ,String categoryName, String Description)
	{
		 String output = "";
		 try {
			 Connection con = connect();
			 if (con == null) {
				 
				 return "Error while connecting to the database for updating."; 
			 }
			 
			 
			 // create a prepared statement
			 String query = "UPDATE category SET categoryName=?,cDescription=? WHERE categoryID =?";
			 
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setString(1, categoryName);
			 preparedStmt.setString(2, Description);
		
			 preparedStmt.setInt(3, Integer.parseInt(CategoryID));
			 preparedStmt.execute();
			 con.close();
			 output = "Category Updated successfully";
			 
		 	} catch (Exception e) {
			 
			 output = "Error while updating the Category.";
			 System.err.println(e.getMessage());
		 }
		 
		 return output;
	}
	
	public String deleteCategory(String CategoryID) {
		 String output = "";
		 try {
			 
			 Connection con = connect();
			 if (con == null) {
				 
				 return "Error while connecting to the database for deleting.";
			 }
			 
			 
			 // create a prepared statement
			 String query = "delete from category where categoryID=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(CategoryID));
			 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = "Category Deleted successfully";
			 
		 	} catch (Exception e) {
			 
			 output = "Error while deleting the Category.";
			 System.err.println(e.getMessage());
			 
		 }
		 return output;
	}
}
