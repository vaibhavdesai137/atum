package com.intuit.atum.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;

@Path("/health")
public class Health {

	// TODO: Add some meaningful health checks
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public String getHealthStatus() {
		JsonObject json = new JsonObject();
		json.addProperty("health", "ok");
		return json.toString();
	}
}
