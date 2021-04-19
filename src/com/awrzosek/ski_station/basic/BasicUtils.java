package com.awrzosek.ski_station.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class BasicUtils {

	private BasicUtils()
	{
	}

	public static Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(BasicConsts.DB_URL);
	}
}
