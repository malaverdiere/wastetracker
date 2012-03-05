package com.core.agora.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataManagerImpl implements DataManager {
	private Connection connection;
	public static String JDBC_DRIVER = "org.hsqldb.jdbcDriver";
	public static String URL;
	
	public DataManagerImpl(String url, String user, String pass) {
		this(createConnection(url, user, pass));
	}

	private static Connection createConnection(String url, String user, String pass) {
		try {
			return DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public DataManagerImpl(Connection conn) {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		this.connection = conn;
	}
	
	public void createShema() {
		if(shemaExists() == false) {
			StringBuilder sb = readSQLSchema();
			createSQLShema(sb);			
		}				
	}
	
	private boolean shemaExists() {
		DatabaseMetaData md;
		try {
			md = connection.getMetaData();
			ResultSet t = md.getTables(null, null, "CUSTOMERS", null);
			return t.first();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public ResultSet executeSQL(String sql, Object[] params) throws SQLException {
		PreparedStatement stmt = createPreparedStatement(sql, params);
		if(stmt.execute()) {
			return stmt.getResultSet();
		} else {
			return null;
		}
	}

	public ResultSet executeSQL(String sql) throws SQLException {
		Statement stmt = connection.createStatement();
		if(stmt.execute(sql)) {
			return stmt.getResultSet();
		} else {
			return null;
		}
	}

	public ResultSet query(String sql, Object[] params) throws SQLException {
		PreparedStatement stmt = createPreparedStatement(sql, params);
		return stmt.executeQuery();
	}
	
	@Override
	public ResultSet query(String sql) throws SQLException {
		Statement stmt = connection.createStatement();
		return stmt.executeQuery(sql);
	}

	public void insertLine(String table, String csv) {
		
	}
	
	private PreparedStatement createPreparedStatement(String sql, Object[] params) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(sql);
		int i = 1;
		for(Object o: params) {
			stmt.setObject(i++, o);			
		}
		return stmt;
	}

	private void createSQLShema(StringBuilder sb) {
		try {
			executeSQL(sb.toString());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private StringBuilder readSQLSchema() {
		
		InputStream res = DataManager.class.getResourceAsStream("/shema.sql");
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(res, "UTF-8"));
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb;
	}

	public void shutdown() {

		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.execute("SHUTDOWN");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}