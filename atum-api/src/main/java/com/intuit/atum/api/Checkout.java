package com.intuit.atum.api;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.intuit.atum.db.DBAccess;

@Path("/checkout")
public class Checkout {

	@POST
	@Path("/books/{bookId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String checkoutBook(@PathParam("bookId") Integer bookId, @QueryParam("memberId") Integer memberId) {

		JsonObject result = new JsonObject();

		try {
			DBAccess.INSTANCE.checkoutBook(bookId, memberId);
			result.addProperty("checkoutSuccessful", true);
			result.addProperty("msg", "");
		} catch (Exception e) {
			result.addProperty("checkoutSuccessful", false);
			result.addProperty("msg", e.getMessage());
		}

		return result.toString();
	}

	@PUT
	@Path("/books/{bookId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String returnBook(@PathParam("bookId") Integer bookId) {

		JsonObject result = new JsonObject();

		try {
			DBAccess.INSTANCE.returnBook(bookId);
			result.addProperty("returnSuccessful", true);
			result.addProperty("msg", "");
		} catch (Exception e) {
			result.addProperty("returnSuccessful", false);
			result.addProperty("msg", e.getMessage());
		}

		return result.toString();
	}

}
