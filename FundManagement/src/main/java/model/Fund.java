package model;

import java.sql.*;
public class Fund 
{
	//method to connect the Database
	
	private Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//credentials
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:8111/dbfunds", "root", "");
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertFund(String fundId, String fundCode, String amount, String description, String requestId)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the databse for inserting!";
			}
			
			String query = " insert into funds(`fundId`, `fundCode`,`amount`,`description`, `requestId`)"
					 + " values (?, ?, ?, ?, ?)"; 
			
			PreparedStatement preparedStmnt = con.prepareStatement(query);
			
			//binding values
			preparedStmnt.setInt(1, 0);
			preparedStmnt.setString(2, fundCode);
			preparedStmnt.setDouble(3, Double.parseDouble(amount));
			preparedStmnt.setString(4, description);
			preparedStmnt.setString(5, requestId);
			
			//execute
			
			preparedStmnt.execute();
			con.close();
			output = "Successfully Inserted";
			
		}
		catch(Exception e)
		{
			output = "Error while inserting the fund!!";
			System.err.println(e.getMessage());
		}
		return output; 
	}
	
	//Read data
	public String readFunds()
	{
			String output = "";
			
			try
			{
					Connection con = connect();
					if (con == null)
					{
						return "Error while connecting to the database for reading data!!"; 
					}
					
					// Prepare the html table to be displayed
					
					output = "<table border='1'><tr><th>Fund Code</th><th>Amount</th>" +
							"<th>Description</th>" +
							"<th>Request ID</th>" +
							"<th>Update</th><th>Remove</th></tr>";
					
					String query = "select * from funds";
					Statement stmnt = con.createStatement();
					ResultSet resultSt = stmnt.executeQuery(query);
		
					// iterate through the rows in the result set
					while (resultSt.next())
					{
							String fundId = Integer.toString(resultSt.getInt("fundId"));
							String fundCode = resultSt.getString("fundCode");
							String amount = Double.toString(resultSt.getDouble("amount"));
							String description = resultSt.getString("description");
							String requestId = resultSt.getString("requestId");
							
							// Add into the html table
							
							output += "<tr><td>" + fundCode + "</td>";
							output += "<td>" + amount + "</td>";
							output += "<td>" + description + "</td>";
							output += "<td>" + requestId + "</td>";
							
							// buttons
							
							output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
											+ "<td><form method='post' action='funds.jsp'>"
											+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
											+ "<input name='fundId' type='hidden' value='" + fundId + "'>" + "</form></td></tr>";
					}
					con.close();
					
					// Complete the html table
					output += "</table>";
		 }
		 catch (Exception e)
		 {
			 output = "Error while reading the funds. Cannot complete the task!!";
			 System.err.println(e.getMessage());
		 }
		 return output; 
	}
	
	//update data
	
	public String updateFunds(String fundId, String fundCode, String amount, String description, String requestId)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			 if (con == null)
			 {
				 return "Cannot process the requested operation!!"; 
			 }
			 
			 // create a prepared statement
			 String query = "UPDATE funds SET fundCode = ?, amount = ?, description = ?, requestId = ? WHERE fundId =?";
			 PreparedStatement preparedStmnt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmnt.setString(1, fundCode);
			 preparedStmnt.setDouble(2, Double.parseDouble(amount));
			 preparedStmnt.setString(3, description);
			 preparedStmnt.setString(4, requestId);
			 preparedStmnt.setInt(5, Integer.parseInt(fundId));
			 
			 // execute the statement
			 preparedStmnt.execute();
			 con.close();
			 output = "Successfully Updated!!";
		}
		catch (Exception e)
		{
			output = "Error while completing the update operation!!";
			System.err.println(e.getMessage());
		}
		return output; 
	}
	
	//delete data
	
	public String deleteFund(String fundId)
	{
		String output = "";
		
		try
		 {
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database for deleting!!"; 
				}
				
				// create a prepared statement
				
				String query = "delete from funds where fundId = ?";
				PreparedStatement preparedStmnt = con.prepareStatement(query);
				
				// binding values
				
				preparedStmnt.setInt(1, Integer.parseInt(fundId));
				
				// execute the statement
				
				preparedStmnt.execute();
				con.close();
				output = "Successfully Deleted";
		 }
		 catch (Exception e)
		 {
			 	output = "Error while deleting the fund!!";
			 	System.err.println(e.getMessage());
		 }
		
		return output;
	}

	/*public String insertFunds(String fundCode, String amount, String description, String userId) {
		// TODO Auto-generated method stub
		return null;
	}*/
}
