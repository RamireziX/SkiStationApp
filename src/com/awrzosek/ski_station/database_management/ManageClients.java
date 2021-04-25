package com.awrzosek.ski_station.database_management;

import com.awrzosek.ski_station.tables.person.client.Client;
import com.awrzosek.ski_station.tables.person.client.ClientDao;
import com.awrzosek.ski_station.tables.ski.equipment.Equipment;
import com.awrzosek.ski_station.tables.ski.skipass.Skipass;
import com.awrzosek.ski_station.tables.ski.skipass.SkipassDao;
import com.awrzosek.ski_station.tables.ski.skipass.type.SkipassType;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ManageClients {
	private Connection connection;
	private ClientDao clientDao;
	private SkipassDao skipassDao;

	public ManageClients(Connection connection)
	{
		this.connection = connection;
		clientDao = new ClientDao(connection);
		skipassDao = new SkipassDao(connection);
	}

	public void addClient(Client client, Equipment equipment, SkipassType skipassType, int numberOfSkipasses)
	{
		//TODO połączenie z equipment i przetestować + pomyśleć co z pokazywaniem błędów
		try
		{
			List<Skipass> skipasses = skipassDao.getNotRented(numberOfSkipasses);
			if (skipasses.size() == numberOfSkipasses)
			{ //TODO else z powiadomieniem
				clientDao.add(client);
				initSkipasses(skipasses, skipassType, client);
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

}
