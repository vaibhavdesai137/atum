package com.intuit.atum.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

import com.intuit.atum.configs.Configs;
import com.intuit.atum.configs.ConfigsWrapper;

public enum DataSourceManager {

	INSTANCE;

	private BasicDataSource ds;

	public void init() throws Exception {

		Configs cfg = ConfigsWrapper.CONFIG.getModel();

		String dbName = cfg.getDbName();
		String dbUsername = cfg.getDbUsername();
		String dbPassword = cfg.getDbPassword();
		String dbUrl = cfg.getDbUrl();

		dbUrl = dbUrl.replace("DB_NAME", dbName);

		createDs(dbUsername, dbPassword, dbUrl);
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

	public Connection getConnection() {

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (Exception e) {
			System.out.println("Exception in getConnection: " + e);
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