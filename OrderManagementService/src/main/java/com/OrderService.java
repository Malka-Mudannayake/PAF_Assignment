package com;

import model.Order;

import java.util.*;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Orders")
public class OrderService {
	Order orderObj = new Order();

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertOrder(@FormParam("productName") String productName,
			@FormParam("orderQuantity") String orderQuantity, @FormParam("price") String price,
			@FormParam("prodDesc") String prodDesc, @FormParam("orderDate") String orderDate) {
		String output = orderObj.insertOrder(productName, orderQuantity, price, prodDesc, orderDate);
		return output;
	}

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readOrders() {
		return orderObj.readOrders();
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateOrder(String orderData) {
		// Convert the input string to a JSON object
		JsonObject orderObj1 = new JsonParser().parse(orderData).getAsJsonObject();
		// Read the values from the JSON object
		String orderID = orderObj1.get("orderID").getAsString();
		String productName = orderObj1.get("productName").getAsString();
		String price = orderObj1.get("price").getAsString();
		String quantity = orderObj1.get("quantity").getAsString();
		String prodDesc = orderObj1.get("prodDesc").getAsString();
		String orderDate = orderObj1.get("orderDate").getAsString();
		String output = orderObj.updateOrder(orderID, productName, price, quantity, prodDesc, orderDate);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteOrder(String orderData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(orderData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String orderID = doc.select("orderID").text();
		String output = orderObj.deleteOrder(orderID);
		return output;
	}

}
