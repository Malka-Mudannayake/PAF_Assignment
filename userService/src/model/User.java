package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class User {
	
	
	
	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
	 }
	 catch (Exception e)
	 {e.printStackTrace();}
	 return con;
	 }


	public String insetUser(String username, String name, String phone, String email, String password)
	 {
		String output = "";
		
		try {
			 Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connecting to the database for inserting."; 
			 
			 }
	
			 
			// create a prepared statement
			 String query = " insert into users2(`userID`,`username`,`name`,`phone`,`email`,`password`)"
			 + " values (?, ?, ?, ?, ?, ?)";
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
	
			 // binding values
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, username);
			 preparedStmt.setString(3, name);
			
			 preparedStmt.setString(4, phone);
			 preparedStmt.setString(5, email);
			 preparedStmt.setString(6, password);
			 
			// execute the statement
			 
			 preparedStmt.execute();
			 con.close();
			 output = " Inserted successfully";
			 
		}catch (Exception e) {
			
			
			 output = "Error while inserting ";
			 System.err.println(e.getMessage());
		}
		
		 return output;
	 }
	
	
	
	public String readUser() {
		
		 String output = "";
		 try {
			 
			 Connection con = connect();
			 if (con == null) {
				 
				 return "Error while connecting to the database for reading.";
			 }
			 
			 output = "<table border='1'><tr><th>User Name</th><th>Name</th>" + "<th>Phone number</th>" +
					 "<th>Email</th>" + "<th>Password</th>" + 
					 "<th>Update</th><th>Remove</th></tr>";
			 
			 	String query = "select * from users2";
			 
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 // iterate through the rows in the result set
			 
			 
			 
			 while (rs.next())
			 {
				 String userID = Integer.toString(rs.getInt("userID"));
				 String username = rs.getString("username");
				 String name = rs.getString("name");
				
				 String phone = rs.getString("phone");
				 String email = rs.getString("email");
				 String password = rs.getString("password");
				 
				// Add into the html table
			 
				 
				 
				 output += "<tr><td>" + username + "</td>";
				 output += "<td>" + name + "</td>";
				 output += "<td>" +phone + "</td>";
				 output += "<td>" + email + "</td>";
				 output += "<td>" + password + "</td>";
				 
				 
// buttons
				 
				 output += "<td><input name='btnUpdate' "
						 + " type='button' value='Update'></td>"
						 + "<td><form method='post' action='user.jsp'>"
						 + "<input name='btnRemove' "
						 + " type='submit' value='Remove'>"
						 + "<input name='userID' type='hidden' "
						 + " value='" + userID  + "'>" + "</form></td></tr>";
				 
 }
			 
			 con.close();
			 // Complete the html table
			 output += "</table>";
		 }catch (Exception e) {
			 
			 output = "Error while reading .";
			 System.err.println(e.getMessage());
		 }
		 return output;
	}
	
	
	public String updateUser(String userID , String username, String name, String phone, String email, String password)
	{
		 String output = "";
		 try {
			 Connection con = connect();
			 if (con == null) {
				 
				 return "Error while connecting to the database for updating."; 
			 }
			 String query = "UPDATE users2 SET username=?,name=?,phone=?,email=? ,password=?   WHERE userID =?";
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 preparedStmt.setString(1, username);
			 preparedStmt.setString(2, name);
			
			 preparedStmt.setString(3, phone);
			 preparedStmt.setString(4, email);
			 preparedStmt.setString(5, password);
			 preparedStmt.setInt(6, Integer.parseInt(userID));
			 
			 preparedStmt.execute();
			 con.close();
			 output = " Updated successfully";
		 } catch (Exception e) {
			 
			 output = "Error while updating .";
			 System.err.println(e.getMessage());
		 }
		 
		 return output;
	}
	
	public String deleteUser(String userID) {
		 String output = "";
		 try {
			 
			 Connection con = connect();
			 if (con == null) {
				 
				 return "Error while connecting to the database for deleting.";
			 }
			 
			 // create a prepared statement
			 String query = "delete from users2 where userID=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(userID));
			 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = " Deleted successfully";
		 } catch (Exception e) {
			 
			 output = "Error while deleting the .";
			 System.err.println(e.getMessage());
			 
		 }
		 return output;
	}
	
	
}
