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

import model.Category;
@Path("/Category") 
public class CategoryService {
	
	Category categoryObj = new Category();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readCategory()
	 {
	 return categoryObj.readCategory();
	 } 
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insetCategory(@FormParam("categoryName") String categoryName,
	 @FormParam("cDescription") String Description)

	{
	 String output = categoryObj.insertCategory(categoryName, Description);
	return output;
	}
	
	
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCategory(String CategoryData)
	{
	//Convert the input string to a JSON object
	 JsonObject CategObject = new JsonParser().parse(CategoryData).getAsJsonObject();
	//Read the values from the JSON object
	 String CategoryID = CategObject.get("categoryID").getAsString();
	 String categoryName = CategObject.get("categoryName").getAsString();
	 String Description = CategObject.get("cDescription").getAsString();
	 String output = categoryObj.updateCategory(CategoryID, categoryName, Description);
	return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCategory(String CategoryData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(CategoryData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String CategoryID = doc.select("categoryID").text();
	 String output = categoryObj.deleteCategory(CategoryID);
	return output;
	}
	

}
