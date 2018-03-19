package com.intuit.atum.api.models;

import com.google.gson.Gson;

public class CheckoutRequest {

	private Integer memberId;
	private String notes;
	private String expectedReturnDate;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getExpectedReturnDate() {
		return expectedReturnDate;
	}

	public void setExpectedReturnDate(String expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
