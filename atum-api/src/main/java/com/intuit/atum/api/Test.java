package com.intuit.atum.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;

@Path("/test")
public class Test {

	@Context
	private HttpServletRequest httpRequest;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public String test() {
		try {
			JsonObject json = new JsonObject();
			json.addProperty("howdy", "hello");
			return json.toString();
		} catch (Exception e) {
			return null;
		}
	}
}
