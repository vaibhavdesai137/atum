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
import com.intuit.atum.api.utils.Utils;
import com.intuit.atum.db.DBAccess;

@Path("/members")
public class Members {

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public String getMembers() {
		return Utils.getDummyJson();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String addNewMember() {
		return Utils.getDummyJson();
	}

	@GET
	@Path("/{memberId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getMemberDetails(@PathParam("memberId") Integer memberId) {
		return Utils.getDummyJson();
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("/{memberId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String updateMemberDetails(@PathParam("memberId") Integer memberId) {
		return Utils.getDummyJson();
	}

	@DELETE
	@Path("/{memberId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String removeMember(@PathParam("memberId") Integer memberId) {
		return Utils.getDummyJson();
	}

	@GET
	@Path("/{memberId}/books")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getBooksBorrowedByMember(@PathParam("memberId") Integer memberId) {

		JsonObject result = new JsonObject();
		JsonArray booksArray = new JsonArray();

		try {
			booksArray = DBAccess.INSTANCE.getBooksBorrowedByMember(memberId);
			result.add("booksBorrowed", booksArray);
			result.addProperty("msg", "ok");
		} catch (Exception e) {
			result.add("booksBorrowed", new JsonArray());
			result.addProperty("msg", e.getMessage());
		}

		return result.toString();
	}
}
