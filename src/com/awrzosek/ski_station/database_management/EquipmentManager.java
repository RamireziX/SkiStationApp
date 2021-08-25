package com.awrzosek.ski_station.database_management;

import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentDao;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRent;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRentConsts;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRentDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class EquipmentManager {
	private EquipmentDao equipmentDao;
	private EquipmentRentDao equipmentRentDao;

	public EquipmentManager(Connection connection)
	{
		equipmentDao = new EquipmentDao(connection);
		equipmentRentDao = new EquipmentRentDao(connection);
	}

	public void addEquipment(Equipment equipment) throws SQLException
	{
		equipmentDao.add(equipment);
	}

	public void editEquipment(Equipment equipment) throws SQLException
	{
		equipmentDao.update(equipment);
	}

	public boolean removeEquipment(Equipment equipment) throws SQLException
	{
		//@formatter:off
		String query =
				"select * " +
				" from " + EquipmentRentConsts.TAB_NAME +
				" where " + EquipmentRentConsts.FLD_EQUIPMENT_ID + " = " + equipment.getId();
		//@formatter:on
		Optional<EquipmentRent> equipmentRent = equipmentRentDao.getByQuery(query);
		if (equipmentRent.isEmpty())
		{
			equipmentDao.delete(equipment);
			return true;
		}

		return false;
	}

	public EquipmentDao getEquipmentDao()
	{
		return equipmentDao;
	}

}
