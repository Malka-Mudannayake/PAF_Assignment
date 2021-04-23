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
	public String insertOrder(@FormParam("productName") String productName, @FormParam("orderQuantity") String orderQuantity,
			@FormParam("price") String price, @FormParam("prodDesc") String prodDesc ,
			@FormParam("orderDate") String orderDate ) {
		String output = orderObj.insertOrder(productName, orderQuantity, price, prodDesc,orderDate);
		return output;
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readOrders() {
		return orderObj.readOrders();
	}





	
}
