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

import model.Product;

@Path("/Product") 
public class ProductService {

	Product productObject = new Product();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readProducts()
	 {
	 return productObject.readProducts();
	 } 
	
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertProducts(@FormParam("productCode") String productCode,
	 @FormParam("productName") String productName,
	 @FormParam("price") String price,
	 @FormParam("description") String description,
	 @FormParam("category") String category,
	 @FormParam("quantity") String quantity,
	 @FormParam("researcherName") String researcher)
	{
	 String output = productObject.insertProducts(productCode, productName, price, description,category,quantity,researcher);
	return output;
	}

	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateProducts(String ProductData)
	{
	//Convert the input string to a JSON object
	 JsonObject productObject1 = new JsonParser().parse(ProductData).getAsJsonObject();
	//Read the values from the JSON object
	 String productId = productObject1.get("productId").getAsString();
	 String productCode = productObject1.get("productCode").getAsString();
	 String productName = productObject1.get("productName").getAsString();
	 String price = productObject1.get("price").getAsString();
	 String description = productObject1.get("description").getAsString();
	 String category = productObject1.get("category").getAsString();
	 String quantity = productObject1.get("quantity").getAsString();
	 String researcher = productObject1.get("researcherName").getAsString();
	 String output = productObject.updateProducts(productId, productCode, productName, price, description,category,quantity,researcher);
	return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProducts(String productData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(productData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String productId = doc.select("productId").text();
	 String output = productObject.deleteProducts(productId);
	return output;
	}

	
	
}
