package com.awrzosek.ski_station.initializers;

import com.awrzosek.ski_station.basic.BasicUtils;

import java.sql.Connection;
import java.sql.SQLException;

public final class InitializerUtils {

	private InitializerUtils()
	{
	}

	// możnaby kiedyś zczytywać te dane z jakiegoś większego cvs/xls
	public static void run()
	{
		try (Connection conn = BasicUtils.getConnection())
		{
			//new EquipmentInitializer(conn).initializeData();
			//new SkipassInitializer(conn).initializeData();

		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}

	}
}
