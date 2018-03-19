package com.intuit.atum;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intuit.atum.api.models.CheckoutRequest;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class AtumApiIT {

	// PreProd endpoint
	String ppUrl = "http://159.89.136.190:8080";

	@Test
	public void testHealth() {

		try {

			String url = ppUrl + "/health";
			Client client = Client.create();
			WebResource webResource = client.resource(url);
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

	// 1. Test checkout is successful
	// 2. Test book shows as checkedout for that member
	// 3. Test return is successful
	@Test
	public void testEndToEnd() {

		try {

			Client client = Client.create();
			WebResource webResource;
			ClientResponse resp;
			String output;
			String url;
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = new JsonObject();

			// 1. Test checkout is successful
			int bookId = 1;
			int memberId = 1;
			CheckoutRequest payload = new CheckoutRequest();
			payload.setMemberId(memberId);
			payload.setNotes("");
			payload.setExpectedReturnDate("foobar");
			url = ppUrl + "/books/" + bookId + "/action/checkout";

			webResource = client.resource(url);
			resp = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, payload.toString());

			if (resp.getStatus() != 200) {
				System.out.println("Failed: HTTP error code: " + resp.getStatus());
				Assert.fail();
			}

			output = resp.getEntity(String.class);
			jsonObject = parser.parse(output).getAsJsonObject();
			Assert.assertEquals(true, jsonObject.get("checkoutSuccessful").getAsBoolean());
			Assert.assertEquals("ok", jsonObject.get("msg").getAsString());

			// 2. Test book shows as checkedout
			url = ppUrl + "/members/" + memberId + "/books";

			webResource = client.resource(url);
			resp = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (resp.getStatus() != 200) {
				System.out.println("Failed: HTTP error code: " + resp.getStatus());
				Assert.fail();
			}

			output = resp.getEntity(String.class);
			jsonObject = parser.parse(output).getAsJsonObject();
			Assert.assertNotNull(jsonObject.get("booksBorrowed").getAsJsonArray().get(0).getAsJsonObject());
			Assert.assertEquals("ok", jsonObject.get("msg").getAsString());

			// 3. Test return is successful
			url = ppUrl + "/books/" + bookId + "/action/return";

			webResource = client.resource(url);
			resp = webResource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, null);

			if (resp.getStatus() != 200) {
				System.out.println("Failed: HTTP error code: " + resp.getStatus());
				Assert.fail();
			}

			output = resp.getEntity(String.class);
			jsonObject = parser.parse(output).getAsJsonObject();
			Assert.assertEquals(true, jsonObject.get("returnSuccessful").getAsBoolean());
			Assert.assertEquals("ok", jsonObject.get("msg").getAsString());

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

}
