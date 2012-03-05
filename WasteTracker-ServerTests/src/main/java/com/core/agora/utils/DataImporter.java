package com.core.agora.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataImporter {	
	private Connection conn;

	public DataImporter() {
	}

	public void connect(String db_connect_str, String db_userid, String db_password) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection(db_connect_str, db_userid,
					db_password);

		} catch (Exception e) {
			e.printStackTrace();
			conn = null;
		}		
	}

	public String[] getTableNames() throws SQLException {
		DatabaseMetaData md;
		List<String> listRes = new ArrayList<String>();
		md = conn.getMetaData();
	    ResultSet rs = md.getTables(null, null, "%", null);
	    
	    while (rs.next()) {
	      listRes.add(rs.getString(3));
	    }		
	    
	    return listRes.toArray(new String[] {});
	}

	public void truncateAll() throws SQLException {
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		for(String tableName: getTableNames()) {
			String query = String.format("TRUNCATE TABLE %s", tableName);
			stmt.executeQuery(query);			
		}
	}
	
	public void importData(String table, String filename, String[] columns) throws SQLException {
		String cols = arrayToList(columns);
		importData(table, filename, cols);
	}
	
	public void importData(String table, String filename, String columns) throws SQLException {
		Statement stmt;
		String query;

		File currentPath = new File(".");
		filename = currentPath.getAbsolutePath() + "/" + filename; 
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		query = String.format("LOAD DATA LOCAL INFILE '%s' INTO TABLE %s FIELDS ESCAPED BY '\\\\' (%s)", filename, table, columns);
//		System.out.println(query);
		stmt.executeUpdate(query);
	}
	
	private String arrayToList(String[] a) {
		String res = "";
		String glue = "";
		for(String s: a) {
			
			res = res + glue + s;
			glue = ", ";
		}
		return res;
	}
}