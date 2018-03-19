package com.intuit.atum;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class AtumApiIT {

	// PreProd endpoint
	String ppUrl = "http://159.89.136.190:8080";

	@Test
	public void testHealth() {

		try {

			Client client = Client.create();
			WebResource webResource = client.resource(ppUrl + "/health");
			ClientResponse resp = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (resp.getStatus() != 200) {
				System.out.println("Failed: HTTP error code: " + resp.getStatus());
				Assert.fail();
			}

			String output = resp.getEntity(String.class);
			JsonParser parser = new JsonParser();
			JsonObject o = parser.parse(output).getAsJsonObject();

			Assert.assertEquals("ok", o.get("health").getAsString());

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testCheckout() {

		try {

			int bookId = 1;
			JsonObject payload = new JsonObject();
			payload.addProperty("memberId", 1);
			payload.addProperty("notes", "");
			payload.addProperty("expectedReturnDate", "foo-bar");

			Client client = Client.create();
			WebResource webResource = client.resource(ppUrl + "/books/" + bookId + "/actions/checkout");
			ClientResponse resp = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class);

			if (resp.getStatus() != 200) {
				System.out.println("Failed: HTTP error code: " + resp.getStatus());
				Assert.fail();
			}

			String output = resp.getEntity(String.class);
			JsonParser parser = new JsonParser();
			JsonObject o = parser.parse(output).getAsJsonObject();

			Assert.assertEquals(true, o.get("checkoutSuccessful").getAsBoolean());
			Assert.assertEquals("ok", o.get("msg").getAsString());

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

}
