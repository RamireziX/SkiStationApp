package com.awrzosek.ski_station.database_management;

import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRent;

public class EquipmentManager {
	public boolean removeRentedEquipment(Client client, EquipmentRent equipmentRent)
	{
		return false;
	}

	public EquipmentRent addRentedEquipment(Client client, Equipment equipment)
	{
		return null;
	}

	public Equipment addEquipment()
	{
		return null;
	}

	public boolean removeEquipment(Equipment equipment)
	{
		return false;
	}
}