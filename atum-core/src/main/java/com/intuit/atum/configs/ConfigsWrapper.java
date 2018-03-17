package com.intuit.atum.configs;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.intuiut.atum.utils.Utils;

public enum ConfigsWrapper {

	CONFIG;

	private Configs model;

	ConfigsWrapper() {

		try {

			String env = Utils.getEnv();

			if (StringUtils.isBlank(env)) {
				throw new Exception("Unknown environment...");
			}

			if (!env.equals("dev") && !env.equals("preprod") && !env.equals("prod")) {
				throw new Exception("Unsupported environment, env: " + env);
			}

			String configFile = Utils.getConfigFilePath(env);
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = classloader.getResourceAsStream(configFile);

			Gson gson = new Gson();
			model = gson.fromJson(IOUtils.toString(is), Configs.class);

		} catch (Exception e) {
			System.out.println("Exception reading configs, ex: " + e);
		}
	}

	public Configs getModel() {
		return model;
	}

}
