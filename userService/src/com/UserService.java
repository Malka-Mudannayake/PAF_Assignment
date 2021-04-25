package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.User;

@Path("/User") 
public class UserService {

	
	User userObj = new User();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readUser()
	 {
	 return userObj.readUser();
	 } 
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insetUser(@FormParam("username") String username,
	 @FormParam("name") String name,
	 @FormParam("phone") String phone,
	 @FormParam("email") String email,
	 @FormParam("password") String password)
	
	{
	 String output = userObj.insetUser(username, name, phone, email,password);
	return output;
	}
	
	
	
	
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateUser(String UsertData)
	{
	//Convert the input string to a JSON object
	 JsonObject UObject = new JsonParser().parse(UsertData).getAsJsonObject();
	//Read the values from the JSON object
	 String userID = UObject.get("userID").getAsString();
	 String username = UObject.get("username").getAsString();
	 String name = UObject.get("name").getAsString();
	 String phone = UObject.get("phone").getAsString();
	 String email = UObject.get("email").getAsString();
	 String password = UObject.get("password").getAsString();
	
	 String output = userObj.updateUser(userID, username, name, phone, email,password);
	return output;
	}
	
	
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteUser(String userData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(userData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String userID = doc.select("userID").text();
	 String output = userObj.deleteUser(userID);
	return output;
	}
}
