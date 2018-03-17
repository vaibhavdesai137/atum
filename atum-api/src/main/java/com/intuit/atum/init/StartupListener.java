package com.intuit.atum.init;

import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.intuit.atum.db.DataSourceManager;

public class StartupListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {

		System.out.println("Initializer starting");
		for (URL url : ((URLClassLoader) (this.getClass().getClassLoader())).getURLs()) {
			System.out.println("URL:" + url);
		}

		try {
			System.out.println("Initializing data sources...");
			DataSourceManager.INSTANCE.init();
			System.out.println("Initializing data sources...done");
		} catch (Exception e) {
			System.out.println("Exception in contextInitialized: " + e);
			System.out.println("Shamelessly aborting...");
			System.exit(1);
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		try {
			System.out.println("Shutting down data sources...");
			DataSourceManager.INSTANCE.shutdown();
			System.out.println("Shutting down data sources...done");
		} catch (Exception e) {
			System.out.println("Exception in contextDestroyed: " + e);
		}
	}

}
