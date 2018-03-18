package com.intuit.atum.api.utils;

import com.google.gson.JsonObject;

public class Utils {

	public static String getDummyJson() {
		JsonObject result = new JsonObject();
		result.addProperty("msg", "Not Yet Implemented");
		return result.toString();
	}

}
