package com.awrzosek.ski_station.database_management;

import com.awrzosek.ski_station.basic.BasicConsts;
import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRent;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRentDao;
import com.awrzosek.ski_station.tables.ski.equipment.rent.RentType;
import com.awrzosek.ski_station.tables.ski.skipass.Skipass;
import com.awrzosek.ski_station.tables.ski.skipass.SkipassDao;
import com.awrzosek.ski_station.tables.ski.skipass.map.Duration;
import com.awrzosek.ski_station.tables.ski.skipass.map.SkipassSkipassTypeMap;
import com.awrzosek.ski_station.tables.ski.skipass.map.SkipassSkipassTypeMapConsts;
import com.awrzosek.ski_station.tables.ski.skipass.map.SkipassSkipassTypeMapDao;
import com.awrzosek.ski_station.tables.ski.skipass.type.DiscountType;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientManager {
	private Connection connection;
	private ClientDao clientDao;
	private SkipassDao skipassDao;
	private EquipmentRentDao equipmentRentDao;
	private SkipassSkipassTypeMapDao sstmDao;

	public ClientManager(Connection connection)
	{
		this.connection = connection;
		clientDao = new ClientDao(connection);
		skipassDao = new SkipassDao(connection);
		equipmentRentDao = new EquipmentRentDao(connection);
		sstmDao = new SkipassSkipassTypeMapDao(connection);
	}

	//TODO porównać z fr
	public void addClient(Client client, HashMap<Equipment, RentType> equipmentsToRentType,
						  List<SkipassType> skipassTypes, Duration duration)
	{
		//pomyśleć co z pokazywaniem błędów - ale to jak już będzie gui
		try
		{
			List<Skipass> skipasses = skipassDao.getNotRented(skipassTypes.size());
			if (skipasses.size() == skipassTypes.size())
			{
				clientDao.add(client);
				initSkipasses(skipasses, skipassTypes, client, duration);
				if (equipmentsToRentType != null)
					rentEquipments(equipmentsToRentType, client, duration);
			} else
				System.err.println("Wystąpił błąd - brak dostępnych skipassów!");

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void removeClient(Client client)
	{
		try
		{
			if (!clientDao.hasRentedEquipment(client))
				unlinkSkipassAndDelete(client);
			else
				System.err.println("Klient ma wypożyczony sprzęt, który należy najpierw zwrócić!");
		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
	}

	//TODO potestować i porównać z fr publiczne metody poniższe
	public void removeRentedEquipment(EquipmentRent equipmentRent)
	{
		try
		{
			equipmentRentDao.delete(equipmentRent);
		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
	}

	public void removeAllRentedEquipment(Client client)
	{
		try
		{
			for (EquipmentRent equipmentRent : equipmentRentDao.listByClient(client))
				equipmentRentDao.delete(equipmentRent);
		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
	}

	public void addRentedEquipment(Client client, Equipment equipment, RentType rentType)
	{
		try
		{
			Skipass skipass = skipassDao.listByClient(client).get(0);
			EquipmentRent equipmentRent = new EquipmentRent(client.getId(), equipment.getId(), LocalDate.now(),
					skipass.getDateTo(), rentType);
			equipmentRentDao.add(equipmentRent);

		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
	}

	private void unlinkSkipassAndDelete(Client client) throws SQLException
	{
		for (Skipass skipass : skipassDao.listByClient(client))
		{
			//@formatter:off
			String query =
					"select * from " + SkipassSkipassTypeMapConsts.TAB_NAME +
					" where " + SkipassSkipassTypeMapConsts.FLD_SKIPASS_ID + " = " + skipass.getId();
			//@formatter:on
			SkipassSkipassTypeMap sstm = sstmDao.getByQuery(query).orElse(null);
			sstmDao.delete(sstm);
			skipass.clearData();
			skipassDao.update(skipass);
		}

		clientDao.delete(client);
	}

	private void initSkipasses(List<Skipass> skipasses, List<SkipassType> skipassTypes, Client client,
							   Duration duration) throws SQLException
	{
		int i = 0;
		for (Skipass skipass : skipasses)
		{
			BigDecimal price = new BigDecimal(0);
			SkipassType skipassType = skipassTypes.get(i);
			DiscountType discountType = skipassType.getDiscountType();

			switch (duration)
			{
				case ONE_DAY:
					price = BasicConsts.ONE_DAY_SKIPASS_PRIZE.multiply(discountType.getDiscount());
					break;
				case THREE_DAYS:
					price = BasicConsts.THREE_DAYS_SKIPASS_PRIZE.multiply(discountType.getDiscount());
					break;
				case ONE_WEEK:
					price = BasicConsts.ONE_WEEK_SKIPASS_PRIZE.multiply(discountType.getDiscount());
					break;
				case TWO_WEEKS:
					price = BasicConsts.TWO_WEEKS_SKIPASS_PRIZE.multiply(discountType.getDiscount());
					break;
			}

			SkipassSkipassTypeMap sstm = new SkipassSkipassTypeMap(skipass.getId(), skipassType.getId(),
					duration, price.setScale(2, RoundingMode.HALF_UP));
			sstmDao.add(sstm);
			skipass.setClientId(client.getId());
			skipass.setDateFrom(LocalDate.now());
			skipass.setDateTo(LocalDate.now().plusDays(duration.getDays()));
			skipass.setRented(true);
			skipass.setActive(false);
			skipassDao.update(skipass);
			i++;
		}
	}

	private void rentEquipments(HashMap<Equipment, RentType> equipmentsToRentType, Client client,
								Duration duration) throws SQLException
	{
		for (Map.Entry<Equipment, RentType> equipmentToRent : equipmentsToRentType.entrySet())
		{
			EquipmentRent rent = new EquipmentRent();
			rent.setClientId(client.getId());
			rent.setEquipmentId(equipmentToRent.getKey().getId());
			rent.setRentDate(LocalDate.now());
			rent.setReturnDate(LocalDate.now().plusDays(duration.getDays()));
			rent.setRentType(equipmentToRent.getValue());
			equipmentRentDao.add(rent);
		}
	}

}
