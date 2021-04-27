package com.awrzosek.ski_station.initializers.tables;

import com.awrzosek.ski_station.initializers.BasicDataInitializer;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentDao;

import java.sql.Connection;
import java.util.List;

public class EquipmentInitializer extends BasicDataInitializer<Equipment> {

	public EquipmentInitializer(Connection connection)
	{
		dao = new EquipmentDao(connection);
	}

	@Override
	protected List<Equipment> provideRecords()
	{
		return null;
	}
}
