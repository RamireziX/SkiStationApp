package com.awrzosek.ski_station.database_management;

import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRent;
import com.awrzosek.ski_station.tables.ski.equipment.rent.EquipmentRentDao;
import com.awrzosek.ski_station.tables.ski.equipment.rent.RentType;
import com.awrzosek.ski_station.tables.ski.skipass.Skipass;
import com.awrzosek.ski_station.tables.ski.skipass.SkipassDao;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ManageClients {
	private ClientDao clientDao;
	private SkipassDao skipassDao;
	private EquipmentRentDao equipmentRentDao;

	public ManageClients(Connection connection)
	{
		clientDao = new ClientDao(connection);
		skipassDao = new SkipassDao(connection);
		equipmentRentDao = new EquipmentRentDao(connection);
	}

	//TODO populate table - dodać skipassy i equipmenty na pewno (można dla próby przez javę zrobić inicjalizator)
	public void addClient(Client client, List<Equipment> equipments, SkipassType skipassType, int numberOfSkipasses,
						  RentType rentType)
	{
		//TODO przetestować + pomyśleć co z pokazywaniem błędów
		try
		{
			List<Skipass> skipasses = skipassDao.getNotRented(numberOfSkipasses);
			if (skipasses.size() == numberOfSkipasses)
			{ //TODO else z powiadomieniem - zabrakło skipassów
				clientDao.add(client);
				initSkipasses(skipasses, skipassType, client);
				rentEquipments(equipments, client, skipassType, rentType);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void removeClient(Client client)
	{
	}

	private void initSkipasses(List<Skipass> skipasses, SkipassType skipassType, Client client) throws SQLException
	{
		for (Skipass skipass : skipasses)
		{
			skipass.setClientId(client.getId());
			skipass.setSkipassTypeId(skipassType.getId());
			skipass.setDateFrom(LocalDate.now());
			skipass.setDateTo(LocalDate.now().plusDays(skipassType.getDuration()));
			skipassDao.update(skipass);
		}
	}

	private void rentEquipments(List<Equipment> equipments, Client client, SkipassType skipassType, RentType rentType)
			throws SQLException //TODO to możliwe, że pójdzie do manage equipments, może jako static funkcja
	{
		for (Equipment equipment : equipments)
		{
			EquipmentRent rent = new EquipmentRent();
			rent.setClientId(client.getId());
			rent.setEquipmentId(equipment.getId());
			rent.setRentDate(LocalDate.now());
			/*TODO może to domyślnie, a dać opcję klientowi wybrać kiedy zwróci*/
			rent.setReturnDate(LocalDate.now().plusDays(skipassType.getDuration()));
			rent.setRentType(rentType);
			equipmentRentDao.add(rent);
		}
	}

}
