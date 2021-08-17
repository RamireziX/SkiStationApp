package edit_windows.client.edit;

import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRent;

public class RentalInfo {
	private Equipment equipment;
	private EquipmentRent equipmentRent;

	public RentalInfo(Equipment equipment, EquipmentRent equipmentRent)
	{
		this.equipment = equipment;
		this.equipmentRent = equipmentRent;
	}

	public Equipment getEquipment()
	{
		return equipment;
	}

	public void setEquipment(Equipment equipment)
	{
		this.equipment = equipment;
	}

	public EquipmentRent getEquipmentRent()
	{
		return equipmentRent;
	}

	public void setEquipmentRent(EquipmentRent equipmentRent)
	{
		this.equipmentRent = equipmentRent;
	}
}
