package model;

import java.sql.*;

public class FundRequests 
{

	//Creating the db connection
	
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
	//insert detail for fund request
	public String insertFundRequest(String requestId, String name, String areaOfExpertise, String reSearchExperience, String estimatedBudget, String Status, String userId)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the databse for inserting!";
			}
			
			String query = " insert into fundrequests(`requestId`, `name`,`areaOfExpertise`,`reSearchExperience`, `estimatedBudget`,  `Status`,  `userId`)"
					 + " values (?, ?, ?, ?, ?, ?, ?)"; 
			
			PreparedStatement preparedStmnt = con.prepareStatement(query);
			
			//binding values
			preparedStmnt.setInt(1, 0);
			preparedStmnt.setString(2, name);
			preparedStmnt.setString(3, areaOfExpertise);
			preparedStmnt.setString(4, reSearchExperience);
			preparedStmnt.setDouble(5, Double.parseDouble(estimatedBudget));
			preparedStmnt.setString(6, Status);
			preparedStmnt.setString(7, userId);
			
			//execute
			
			preparedStmnt.execute();
			con.close();
			output = "Successfully Inserted!!";
			
		}
		catch(Exception e)
		{
			output = "Error while inserting the details!!";
			System.err.println(e.getMessage());
		}
		return output; 
	}
	
	//read data from Fund Requests
	
		public String readFundRequests()
		{
				String output = "";
				
				try
				{
						Connection con = connect();
						if (con == null)
						{
							return "Error while connecting to the database for reading fund request data!!"; 
						}
						
						// Prepare the html table to be displayed
						
						output = "<table border='1'><tr><th>Name</th><th>Area Of Expertise</th>" +
								"<th>Research Experience</th>" +
								"<th>Estimated Budget</th>" +
								"<th>Status</th><th>User ID</th></tr>";
						
						String query = "select * from fundrequests";
						Statement stmnt = con.createStatement();
						ResultSet resultSt = stmnt.executeQuery(query);
			
						// iterate through the rows in the result set
						while (resultSt.next())
						{
								String requestId = Integer.toString(resultSt.getInt("requestId"));
								String name = resultSt.getString("name");
								String areaOfExpertise = resultSt.getString("areaOfExpertise");
								String reSearchExperience = resultSt.getString("reSearchExperience");
								String estimatedBudget = Double.toString(resultSt.getDouble("estimatedBudget"));
								String Status = resultSt.getString("Status");
								String userId = Integer.toString(resultSt.getInt("userId"));;
								
								// Add into the html table
								
								output += "<tr><td>" + name + "</td>";
								output += "<td>" + areaOfExpertise + "</td>";
								output += "<td>" + reSearchExperience + "</td>";
								output += "<td>" + estimatedBudget + "</td>";
								output += "<td>" + Status + "</td>";
								output += "<td>" + userId + "</td>";
								
								// buttons
								
								output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
												+ "<td><form method='post' action='fundrequest.jsp'>"
												+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
												+ "<input name='fundId' type='hidden' value='" + requestId + "'>" + "</form></td></tr>";
						}
						con.close();
						
						// Complete the html table
						output += "</table>";
			 }
			 catch (Exception e)
			 {
				 output = "Error while reading the fund requests. Cannot complete the task!!";
				 System.err.println(e.getMessage());
			 }
			 return output; 
		}
		
		//edit fund request details
		
		public String updateFundRequest(String requestId, String name, String areaOfExpertise, String reSearchExperience, String estimatedBudget, String Status, String userId)
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
				 String query = "UPDATE fundrequests SET name = ?, areaOfExpertise = ?, reSearchExperience = ?, estimatedBudget = ?, Status = ?, userId = ?  WHERE requestId =?";
				 PreparedStatement preparedStmnt = con.prepareStatement(query);
				 
				 // binding values
				 preparedStmnt.setString(1, name);
				 preparedStmnt.setString(2, areaOfExpertise);
				 preparedStmnt.setString(3, reSearchExperience);
				 preparedStmnt.setDouble(4, Double.parseDouble(estimatedBudget));
				 preparedStmnt.setString(5, Status);
				 preparedStmnt.setInt(6, Integer.parseInt(userId));
				 preparedStmnt.setInt(7, Integer.parseInt(requestId));
				 
				 // execute the statement
				 preparedStmnt.execute();
				 con.close();
				 output = "Successfully Updated fund request details!!";
			}
			catch (Exception e)
			{
				output = "Error while completing the update operation!!";
				System.err.println(e.getMessage());
			}
			return output; 
		}
		
		//delete fund requests
		
		public String deleteFundRequest(String requestId)
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
					
					String query = "delete from fundrequests where requestId = ?";
					PreparedStatement preparedStmnt = con.prepareStatement(query);
					
					// binding values
					
					preparedStmnt.setInt(1, Integer.parseInt(requestId));
					
					// execute the statement
					
					preparedStmnt.execute();
					con.close();
					output = "Successfully Deleted Fund Request!!";
			 }
			 catch (Exception e)
			 {
				 	output = "Error while deleting the fund request!!";
				 	System.err.println(e.getMessage());
			 }
			
			return output;
		}
}
