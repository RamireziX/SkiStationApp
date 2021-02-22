package com.awrzosek.ski_station;

import com.awrzosek.ski_station.tables.ski.equipment.Condition;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentDao;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentType;

import java.math.BigDecimal;
import java.util.List;

public class Main {

	public static void main(String[] args)
	{
		//TODO skipass_typeDAO a potem resztę - eq rent i skipass mają fk
		EquipmentDao equipmentDao = new EquipmentDao();
		equipmentDao
				.add(new Equipment(null, "eq", "123", EquipmentType.GOOGLES, new BigDecimal(12.00), Condition.BROKEN));
		equipmentDao
				.add(new Equipment(null, "eq2", "12333", EquipmentType.GOOGLES, new BigDecimal(12.00),
						Condition.BROKEN));
		equipmentDao
				.add(new Equipment(null, "eq3", "1233333", EquipmentType.GOOGLES, new BigDecimal(12.00),
						Condition.BROKEN));
		List<Equipment> l = equipmentDao.getAll();
		Equipment eq = equipmentDao.get(3L).orElse(null);
		eq.setName("updated");
		equipmentDao.update(eq);
		equipmentDao.delete(eq);


		System.out.println("end");

	}
}
