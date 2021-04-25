package com;

import model.Fund;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;

import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Funds")
public class FundService 
{
		Fund fndObj = new Fund();
		
		@GET
		@Path("/")
		@Produces(MediaType.TEXT_HTML)
		public String readFunds()
		{
				return fndObj.readFunds();
		}
		
		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String insertFund(@FormParam("fundId") String fundId,
				@FormParam("fundCode") String fundCode,
				@FormParam("amount") String amount,
				@FormParam("description") String description,
				@FormParam("requestId") String requestId)
		{
			String output = fndObj.insertFund(fundId, fundCode, amount, description, requestId);
			return output;
				
		}
		
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String updateFunds(String fundData)
		{
			//JsonObject fundObject = new JsonParser().parse(fundData).getAsJsonObject();
			JsonObject fundObject = new JsonParser().parse(fundData).getAsJsonObject(); 
			
			String fundId = fundObject.get("fundId").getAsString();
			String fundCode = fundObject.get("fundCode").getAsString();
			String amount = fundObject.get("amount").getAsString();
			String description = fundObject.get("description").getAsString();
			String requestId = fundObject.get("requestId").getAsString();
			
			String output = fndObj.updateFunds(fundId, fundCode, amount, description, requestId);
			return output;
		}
		
		@DELETE
		@Path("/") 
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteFunds(String fundData)
		{
			//Convert the input string to an XML document
			 Document document = Jsoup.parse(fundData, "", Parser.xmlParser());

			//Read the value from the element
			 String fundId = document.select("fundId").text();
			 String output = fndObj.deleteFund(fundId);
			 return output;
		}
}
