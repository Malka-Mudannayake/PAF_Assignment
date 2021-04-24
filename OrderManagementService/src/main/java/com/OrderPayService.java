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

@Path("/Pay")
public class OrderPayService {
	Order orderObj = new Order();

	//Order Payments
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPay(@FormParam("orderID") String orderID,
			@FormParam("payMethod") String payMethod, @FormParam("cardType") String cardType,
			@FormParam("cardNo") String cardNo, @FormParam("SSN") String SSN , @FormParam("cardExpDate") String cardExpDate,
			@FormParam("amount") String amount)  {
		String output = orderObj.insertPay(orderID, payMethod, cardType, cardNo, SSN,cardExpDate,amount);
		return output;
	}
}
