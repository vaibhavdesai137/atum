package com.intuit.atum.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;

public enum DataSourceManager {

	INSTANCE;

	private BasicDataSource ds;

	public void init() throws Exception {

		String dbUsername = System.getenv("DB_USERNAME");
		String dbPassword;
		String dbName;
		String dbPort;
		String dbUrl;

		// TODO: Do not expose passwords. Encrypt
		// If env vars are not set, we must be running in local
		if (StringUtils.isBlank(dbUsername)) {
			System.out.println("ENV vars missing, assuming local run...");
			dbUsername = "root";
			dbPassword = "";
			dbName = "atumdb";
			dbPort = "3306";
			dbUrl = "jdbc:mysql://localhost:" + dbPort + "/" + dbName + "?autoReconnect=true";
		} else {
			System.out.println("ENV vars present, running as container...");
			dbUsername = System.getenv("DB_USERNAME");
			dbPassword = System.getenv("DB_PASSWORD");
			dbName = System.getenv("DB_NAME");
			dbPort = System.getenv("DB_PORT");
			dbUrl = System.getenv("DB_URL");
		}

		System.out.println("dbUsername: " + dbUsername);
		System.out.println("dbPassword: " + dbPassword);
		System.out.println("dbName: " + dbName);
		System.out.println("dbPort: " + dbPort);
		System.out.println("dbUrl:" + dbUrl);

		System.out.println("Trying to get a db connection...");
		createDs(dbUsername, dbPassword, dbUrl);
		getConnection();
		System.out.println("Trying to get a db connection...successful");
	}

	private void createDs(String username, String password, String url) {

		ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setUrl(url);

		// the settings below are optional -- dbcp can work with defaults
		ds.setMinIdle(10);
		ds.setMaxTotal(100);
		ds.setTestWhileIdle(true);
		ds.setTestOnBorrow(true);
		ds.setTestOnReturn(false);
		ds.setValidationQuery("SELECT 1");
		ds.setValidationQueryTimeout(30000);
		ds.setTimeBetweenEvictionRunsMillis(5000);
		ds.setInitialSize(10);
		ds.setLogAbandoned(true);
		ds.setMinEvictableIdleTimeMillis(30000);
	}

	public Connection getConnection() throws Exception {

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (Exception e) {
			System.out.println("Exception in getConnection: " + e);
			throw e;
		}
		return conn;
	}

	public void closeConnection(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			System.out.println("Exception in closeConnection: " + e);
		}
	}

	public void shutdown() {
		try {
			if (ds != null) {
				ds.close();
			}
		} catch (Exception e) {
			System.out.println("Exception in shutdown: " + e);
		}
	}

	public void closeQuietly(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			// Ignore
		}
	}

	public void closeQuietly(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			// Ignore
		}
	}

}