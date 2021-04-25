package com;

import model.FundRequests;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.google.gson.*;

import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Fund")
public class FundRequestService 
{
		FundRequests fndrObj = new FundRequests();
	
		//Get method
		@GET
		@Path("/")
		@Produces(MediaType.TEXT_HTML)
		public String readFundRequests()
		{
				return fndrObj.readFundRequests();
		}
	
		//Post Method
		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String insertFundRequest(@FormParam("requestId") String requestId,
			@FormParam("name") String name,
			@FormParam("areaOfExpertise") String areaOfExpertise,
			@FormParam("reSearchExperience") String reSearchExperience,
			@FormParam("estimatedBudget") String estimatedBudget,
			@FormParam("Status") String Status,
			@FormParam("userId") String userId)
		{
				String output = fndrObj.insertFundRequest(requestId, name, areaOfExpertise, reSearchExperience, estimatedBudget, Status, userId);
				return output;
			
		}
		
		//Put method
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String updateFundRequest(String fundRequestData)
		{
			//JsonObject fundObject = new JsonParser().parse(fundData).getAsJsonObject();
			JsonObject fundRequestObject = new JsonParser().parse(fundRequestData).getAsJsonObject(); 
			
			String requestId = fundRequestObject.get("requestId").getAsString();
			String name = fundRequestObject.get("name").getAsString();
			String areaOfExpertise = fundRequestObject.get("areaOfExpertise").getAsString();
			String reSearchExperience = fundRequestObject.get("reSearchExperience").getAsString();
			String estimatedBudget = fundRequestObject.get("estimatedBudget").getAsString();
			String Status = fundRequestObject.get("Status").getAsString();
			String userId = fundRequestObject.get("userId").getAsString();
			
			String output = fndrObj.updateFundRequest(requestId, name, areaOfExpertise, reSearchExperience, estimatedBudget, Status, userId);
			return output;
		}
		
		//Delete Method
		@DELETE
		@Path("/") 
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteFundRequest(String fundRequestData)
		{
			//Converting the input string to an XML document
			 Document document = Jsoup.parse(fundRequestData, "", Parser.xmlParser());

			//Reading values from the element
			 String requestId = document.select("requestId").text();
			 String output = fndrObj.deleteFundRequest(requestId);
			 
			 return output;
		}
}
