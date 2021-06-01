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
import com.awrzosek.ski_station.tables.ski.skipass.map.SkipassSkipassTypeMapDao;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;

import java.math.BigDecimal;
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

	//TODO potestować wsyztsko czy jest ok po zmianach w bazie i porównać z fr
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

	//TODO dopasować do zmian, zaimplementować, potestować i porównać z fr publiczne metody poniższe
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
			String query = "";//TODO query z joinem, żeby zdobyć czas skipassu (a potem do dao to pewnie dać)
			SkipassSkipassTypeMap sstm = new SkipassSkipassTypeMapDao(connection).getByQuery(query).orElse(null);
			if (sstm != null)
			{
				EquipmentRent equipmentRent = new EquipmentRent(client.getId(), equipment.getId(), LocalDate.now(),
						LocalDate.now().plusDays(sstm.getDuration().getDays()), rentType);
				equipmentRentDao.add(equipmentRent);
			}
		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}

	}

	private void unlinkSkipassAndDelete(Client client) throws SQLException
	{
		for (Skipass skipass : skipassDao.listByClient(client))
		{
			skipass.clearData();//TODO albo w tej metodzie usuwać mapę albo w unlink
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
			BigDecimal price = BasicConsts.ONE_DAY_SKIPASS_PRIZE;//TODO jakieś liczenie w zależności od duration i typu
			SkipassSkipassTypeMap sstm = new SkipassSkipassTypeMap(skipass.getId(), skipassTypes.get(i).getId(),
					duration, price);
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
