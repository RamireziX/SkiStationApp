package com.awrzosek.ski_station.initializers;

import com.awrzosek.ski_station.basic.BasicUtils;
import com.awrzosek.ski_station.initializers.tables.EquipmentInitializer;

import java.sql.Connection;
import java.sql.SQLException;

public final class InitializerUtils {

	private InitializerUtils()
	{
	}

	//TODO możnaby kiedyś zczytywać te dane z jakiegoś większego cvs/xls
	public static void run()
	{
		try (Connection conn = BasicUtils.getConnection())
		{
			EquipmentInitializer eqInit = new EquipmentInitializer(conn);
			eqInit.initializeData();
		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}

	}
}
