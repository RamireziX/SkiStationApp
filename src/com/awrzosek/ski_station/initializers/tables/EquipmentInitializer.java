package com.awrzosek.ski_station.initializers.tables;

import com.awrzosek.ski_station.initializers.BasicDataInitializer;
import com.awrzosek.ski_station.tables.ski.equipment.Condition;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.EquipmentDao;
import com.awrzosek.ski_station.tables.ski.equipment.Type;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class EquipmentInitializer extends BasicDataInitializer<Equipment> {

	public EquipmentInitializer(Connection connection)
	{
		dao = new EquipmentDao(connection);
	}

	@Override
	protected List<Equipment> provideRecords()
	{
		//kiedyś to powinno zczytywać dane z xls np - ale to nie jest top priority (ale jest w func req)
		List<Equipment> equipments = new ArrayList<>();

		equipments.add(new Equipment("Sprzęt1", "0001", Type.SKIS,
				new BigDecimal("50.20"), Condition.GOOD));
		equipments.add(new Equipment("Sprzęt2", "0002", Type.HELMET,
				new BigDecimal("20.00"), Condition.EX));
		equipments.add(new Equipment("Sprzęt3", "0003", Type.GOOGLES,
				new BigDecimal("10.00"), Condition.GOOD));
		equipments.add(new Equipment("Sprzęt4", "0004", Type.SHOES,
				new BigDecimal("30.00"), Condition.GOOD));
		equipments.add(new Equipment("Sprzęt5", "0005", Type.SNOWBOARD,
				new BigDecimal("100.00"), Condition.GOOD));
		equipments.add(new Equipment("Sprzęt6", "0006", Type.SKIS,
				new BigDecimal("50.00"), Condition.POOR));
		equipments.add(new Equipment("Sprzęt7", "0007", Type.SKIS,
				new BigDecimal("50.00"), Condition.BROKEN));
		equipments.add(new Equipment("Sprzęt8", "0008", Type.SKIS,
				new BigDecimal("50.00"), Condition.GOOD));
		equipments.add(new Equipment("Sprzęt9", "0009", Type.SKIS,
				new BigDecimal("50.00"), Condition.GOOD));
		equipments.add(new Equipment("Sprzęt10", "0010", Type.SKIS,
				new BigDecimal("50.00"), Condition.GOOD));


		return equipments;
	}
}
