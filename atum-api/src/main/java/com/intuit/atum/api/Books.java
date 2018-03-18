package com.intuit.atum.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.intuit.atum.api.models.CheckoutRequest;
import com.intuit.atum.api.utils.Utils;
import com.intuit.atum.db.DBAccess;

@Path("/books")
public class Books {

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public String getBooks() {
		return Utils.getDummyJson();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String createNewBook() {
		return Utils.getDummyJson();
	}

	@GET
	@Path("/{bookId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getBookDetails(@PathParam("bookId") Integer bookId) {
		return Utils.getDummyJson();
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("/{bookId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String updateBookDetails(@PathParam("bookId") Integer bookId) {
		return Utils.getDummyJson();
	}

	@DELETE
	@Path("/{bookId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String deleteBook(@PathParam("bookId") Integer bookId) {
		return Utils.getDummyJson();
	}

	@GET
	@Path("/title/{bookTitle}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getBookDetailsByTitle(@PathParam("bookTitle") String bookTitle) {

		JsonObject result = new JsonObject();
		JsonArray booksArray = new JsonArray();

		try {
			booksArray = DBAccess.INSTANCE.getBookDetailsByTitle(bookTitle);
			result.add("books", booksArray);
			result.addProperty("msg", "ok");
		} catch (Exception e) {
			result.add("books", new JsonArray());
			result.addProperty("msg", e.getMessage());
		}

		return result.toString();
	}

	@POST
	@Path("/{bookId}/action/checkout")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String checkoutBook(@PathParam("bookId") Integer bookId, CheckoutRequest checkoutRequest) {

		// TODO: Perform validations on input json
		int memberId = checkoutRequest.getMemberId();
		String notes = checkoutRequest.getNotes();
		String checkoutDate = checkoutRequest.getCheckoutDate();
		String expectedReturnDate = checkoutRequest.getExpectedReturnDate();

		JsonObject result = new JsonObject();

		try {
			DBAccess.INSTANCE.checkoutBook(bookId, memberId, notes, checkoutDate, expectedReturnDate);
			result.addProperty("checkoutSuccessful", true);
			result.addProperty("msg", "ok");
		} catch (Exception e) {
			result.addProperty("checkoutSuccessful", false);
			result.addProperty("msg", e.getMessage());
		}

		return result.toString();
	}

	@POST
	@Path("/{bookId}/action/return")
	@Produces({ MediaType.APPLICATION_JSON })
	public String returnBook(@PathParam("bookId") Integer bookId) {

		JsonObject result = new JsonObject();

		try {
			DBAccess.INSTANCE.returnBook(bookId);
			result.addProperty("returnSuccessful", true);
			result.addProperty("msg", "ok");
		} catch (Exception e) {
			result.addProperty("returnSuccessful", false);
			result.addProperty("msg", e.getMessage());
		}

		return result.toString();
	}
}
