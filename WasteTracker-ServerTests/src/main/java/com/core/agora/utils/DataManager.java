package com.core.agora.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataManager {

	public ResultSet executeSQL(String sql, Object[] params)
			throws SQLException;

	public ResultSet executeSQL(String sql) throws SQLException;

	public ResultSet query(String sql, Object[] params) throws SQLException;

	public ResultSet query(String sql) throws SQLException;

	public void shutdown();

	public void createShema();
}