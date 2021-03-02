package com.awrzosek.ski_station;

import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRent;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRentDao;
import com.awrzosek.ski_station.tables.ski.equipment.rent.RentType;

import java.time.LocalDate;
import java.util.List;

public class Main {

	public static void main(String[] args)
	{
		//TODO skipass dao - uwaga maja fk
		//TODO - sprawdzić, czy ja wszedzie nie przekazuję string do bazy
		EquipmentRentDao equipmentRentDao = new EquipmentRentDao();
		equipmentRentDao
				.add(new EquipmentRent(null, 1L, 1L, LocalDate.now(), LocalDate.now(), RentType.STAY));
		equipmentRentDao
				.add(new EquipmentRent(null, 1L, 2L, LocalDate.now(), LocalDate.now(), RentType.STAY));
		equipmentRentDao
				.add(new EquipmentRent(null, 2L, 2L, LocalDate.now(), LocalDate.now(), RentType.STAY));

		List<EquipmentRent> l = equipmentRentDao.getAll();
		EquipmentRent equipmentRent = equipmentRentDao.get(1L).orElse(null);
		equipmentRent.setRentType(RentType.TO_GO);
		equipmentRentDao.update(equipmentRent);
		equipmentRentDao.delete(equipmentRent);


		System.out.println("end");

	}
}
