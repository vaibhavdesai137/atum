package com.intuiut.atum.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

	public static String getEnv() throws Exception {

		Properties prop = new Properties();
		InputStream input = null;
		String env;

		try {
			input = new FileInputStream("/intuit/config/env.properties");
			prop.load(input);
			env = prop.getProperty("env");
		} catch (Exception e) {
			throw e;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return env;
	}

	public static String getConfigFilePath(String env) {
		return "configs/" + env + ".json";
	}

}
